package ch.qos.logback.core.rolling;

import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.rolling.helper.*;
import co.my.logback.util.ReflectUtils;

import java.util.Date;

import static ch.qos.logback.core.CoreConstants.UNBOUND_HISTORY;

/**
 * @Author pengyue.du
 * @Date 2020/8/18 5:46 pm
 * @Description
 */
public class mySizeAndTimeBasedRollingPolicy<E> extends SizeAndTimeBasedRollingPolicy {

    @Override
    public void start() {
        SizeAndTimeBasedFNATP<E> sizeAndTimeBasedFNATP = new SizeAndTimeBasedFNATP<E>(SizeAndTimeBasedFNATP.Usage.EMBEDDED);
        if(maxFileSize == null) {
            addError("maxFileSize property is mandatory.");
            return;
        } else {
            addInfo("Archive files will be limited to ["+maxFileSize+"] each.");
        }

        sizeAndTimeBasedFNATP.setMaxFileSize(maxFileSize);
        timeBasedFileNamingAndTriggeringPolicy = sizeAndTimeBasedFNATP;

        if(!isUnboundedTotalSizeCap() && totalSizeCap.getSize() < maxFileSize.getSize()) {
            addError("totalSizeCap of ["+totalSizeCap+"] is smaller than maxFileSize ["+maxFileSize+"] which is non-sensical");
            return;
        }

        // most work is done by the parent

        // set the LR for our utility object
        ReflectUtils.getFiled(TimeBasedRollingPolicy.class, "renameUtil", this, RenameUtil.class).setContext(this.context);

        // find out period from the filename pattern
        if (fileNamePatternStr != null) {
            fileNamePattern = new myFileNamePattern(fileNamePatternStr, this.context);
            determineCompressionMode();
        } else {
            addWarn(FNP_NOT_SET);
            addWarn(CoreConstants.SEE_FNP_NOT_SET);
            throw new IllegalStateException(FNP_NOT_SET + CoreConstants.SEE_FNP_NOT_SET);
        }

        Compressor compressor = new Compressor(compressionMode);
        ReflectUtils.setFiled(TimeBasedRollingPolicy.class, "compressor", this, compressor);
        compressor.setContext(context);

        // wcs : without compression suffix
        fileNamePatternWithoutCompSuffix = new myFileNamePattern(Compressor.computeFileNameStrWithoutCompSuffix(fileNamePatternStr, compressionMode), this.context);

        addInfo("Will use the pattern " + fileNamePatternWithoutCompSuffix + " for the active file");

        if (compressionMode == CompressionMode.ZIP) {
            String zipEntryFileNamePatternStr = transformFileNamePattern2ZipEntry(fileNamePatternStr);
            zipEntryFileNamePattern = new myFileNamePattern(zipEntryFileNamePatternStr, context);
        }

        if (timeBasedFileNamingAndTriggeringPolicy == null) {
            timeBasedFileNamingAndTriggeringPolicy = new DefaultTimeBasedFileNamingAndTriggeringPolicy<E>();
        }
        timeBasedFileNamingAndTriggeringPolicy.setContext(context);
        timeBasedFileNamingAndTriggeringPolicy.setTimeBasedRollingPolicy(this);
        timeBasedFileNamingAndTriggeringPolicy.start();

        if (!timeBasedFileNamingAndTriggeringPolicy.isStarted()) {
            addWarn("Subcomponent did not start. TimeBasedRollingPolicy will not start.");
            return;
        }

        // the maxHistory property is given to TimeBasedRollingPolicy instead of to
        // the TimeBasedFileNamingAndTriggeringPolicy. This makes it more convenient
        // for the user at the cost of inconsistency here.
        Integer maxHistory = ReflectUtils.getFiled(TimeBasedRollingPolicy.class, "maxHistory", this, Integer.class);
        if (maxHistory != UNBOUND_HISTORY) {
            ArchiveRemover archiveRemover = timeBasedFileNamingAndTriggeringPolicy.getArchiveRemover();
            ReflectUtils.setFiled(TimeBasedRollingPolicy.class, "archiveRemover", this, archiveRemover);
            archiveRemover.setMaxHistory(maxHistory);
            archiveRemover.setTotalSizeCap(totalSizeCap.getSize());
            if (cleanHistoryOnStart) {
                addInfo("Cleaning on start up");
                Date now = new Date(timeBasedFileNamingAndTriggeringPolicy.getCurrentTime());
                cleanUpFuture = archiveRemover.cleanAsynchronously(now);
            }
        } else if (!isUnboundedTotalSizeCap()) {
            addWarn("'maxHistory' is not set, ignoring 'totalSizeCap' option with value ["+totalSizeCap+"]");
        }

        ReflectUtils.setFiled(RollingPolicyBase.class, "started", this, true);
    }

    private String transformFileNamePattern2ZipEntry(String fileNamePatternStr) {
        String slashified = FileFilterUtil.slashify(fileNamePatternStr);
        return FileFilterUtil.afterLastSlash(slashified);
    }

}

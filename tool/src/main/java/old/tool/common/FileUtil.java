package old.tool.common;

import java.io.File;

/**
 * @Author pengyue.du
 * @Date 2020/8/14 11:02 上午
 * @Description
 */
public class FileUtil {


    public static void deleteFileBySuffix(File file, String endSuffix) {
        if(file.isDirectory()) {
            for(File subFile : file.listFiles()) {
                deleteFileBySuffix(subFile, endSuffix);
            }
        }else {
            if(file.getName().endsWith(endSuffix)) {
                file.delete();
            }
        }
    }

}

package old.tool.tool;

import org.py.tool.file.FileUtil;
import org.junit.Test;

import java.io.File;

/**
 * @Author pengyue.du
 * @Date 2020/8/7 3:28 下午
 * @Description
 */
public class MavenTool {

    @Test
    public void delete() {
        String basePath = "/Users/pengyue.du/Documents/mysoft/repository/MavenRepository";
        String endSuffix = ".lastUpdated";
        FileUtil.deleteFileBySuffix(new File(basePath), endSuffix);
    }



}

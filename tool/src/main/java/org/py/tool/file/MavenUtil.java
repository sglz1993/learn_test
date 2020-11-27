package org.py.tool.file;

import org.junit.Test;

import java.io.File;

public class MavenUtil {

    @Test
    public void delete() {
        String basePath = "/Users/pengyue.du/Documents/mysoft/repository/MavenRepository";
        String endSuffix = ".lastUpdated";
        FileUtil.deleteFileBySuffix(new File(basePath), endSuffix);
    }



}

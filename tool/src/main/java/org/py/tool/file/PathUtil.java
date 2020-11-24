package org.py.tool.file;

import org.junit.Test;

import java.io.File;

public class PathUtil {

    @Test
    public void getDirectByBasePath() {
        String basePath = "/Users/pengyue.du/Code/Meijia/Work01/learn_test";
        String direct = "target";

        File file = new File(basePath);
        findDirect(file, direct, basePath);
    }

    private void findDirect(File file, String direct, String removePath) {
        if(file.isDirectory()) {
            if(file.getName().equals(direct)) {
                String targetPath = file.getAbsolutePath().substring(removePath.length() + 1);
                System.out.println(String.format("git rm --cached -r %s;\\", targetPath));
            }
            File[] files = file.listFiles();
            for(File subFile : files) {
                findDirect(subFile, direct, removePath);
            }
        }
    }

}

package org.py.test.log.test;

import org.junit.Test;

public class MathTest {

    @Test
    public void math() {
        for (int i = 1; i <= 9; i++) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int j = 1; j <= i; j++) {
                if (i != 7 || j != 7) {
                    stringBuffer.append(String.format("%s * %s = %s", i, j, i*j)).append("\t");
                }
            }
            System.out.println(stringBuffer.toString());
        }
    }


}

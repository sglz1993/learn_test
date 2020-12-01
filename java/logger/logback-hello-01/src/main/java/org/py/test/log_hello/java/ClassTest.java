package org.py.test.log_hello.java;












public class ClassTest {

    {
        String a = new InnerTest().name;
        String b = new ProtectedTest().name;
    }

    static class InnerTest {
        private String name;
    }

}

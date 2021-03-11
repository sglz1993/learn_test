package old.tool.java_base;

import com.google.gson.Gson;
import old.tool.java_base.test.SubEnumTest;
import old.tool.java_base.test.SubSubEnumTest;
import org.junit.Test;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author pengyue.du
 * @Date 2020/8/17 5:41 下午
 * @Description
 */
public class ClassTest {

    @Test
    public void test() {
        System.out.println(new B().person.name);
    }

    @Test
    public void test2() {
//        System.out.println(new EnumTest().name);
        System.out.println(new SubEnumTest().name);
        System.out.println(new SubSubEnumTest().name);
        SubSubEnumTest subSubEnumTest = new SubSubEnumTest();
        System.out.println(new SubSubEnumTest().name);
    }

    @Test
    public void test3() throws Exception {
        EnumTest enumTest = new SubEnumTest();
        System.out.println(enumTest.getName());
        Field name = EnumTest.class.getDeclaredField("name");
        name.setAccessible(true);
        System.out.println(name.get(enumTest));
        name.set(enumTest, "lalala");
        System.out.println(enumTest.name);
    }

    @Test
    public void test4() {
        Man man = new Man();
        System.out.println(new Gson().toJson(man));
    }

    // 2020-09-11T17:05:48.560+08:00
    @Test
    public void test5() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date = new Date();
        System.out.println(format.format(date));
    }

    @SuppressWarnings("AliAccessStaticViaInstance")
    @Test
    public void test6() {
        System.out.println(Person.name);
        System.out.println(Man.name);
        System.out.println(new Man(){}.name);
    }




    static class Person {
        static String name = "Person";
    }

    static class Man extends Person{
        static Man man = new Man();
        static String name = "Man";
        static {
            System.out.println(man.toString());
        }

    }

    static class A {
        Person person = new Person();
    }

    static class B extends A {
        Man person = new Man();
    }

}

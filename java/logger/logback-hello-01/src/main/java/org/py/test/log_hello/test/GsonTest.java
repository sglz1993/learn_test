package org.py.test.log_hello.test;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class GsonTest {

    /**
     * [2021-01-18 14:37:27.976+0800] [INFO ] [] [org.py.test.log_hello.test.GsonTest] [test]:24 {
     *   "age": "age test",
     *   "person": "person llalal",
     *   "test": {
     *     "name": "class name"
     *   }
     * }
     */
    @Test
    public void test() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        log.info(gson.toJson(new ExposeFiledTest()));
    }

    /**
     * [2021-01-18 14:55:22.871+0800] [INFO ] [] [org.py.test.log_hello.test.GsonTest] [test2]:33 {}
     *
     * 说明：
     *      启用注解：excludeFieldsWithoutExposeAnnotation()
     *      只有标注有@expose的属性才可能被序列化和反序列化
     */
    @Test
    public void test2() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        log.info(gson.toJson(new ExposeFiledTest()));
    }

    /**
     * [2021-01-18 14:45:23.018+0800] [INFO ] [] [org.py.test.log_hello.test.GsonTest] [test2]:57 {
     *   "age": "age test"
     * }
     */
    @Test
    public void test3() {
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        Expose expose = f.getAnnotation(Expose.class);
                        return expose != null && !expose.serialize();
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        Expose expose = f.getAnnotation(Expose.class);
                        return expose != null && !expose.deserialize();
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).setPrettyPrinting().create();
        log.info(gson.toJson(new ExposeFiledTest()));
    }


    @Data
    static class ExposeClassTest {
        private String name = "class name";
    }

    @Data
    public static class ExposeFiledTest {

//        @Expose
        private String age = "age test";

        @Expose(serialize = false)
        private String person = "person llalal";

        @Expose(serialize = false)
        private ExposeClassTest test = new ExposeClassTest();
    }
}

package co.my.logback.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author pengyue.du
 * @Date 2020/8/18 5:51 下午
 * @Description
 */
public class ReflectUtils {

    public static <T> T getFiled(Class targetClass, String filedName, Object targetObj, Class<T> resultType) {
        try {
            Field field = targetClass.getDeclaredField(filedName);
            field.setAccessible(true);
            return resultType.cast(field.get(targetObj));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setFiled(Class targetClass, String filedName, Object targetObj, Object value) {
        try {
            Field field = targetClass.getDeclaredField(filedName);
            field.setAccessible(true);
            field.set(targetObj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invoke(Class targetClass, String filedName, Object targetObj, Object... param) {
        try {
            Method[] methods = targetClass.getDeclaredMethods();
            if(methods != null && methods.length > 0) {
                for(Method method : methods) {
                    if(!method.getName().equals(filedName)) {
                        continue;
                    }
                    Class<?>[] types = method.getParameterTypes();
                    if(equal(types, param)) {
                        method.setAccessible(true);
                        return method.invoke(targetObj, param);
                    }
                }
            }
            throw new NoSuchMethodException(String.format("not find method %s", filedName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean equal(Class<?>[] types, Object[] param) {
        if(types.length != param.length) {
            return false;
        }
        for(int i = 0; i < types.length; i++) {
            if(!types[i].isAssignableFrom(param[i].getClass())) {
                return false;
            }
        }
        return true;
    }


}

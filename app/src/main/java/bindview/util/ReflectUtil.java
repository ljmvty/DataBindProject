package bindview.util;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by LJM on 2017/8/8 9:39.
 * Description:
 */

public class ReflectUtil {
    /**
     * 通过key获取某个对象中的Object
     *
     * @param object
     * @param key
     * @return
     */
    public static Object getObjectByKey(Object object, String key) {
        Class clazz = object.getClass();
        Field field;
        try {
            field = clazz.getDeclaredField(key);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            field = null;
        }

        Object result = null;
        if (field != null) {
            try {
                result = field.get(object);
            } catch (IllegalAccessException e) {

            }
        }
        return result;
    }

    /**
     * 通过key获取某个对象中的Field
     *
     * @param object
     * @param key
     * @return
     */
    public static Field getFieldByKey(Object object, String key) {
        Class clazz = object.getClass();
        Field field;
        try {
            field = clazz.getDeclaredField(key);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            field = null;
        }

        return field;
    }

    /**
     * 通过key获取某个对象中的Field
     *
     * @param object
     * @param key
     * @return
     */
    public static String getValueByKey(Object object, String key) {
        String result = "";
        Field field = getFieldByKey(object, key);
        if (field != null) {
            try {
                if (field.get(object) != null) {
                    result = field.get(object).toString();
                }
            } catch (IllegalAccessException e) {
                result = "";
            }
        }
        return result;
    }

    /**
     * 获取所有成员变量的名称
     *
     * @param object
     * @return
     */
    public static ArrayList<String> getAllKeys(Object object) {
        ArrayList<String> keys = new ArrayList<>();
        if (object == null) {
            return keys;
        }

        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        if (fields == null || fields.length < 1) {
            return keys;
        }

        for (int i = 0; i < fields.length; i++) {
            keys.add(fields[i].getName());
        }

        return keys;
    }
}

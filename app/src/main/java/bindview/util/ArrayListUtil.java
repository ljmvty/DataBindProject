package bindview.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LJM on 2017/8/8 9:43.
 * Description:
 */

public class ArrayListUtil {
    /**
     * 将数组转化成ArrayList
     *
     * @param objects
     * @return
     */
    public static ArrayList<Object> arrayToArrayList(Object[] objects) {
        ArrayList<Object> objectArrayList = new ArrayList<>();
        if (objects != null && objects.length < 1) {
            return objectArrayList;
        }

        for (Object object : objects) {
            objectArrayList.add(object);
        }
        return objectArrayList;
    }

    public static ArrayList<String> suffixAdder(ArrayList<String> parent) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> parentCopy = (ArrayList<String>) parent.clone();
        result.addAll(parent);

        ArrayList<String> suffixList = getSuffixList();
        for (String suffix : suffixList) {
            ArrayList<String> withSingleSuffix = new ArrayList<>();
            for (int i = 0; i < parentCopy.size(); i++) {
                withSingleSuffix.add(parentCopy.get(i) + "_" + suffix);
                withSingleSuffix.add(parentCopy.get(i) + suffix);
            }
            result.addAll(withSingleSuffix);
        }
        return result;
    }

    /**
     * object 转化成 ArrayList<Object>
     *
     * @param object
     * @return
     */
    public static ArrayList<Object> objectToArray(Object object) {
        if (object instanceof List) {
            return (ArrayList<Object>) object;
        } else if (object.getClass().isArray()) {
            Object[] objects = (Object[]) object;
            return arrayToArrayList(objects);
        }
        return null;
    }

    /**
     * 判断一个对象是不是数组类型。
     *
     * @param object
     * @return
     */
    public static boolean isArrayOrList(Object object) {
        if (object instanceof List) {
            return true;
        } else if (object.getClass().isArray()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 判断一个对象是不是数组类型。
     *
     * @param object
     * @return
     */
    public static boolean isArrayOrList(Object object, String key) {
        Object objectFromObject = ReflectUtil.getObjectByKey(object, key);
        boolean flag = ArrayListUtil.isArrayOrList(objectFromObject);
        return flag;
    }

    private static ArrayList<String> getSuffixList() {
        //可能的后缀
        ArrayList<String> suffixList = new ArrayList<>();
        suffixList.add("iv");
        suffixList.add("tv");
        suffixList.add("et");
        suffixList.add("bt");
        suffixList.add("btn");
        suffixList.add("list");
        //可能的后缀
        return suffixList;
    }
}

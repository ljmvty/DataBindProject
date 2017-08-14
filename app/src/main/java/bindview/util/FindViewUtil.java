package bindview.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by LJM on 2017/8/8 10:04.
 * Description:
 */

public class FindViewUtil {
    /**
     * 通过Data的成员变量名进行赋值
     *
     * @param rootView
     * @param context
     * @param string
     * @return
     */
    public static View findViewByDataKey(View rootView, Context context, String string) {
        View view = null;
        int viewId;

        if (context == null || string == null || string == "") {
            return null;
        }

        viewId = context.getResources().getIdentifier(string, "id", context.getPackageName());

        if (viewId != 0) {
            view = rootView.findViewById(viewId);
        }
        return view;
    }

    /**
     * 获取一项优先级最高的View
     *
     * @param key
     * @param fuzzyMatch
     * @return
     */
    public static View getViewByObjectFieldName(Activity activity, String key, boolean fuzzyMatch) {
        View rootView = activity.getWindow().getDecorView();
        return getViewByObjectFieldName(activity, rootView, key, fuzzyMatch);
    }

    /**
     * 获取一项优先级最高的View
     *
     * @param key
     * @param fuzzyMatch
     * @return
     */
    public static View getViewByObjectFieldName(Fragment fragment, String key, boolean fuzzyMatch) {
        View rootView = fragment.getView();
        Activity activity = fragment.getActivity();
        return getViewByObjectFieldName(activity, rootView, key, fuzzyMatch);
    }


    /**
     * 获取一项优先级最高的View
     *
     * @param key
     * @param fuzzyMatch
     * @return
     */
    public static View getViewByObjectFieldName(Activity activity, View rootView, String key, boolean fuzzyMatch) {
        ArrayList<String> possibleIds = guessViewIdNameByFieldName(key, fuzzyMatch);
        for (String string : possibleIds) {
            View singleView = findViewByDataKey(rootView, activity, string);
            if (singleView != null) {
                return singleView;
            }
        }
        return null;
    }

    /**
     * 从类的成员变量名推断可能的View的Id
     * <p>
     * =============基础规则（规则之间不会相互影响）=============
     * 变量名和View的id完全一致
     * 变量名(驼峰)对应View(下划线分隔符)
     * =============通用规则（会将基础规则翻倍）=============
     * View比变量名多出了Iv，Tv，Et下划线
     *
     * @param string
     * @param fuzzyMatch 完全匹配的情况，只考虑 “变量名和View的id完全一致” 的情况
     * @return
     */
    public static ArrayList<String> guessViewIdNameByFieldName(String string, boolean fuzzyMatch) {

        //避免null的情况
        if (string == null) {
            string = "";
        }

        ArrayList<String> viewIds = new ArrayList<>();
        viewIds.add(string);

        if (fuzzyMatch == false) {
            //变量名和View的id完全一致
            return viewIds;
        } else {
            //变量名(驼峰)对应View(下划线分隔符)
            viewIds.add(StringUtil.camelToUnderline(string));
            //之前所有规则加上后缀
            viewIds = ArrayListUtil.suffixAdder(viewIds);
            return viewIds;
        }
    }
}

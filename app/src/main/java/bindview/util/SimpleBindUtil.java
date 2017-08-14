package bindview.util;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by LJM on 2017/8/8 9:54.
 * Description:
 */

public class SimpleBindUtil {

    final static String placeHolder = "--";

    /**
     * 设置数据到TextView或者相关的父类
     *
     * @param textView
     * @param string
     */
    public static void bindDataToTextView(TextView textView, String string) {
        if (textView == null) {
            return;
        }

        if (TextUtils.isEmpty(string)) {
            string = placeHolder;
        }

        textView.setText(string);
    }

    /**
     * 设置View到具体的ImageView
     *
     * @param imageView
     * @param string
     */
    public static void bindDataToImageView(ImageView imageView, String string) {
        if (imageView == null) {
            return;
        }
        //ImageLoader
    }
}

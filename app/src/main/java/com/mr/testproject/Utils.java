package com.mr.testproject;

import android.content.Context;

/**
 * @auther: pengwang
 * @date: 2022/5/9
 * @description:
 */
public class Utils {

    public static int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px转dip
     */
    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

}

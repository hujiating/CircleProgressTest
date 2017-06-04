package com.jenny.example.circleprogress;

import android.content.res.Resources;

/**
 * Created by hujiating on 2017/6/1.
 */
public final class Utils {
    
    private Utils() {
    }
    
    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static int strToTime(String strTime){
        String[] strings = strTime.split(":");
        int minute = Integer.parseInt(strings[0]);
        int second = Integer.parseInt(strings[1]);
        int time = minute*60+second;
        return time;
    }
}

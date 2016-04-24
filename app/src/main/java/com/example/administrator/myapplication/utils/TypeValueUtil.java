package com.example.administrator.myapplication.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * TypeValueUtil
 * Created by zhuyingxin at 2016/1/17 9:55.
 * QQ: 657036139
 */
public class TypeValueUtil {

    private Context mContext;
    private DisplayMetrics metrics;
    public TypeValueUtil(Context context) {
        mContext = context;
        initWindowManager();
    }

    /**
     * 单位类型转换成px，例如dp转换成其他类型
     * @param unit TypedValue.COMPLEX_UNIT_SP等等,把unit单位转成px单位
     * @param value
     * @return type  px
     */


    public int translateTypedValue(int unit,float value){
        int type = (int) TypedValue.applyDimension(unit, value, mContext.getResources().getDisplayMetrics());
        return type;
    }

    /***
     * 获取屏幕宽度像素
     * @return int
     */
    public int getScreenWidth(){
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕高度像素
     * @return int
     */
    public int getScreenHeight(){
        return metrics.heightPixels;
    }

    /***
     * 获取DisplayMetrics对象，再获取屏幕的参数
     */
    private void initWindowManager() {
        /**
         * 第一种方法
         */
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        /**
         * 第二种方法
         * DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
         */

    }
}

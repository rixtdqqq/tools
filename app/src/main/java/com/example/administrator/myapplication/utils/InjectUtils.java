package com.example.administrator.myapplication.utils;

import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * InjectUtils
 * Created by zhuyingxin at 2016/2/28 21:03.
 * QQ: 657036139
 */
public class InjectUtils {

    private static final String SET_CONTENT_VIEW= "setContentView";
    private static final String FIND_VIEW_BY_ID= "findViewById";
    /**将Activity注入Context*/
    public static void inject(Context context){
        injectLayout(context);
        Map<Integer, Object> injectView = injectView(context);
    }
    /**注入布局*/
    private static void injectLayout(Context context){
//1、创建一个布局注解类
        //2、实现布局注入
        //获取自定义的布局注解类,clazz其实就是我们当前的activity对象
        Class<? extends Context> clazz = context.getClass();
        ContextView contextView = clazz.getAnnotation(ContextView.class);
        int layoutId  = contextView.value();
        //加载布局文件
        //通过类反射机制动态调用activity身上的setContentView方法
        try {
            Method method = clazz.getMethod(SET_CONTENT_VIEW, int.class);
            method.invoke(context,layoutId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  static Map<Integer,Object> injectView(Context context){
        Map<Integer,Object> viewMap = new HashMap<>();
        //定义注解类
        Class<? extends Context> clazz = context.getClass();
        //第一步获取当前activity身上所有的属性
        Field[] fields = clazz.getDeclaredFields();
        for (Field field:fields){
            ViewInject annotation = field.getAnnotation(ViewInject.class);
            if (null != annotation){
                int viewId = annotation.value();
                //注入view
                //通过java反射机制动态获取activity身上的findViewById方法
                try {
                    Method findViewByIdMethod = clazz.getMethod(FIND_VIEW_BY_ID, int.class);
                    Object view = findViewByIdMethod.invoke(context, viewId);
                    //设置访问权限
                    field.setAccessible(true);
                    field.set(context,view);
                    viewMap.put(viewId,view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return viewMap;
    }
}

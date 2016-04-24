package com.example.administrator.myapplication.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ContextView 配置注解类的两要素  1、作用范围  2、生命周期
 * Created by zhuyingxin at 2016/2/28 21:07.
 * QQ: 657036139
 */
//配置作用范围ElementType.TYPE：作用在类身上
@Target(ElementType.TYPE)
//2、配置生命周期（运行时）
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextView {
    int value();
}

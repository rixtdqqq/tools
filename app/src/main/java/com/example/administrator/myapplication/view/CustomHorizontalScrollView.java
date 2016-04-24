package com.example.administrator.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.utils.TypeValueUtil;

/**
 * CustomHorizatolScrollView
 * Created by zhuyingxin at 2016/1/17 11:12.
 * QQ: 657036139
 */
public class CustomHorizontalScrollView extends HorizontalScrollView {

    private LinearLayout mWrapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private TypeValueUtil util;
    //防止onMeasure方法多次调用
    private boolean isFirst;

    private int mMenuWidth;
    /***
     * 屏幕的宽
     */
    private int mScreenWidth;
    /***
     * px
     */
    private int leftMenuPaddingRight = 50;

    /**
     * 未使用自定义属性调用构造方法
     *
     * @param context
     * @param attrs
     */
    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        util = new TypeValueUtil(context);
        mScreenWidth = util.getScreenWidth();
//        将dp转换成px
        leftMenuPaddingRight = util.translateTypedValue(TypedValue.COMPLEX_UNIT_DIP, 50);

    }

    /**
     * 测量内部VIEW(子View)的宽和高，以及自己的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isFirst) {
            mWrapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWrapper.getChildAt(0);
            mContent = (ViewGroup) mWrapper.getChildAt(1);
            mMenuWidth =  mMenu.getLayoutParams().width = mScreenWidth - leftMenuPaddingRight;
            mContent.getLayoutParams().width = mScreenWidth;
            isFirst = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    /***
     * 决定子view的摆放位置
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            this.scrollTo(mMenuWidth,0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                /***
                 * this.getScaleX() 是隐藏部分的宽度
                 */
                if (this.getScaleX() >= mMenuWidth / 2){
                    this.smoothScrollTo(mMenuWidth,0);
                } else {
                    this.smoothScrollTo(0,0);
                }
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);

    }
}

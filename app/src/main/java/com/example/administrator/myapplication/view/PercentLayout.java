package com.example.administrator.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.R;

/**
 * PercentLayout
 * Created by zhuyingxin at 2016/2/2 21:00.
 * QQ: 657036139
 */
public class PercentLayout extends ViewGroup {

    public PercentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            PercentLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int left= (int) (getMeasuredWidth()*lp.x+getPaddingLeft()-child.getMeasuredWidth()/2);
            int top= (int) (getMeasuredHeight()*lp.y+getPaddingTop()-child.getMeasuredHeight()/2);
            int right=left+child.getMeasuredWidth();
            int bottom=top+child.getMeasuredHeight();

            child.layout(left, top, right, bottom);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {

        float x;
        float y;

        /***
         * 从xml文件加载属性的时候会使用
         */
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.my_attrs);
            x = a.getFloat(R.styleable.my_attrs_x, 0);
            y = a.getFloat(R.styleable.my_attrs_y, 0);
            a.recycle();
        }

        /***
         * 代码动态new的时候
         **/
        public LayoutParams(int width, int height, float x, float y) {
            super(width, height);
            this.x = x;
            this.y = y;
        }

        /**
         * 默认调用
         **/
        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {

        return new PercentLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new PercentLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new PercentLayout.LayoutParams(p);
    }
}

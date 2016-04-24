package com.example.administrator.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * FlowLayout
 * Created by zhuyingxin at 2016/2/1 20:47.
 * QQ: 657036139
 */
public class FlowLayout extends ViewGroup {

    private List<Info> list = new ArrayList<>();

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /***
     * 考虑窗口的子控件的布局问题
     *
     * @param changed 是否改变
     * @param l       左
     * @param t       上
     * @param r       右
     * @param b       下
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(list.get(i).left, list.get(i).top, list.get(i).right, list.get(i).bottom);

        }
    }


    /**
     * 考虑布局窗口尺寸问题
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int sizeW = MeasureSpec.getSize(widthMeasureSpec);
        int sizeH = MeasureSpec.getSize(heightMeasureSpec);
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        int modeH = MeasureSpec.getMode(heightMeasureSpec);
//        MeasureSpec.makeMeasureSpec(sizeW,modeW);
        int measureWidth = 0, measureHeight = 0;
        int lineWidth = 0, lineHeight = 0;
        for (int i = 0; i < childCount; i++) {
            list.add(new Info());
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childWidth < sizeW) {/***没有换行的情况**/
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            } else { /***换行的情况*/
                measureWidth = Math.max(measureWidth, lineWidth);
                measureHeight += lineHeight;
                /***重置**/
                lineWidth = childWidth;
                lineHeight = childHeight;
            }

            list.get(i).left=lineWidth-childWidth;
            list.get(i).right=lineWidth;
            list.get(i).top=measureHeight;
            list.get(i).bottom=measureHeight+childHeight;
        }
        measureHeight+=lineHeight;
        measureWidth=Math.max(measureWidth,lineWidth);
        super.onMeasure(MeasureSpec.EXACTLY == modeW ? sizeW : measureWidth, MeasureSpec.EXACTLY == modeH ? sizeH : measureHeight);

    }

    /**如果没有这上方法会62行报错，无法转换成MarginLayoutParams*/
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    class Info {
        int left, right, top, bottom;
    }
}

package com.example.administrator.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.myapplication.config.Config;

/**
 * SideView
 * Created by zhuyingxin at 2016/1/27 23:06.
 * QQ: 657036139
 */
public class SideView extends View {

    private char[] cs = Config.ALPHAS;
    private Paint paint;
    private int index = -1;
    private int height;

    public SideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(25);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        height = getHeight();

        String text;
        for (int i = 0, j = cs.length; i < j; i++) {
            text = cs[i] + "";
            int x = (int) ((width - paint.measureText(text)) / 2);
            int y = (i + 1) * height / (j + 1);
            paint.setColor(i == index ? Color.RED : Color.BLACK);
            paint.setTextSize(i == index ? 30 : 25);
            canvas.drawText(text, x, y, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventY = event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundColor(0x00000000);
                index = -1;
                listener.onActionUp();
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                setBackgroundColor(Color.DKGRAY);
                index = (int) (cs.length * eventY / height);
                listener.onActionDown(cs[index]);
                break;
        }
        invalidate();
        return true;
    }

    public  interface OnTouchDownUpListener{
        void onActionDown(char c);
        void onActionUp();
    }
    private OnTouchDownUpListener listener;
    public void setOnTouchDownUpListener(OnTouchDownUpListener listener){
        this.listener = listener;
    }
}

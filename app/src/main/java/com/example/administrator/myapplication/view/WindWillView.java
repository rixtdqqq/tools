//package com.example.administrator.myapplication.view;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Matrix;
//import android.graphics.Paint;
//import android.graphics.RectF;
//import android.util.TypedValue;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//
//import com.example.administrator.myapplication.R;
//
///**
// * WindWillView
// * Created by zhuyingxin at 2016/1/31 20:50.
// * QQ: 657036139
// */
//public class WindWillView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
//
//    private int w, h;
//    private Bitmap bj;
//    private Bitmap windWill;
//    private Bitmap point;
//    /***
//     * 缩放比
//     */
//    private float percentW, percentH;
//    private SurfaceHolder holder;
//    private Canvas canvas;
//    /***
//     * 风车是否旋转
//     */
//    private boolean isRun = true;
//
//    public WindWillView(Context context) {
//        super(context);
//        holder = getHolder();
//        holder.addCallback(this);
//        w = context.getResources().getDisplayMetrics().widthPixels;
//        h = context.getResources().getDisplayMetrics().heightPixels;
//        initPhoto();
//    }
//
//    /***
//     * 初始化图片
//     */
//    private void initPhoto() {
//        /**得到背景图片的参数*/
//        TypedValue value = new TypedValue();
////        getResources().openRawResource(R.drawable.bj1, value);
//        /**设置目标的option*/
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        /***设置目标的缩放比*/
//        options.inTargetDensity = value.density;
//        bj = BitmapFactory.decodeResource(getResources(), R.drawable.bjbj, options);
//        windWill = BitmapFactory.decodeResource(getResources(), R.drawable.img_windmill, options);
//        point = BitmapFactory.decodeResource(getResources(), R.drawable.img_point, options);
//        percentW = bj.getWidth() / w;
//        percentH = bj.getHeight() / h;
//
//        int width = (int) (250 / percentW);
//        windWill = Bitmap.createScaledBitmap(windWill, 2 * width, 2 * width, true);
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        new Thread(this).start();
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        isRun = false;
//    }
//
//    @Override
//    public void run() {
//        int rotate = 0;
//        while (isRun) {
//            synchronized (this) {
//                /***将画布锁起来*/
//                canvas = holder.lockCanvas();
//                try {
//                    if (canvas != null) {
//                        Paint paint = new Paint();
//                        paint.setAntiAlias(true);
//                        /****对图片进行过滤*/
//                        paint.setFilterBitmap(true);
//                        RectF rectF = new RectF(0, 0, w, h);
//                        canvas.drawBitmap(bj, null, rectF, paint);
//                        int dx = (int) (250 / percentW);
//                        int dy = (int) (500 / percentH);
//                        Matrix matrix = new Matrix();
//                        matrix.postRotate(rotate += 2 % 360, windWill.getWidth() / 2, windWill.getHeight() / 2);
//                        matrix.postTranslate(0, dy - windWill.getHeight() / 2);
//                        canvas.drawBitmap(windWill, matrix, paint);
//                        canvas.drawBitmap(point, dx - point.getWidth() / 2, dy - point.getHeight() / 2, paint);
//                        Thread.sleep(2);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (canvas != null) {
//                        holder.unlockCanvasAndPost(canvas);
//                    }
//
//                }
//            }
//        }
//    }
//
//}

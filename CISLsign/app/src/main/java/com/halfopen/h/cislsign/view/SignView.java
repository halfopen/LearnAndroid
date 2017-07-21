package com.halfopen.h.cislsign.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by h on 2017/7/21.
 */

public class SignView extends View {

    private float startAngle=120;
    private float sweepAngle=300;
    private RectF oval;
    private int len;
    private int radius;
    private Paint paint;
    private Boolean useCenter;
    // 刻度经过角度范围
    private float targetAngle = 300;

    public boolean isSign() {
        return isSign;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }

    private boolean isSign=false;



    public void refresh(){
        Log.d("flag--","refresh(SignView.java:42)-->>"+this.isSign);
        postInvalidate();

    }

    /**
     * 用来初始化画笔等
     * @param context
     * @param attrs
     */
    public SignView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint =new Paint();
        //设置画笔颜色
        paint.setColor(Color.WHITE);
        //设置画笔抗锯齿
        paint.setAntiAlias(true);
        //让画出的图形是空心的(不填充)
        paint.setStyle(Paint.Style.STROKE);

        useCenter = false;
    }

    /**
     * 用来测量限制view为正方形
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //以最小值为正方形的长
        len=Math.min(width,height);

        radius = len/2;

        //实例化矩形
        oval=new RectF(0,0,len,len);
        //设置测量高度和宽度（必须要调用，不然无效果）
        setMeasuredDimension(len, len);
    }

    /**
     * 实现各种绘制功能
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆弧的方法
        canvas.drawArc(oval, startAngle, sweepAngle, useCenter,paint);
        //画刻度线的方法
        drawViewLine(canvas);
        //画文字
        drawScoreText(canvas);
    }

    private void drawViewLine(Canvas canvas) {

        //先保存之前canvas的内容
        canvas.save();
        //移动canvas(X轴移动距离，Y轴移动距离)
        canvas.translate(radius,radius);
        //旋转坐标系
        canvas.rotate(30);
        Paint linePatin=new Paint();
        //设置画笔颜色
        linePatin.setColor(Color.WHITE);
        //线宽
        linePatin.setStrokeWidth(2);
        //设置画笔抗锯齿
        linePatin.setAntiAlias(true);
        //确定每次旋转的角度
        float rotateAngle=sweepAngle/100;
        //绘制有色部分的画笔
        Paint targetLinePatin=new Paint();
        targetLinePatin.setColor(Color.GREEN);
        targetLinePatin.setStrokeWidth(2);
        targetLinePatin.setAntiAlias(true);
        //记录已经绘制过的有色部分范围
        float hasDraw=0;
        for(int i=0;i<=100;i++){
            if(hasDraw<=targetAngle&&targetAngle!=0){//需要绘制有色部分的时候
                //画一条刻度线
                canvas.drawLine(0,radius,0,radius-40,targetLinePatin);
            }else {//不需要绘制有色部分
                //画一条刻度线
                canvas.drawLine(0,radius,0,radius-40,linePatin);
            }
            //累计绘制过的部分
            hasDraw+=rotateAngle;
            //旋转
            canvas.rotate(rotateAngle);
        }

        //操作完成后恢复状态
        canvas.restore();
    }

    public void change(){
        isSign = !isSign;

        postInvalidate();
    }

    /**
     * 绘制小圆和文本的方法，小圆颜色同样渐变
     * @param canvas
     */
    private void drawScoreText(Canvas canvas) {
        //先绘制一个小圆
        Paint smallPaint = new Paint();
        //smallPaint.setAlpha(50);
        if(isSign)smallPaint.setARGB(50, 236, 241, 243);
        else smallPaint.setARGB(50,0,250,0);
        // 画小圆指定圆心坐标，半径，画笔即可
        int smallRadius=radius-60;
        canvas.drawCircle(radius, radius, radius - 60, smallPaint);
        //绘制文本
        Paint textPaint=new Paint();
        //设置文本居中对齐
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(smallRadius/2);
        //score需要通过计算得到
        if(isSign)canvas.drawText("签出",radius,radius,textPaint);
        else canvas.drawText("签入",radius,radius,textPaint);
//        //绘制分，在分数的右上方
//        textPaint.setTextSize(smallRadius/6);
//        canvas.drawText("分",radius+smallRadius/2,radius-smallRadius/4,textPaint);
        //绘制点击优化在分数的下方
        textPaint.setTextSize(smallRadius/6);
        canvas.drawText("点击进行操作",radius,radius+smallRadius/2,textPaint);

    }
}

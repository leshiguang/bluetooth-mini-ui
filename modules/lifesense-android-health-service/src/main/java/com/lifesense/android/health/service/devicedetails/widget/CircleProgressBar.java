package com.lifesense.android.health.service.devicedetails.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.lifesense.android.health.service.util.ScreenUtils;


/**
 * Author:  winds
 * Email:   heardown@163.com
 * Date:    2019/6/3.
 * Desc:    圆形进度条
 */
public class CircleProgressBar extends View {

    private int mMaxProgress = 100;
    private int mProgress = 0;
    private int mCircleLineStrokeWidth = 15;

    private RectF mRectF;
    private Paint mPaint;
    private Paint mCirclePaint;
    private int width;
    private int height;

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mRectF = new RectF();

        mPaint = new Paint();
        mCircleLineStrokeWidth = ScreenUtils.dpInt2px(getContext(), 4);

        // 设置画笔相关属性
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mCircleLineStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0XFFEEEEEE);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStrokeWidth(mCircleLineStrokeWidth);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        gradient = new SweepGradient(0, 0, new int[]{Color.parseColor("#5FC4D1"), Color.parseColor("#299FB5")}, null);
        mCirclePaint.setShader(gradient);
    }

    SweepGradient gradient;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = this.getMeasuredWidth();
        height = this.getMeasuredHeight();
        if (width != height) {
            int min = Math.min(width, height);
            width = height = min;
        }

        mRectF.left = mCircleLineStrokeWidth / 2; // 左上角x
        mRectF.top = mCircleLineStrokeWidth / 2; // 左上角y
        mRectF.right = width - mCircleLineStrokeWidth / 2;
        // 左下角x
        mRectF.bottom = height - mCircleLineStrokeWidth / 2; // 右下角y
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制圆圈，进度条背景
        canvas.drawArc(mRectF, 0, 360, false, mPaint);

        canvas.drawArc(mRectF, 270, ((float) mProgress / mMaxProgress) * 360, false, mCirclePaint);
    }

    public void setCircleColor(int color) {
        setCircleColor(new SweepGradient(0, 0, new int[]{color, color}, null));
    }

    public void setCircleColor(SweepGradient gradient) {
        this.gradient = gradient;
        mCirclePaint.setShader(this.gradient);
        invalidate();
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        this.invalidate();
    }
}

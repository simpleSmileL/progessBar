package com.gb.progressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import com.gb.progressbar.R;

/**
 * 自定义圆形进度条 Created by simpleSmile on 2017/10/13.
 */

public class RoundProgessBar extends ProgessBarone {

  private int mRaduio = dp2px(30);
  private int maxPaintWidth;

  public RoundProgessBar(Context context) {
    this(context, null);
  }

  public RoundProgessBar(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RoundProgessBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    mReachHight = (int) (mReachHight * 2.5f);

    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundProgessBar);
    mRaduio = (int) ta.getDimension(R.styleable.RoundProgessBar_raduio, mRaduio);
    ta.recycle();

    mpaint.setStyle(Paint.Style.STROKE);
    mpaint.setAntiAlias(true);
    mpaint.setDither(true);
    mpaint.setStrokeCap(Paint.Cap.ROUND);
  }

  @Override protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    maxPaintWidth = Math.max(mReachHight, mUnReachHiight);
    int expect = mRaduio * 2 + maxPaintWidth + getPaddingRight() + getPaddingLeft();

    int width = resolveSize(expect, widthMeasureSpec);
    int hight = resolveSize(expect, heightMeasureSpec);

    int realWidth = Math.min(width, hight);

    mRaduio = (realWidth - getPaddingLeft() - getPaddingRight() - maxPaintWidth) / 2;

    setMeasuredDimension(realWidth, realWidth);
  }

  @Override protected synchronized void onDraw(Canvas canvas) {
    String text = getProgress() + "%";
    float textWidth = mpaint.measureText(text);
    float textHight = (mpaint.ascent() + mpaint.descent()) / 2;

    canvas.save();
    canvas.translate(getPaddingLeft() + maxPaintWidth / 2, getPaddingRight() + maxPaintWidth / 2);
    mpaint.setStyle(Paint.Style.STROKE);

    //先画没到的部分
    mpaint.setColor(mProBarUnreachColoe);
    mpaint.setStrokeWidth(mUnReachHiight);
    canvas.drawCircle(mRaduio, mRaduio, mRaduio, mpaint);

    //画到了的部分
    mpaint.setColor(mProBarReachColoe);
    mpaint.setStrokeWidth(mReachHight);

    float sweeangle = getProgress() * 1.0f / getMax() * 360;
    canvas.drawArc(new RectF(0, 0, mRaduio * 2, mRaduio * 2), 0, sweeangle, false, mpaint);

    //画文字
    mpaint.setStyle(Paint.Style.FILL);
    mpaint.setColor(mTextColor);
    canvas.drawText(text, mRaduio - textWidth / 2, mRaduio - textHight, mpaint);

    canvas.restore();
  }
}

package com.gb.progressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;
import com.gb.progressbar.R;

/**
 * 自定义进度条
 * Created by simpleSmile on 2017/10/12.
 */

public class ProgessBarone extends ProgressBar {
  private static final int DEFAULT_TEXT_SIZE = 20;
  private static final int DEFAULT_TEXT_COLOR = 0xFF0000;
  private static final int DEFAULT_COLOR_UNREACH = 0xFF00FF;
  private static final int DEFAULT_COLOR_REACH = 0xFFFF00;
  private static final int DEFAULT_HIGHT_REACH = 2;
  private static final int DEFAULT_HIGHT_UNREACH = 2;
  private static final int DEFAULT_TEXT_OFFSET = 10;

  protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
  protected int mTextColor = DEFAULT_TEXT_COLOR;
  protected int mProBarUnreachColoe = DEFAULT_COLOR_UNREACH;
  protected int mProBarReachColoe = DEFAULT_COLOR_REACH;
  protected int mReachHight = dp2px(DEFAULT_HIGHT_REACH);
  protected int mUnReachHiight = dp2px(DEFAULT_HIGHT_UNREACH);
  protected int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

  protected Paint mpaint = new Paint();
  private int realWidth;

  public ProgessBarone(Context context) {
    this(context, null);

  }

  public ProgessBarone(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
    obtainStylesAttres(attrs);
  }

  public ProgessBarone(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    obtainStylesAttres(attrs);
  }

  /**
   * 获取自定义属性
   */
  private void obtainStylesAttres(AttributeSet attrs) {

    TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ProgessBarone);

    mTextSize = (int) ta.getDimension(R.styleable.ProgessBarone_text_size, mTextSize);

    mTextColor = ta.getColor(R.styleable.ProgessBarone_text_color, mTextColor);

    mTextOffset = (int) ta.getDimension(R.styleable.ProgessBarone_text_offset, mTextOffset);

    mProBarReachColoe =
        ta.getColor(R.styleable.ProgessBarone_progess_reach_color, mProBarReachColoe);

    mProBarUnreachColoe =
        ta.getColor(R.styleable.ProgessBarone_progess_unreach_color, mProBarUnreachColoe);

    mReachHight = (int) ta.getDimension(R.styleable.ProgessBarone_progess_reach_hight, mReachHight);

    mUnReachHiight =
        (int) ta.getDimension(R.styleable.ProgessBarone_progess_unreach_hight, mUnReachHiight);

    ta.recycle();

    mpaint.setTextSize(mTextSize);
  }

  @Override protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //  int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int widthVal = MeasureSpec.getSize(widthMeasureSpec);

    int hightVal = getHightVal(heightMeasureSpec);

    setMeasuredDimension(widthVal, hightVal);

    realWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
  }

  @Override protected synchronized void onDraw(Canvas canvas) {

    canvas.save();

    //画文字前面部分
    canvas.translate(getPaddingLeft(), getHeight() / 2);

    boolean noNeedUnReach = false;

    String text = getProgress() + "%";
    int textWidth = (int) mpaint.measureText(text);

    float radio = getProgress() * 1.0f / getMax();

    float progessX = radio * realWidth;

    float endX = progessX - mTextOffset / 2;

    if(progessX + textWidth > realWidth){

      progessX = realWidth -textWidth;
      noNeedUnReach = true;

    }

    if (endX > 0) {
      mpaint.setColor(mProBarReachColoe);
      mpaint.setStrokeWidth(mReachHight);
      canvas.drawLine(0, 0, endX, 0, mpaint);
    }

    //画文字的部分
    mpaint.setColor(mTextColor);
    mpaint.setStrokeWidth(mTextSize);
    int y = (int) -((mpaint.descent() + mpaint.ascent())/2);
    canvas.drawText(text,progessX,y,mpaint);

    //画文字后面部分
    if(!noNeedUnReach){

      float start = progessX + mTextOffset/2 + textWidth;
      mpaint.setColor(mProBarUnreachColoe);
      mpaint.setStrokeWidth(mUnReachHiight);
      canvas.drawLine(start,0,realWidth,0,mpaint);
    }


    canvas.restore();
  }

  private int getHightVal(int heightMeasureSpec) {
    int result = 0;
    int mode = MeasureSpec.getMode(heightMeasureSpec);
    int size = MeasureSpec.getSize(heightMeasureSpec);

    if (mode == MeasureSpec.EXACTLY) {

      result = size;
    } else {
      int textHight = (int) (mpaint.descent() - mpaint.ascent());
      result =
          getPaddingBottom() + getPaddingTop() + Math.max(Math.max(mUnReachHiight, mReachHight),
              Math.abs(textHight));
      if (mode == MeasureSpec.AT_MOST) {

        result = Math.min(size, result);
      }
    }

    return result;
  }

  protected int dp2px(int dpVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
        getResources().getDisplayMetrics());
  }

  protected int sp2px(int spVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal,
        getResources().getDisplayMetrics());
  }
}

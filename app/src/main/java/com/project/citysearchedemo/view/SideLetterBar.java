package com.project.citysearchedemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.project.citysearchedemo.R;
import com.project.citysearchedemo.utils.DisplayUtil;

/**
 * 包名:      com.project.citysearchedemo.view
 * 文件名:    SideLetterBar
 * 创建者:    hello
 * 创建时间:  2016/12/9 10:07
 * 描述:      侧面字母选择条
 */
public class SideLetterBar extends View {
    private static final String mLettersShow[] = {"当前","A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z","#"};

    private Paint mPaint;
    private int mLetterWidth;
    private float mLetterHeight;
    private int lastIndex = -1;//标记上次的触摸字母的索引
    private Context mContext;


    public SideLetterBar(Context context) {
        super(context);
    }

    public SideLetterBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    private void init(Context context) {
        //创建画笔，并设置画笔抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置画笔颜色
        mPaint.setColor(Color.BLACK);
        //画笔绘制文字大小
        mPaint.setTextSize(DisplayUtil.dip2px(context,14));
        //设置文字居中
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * 当view发生变化的时候会调用该方法
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //得到一个字母要显示的宽高
        mLetterWidth = getMeasuredWidth();
        mLetterHeight = getMeasuredHeight()*1f / mLettersShow.length;
    }

    /**
     * 开始绘制文字
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mLettersShow.length; i++) {
            int start = mLetterWidth / 2;
            float end = mLetterHeight / 2 + getTextHeight(mLettersShow[i]) / 2 + i * mLetterHeight;
            mPaint.setColor(lastIndex==i?Color.BLUE:mContext.getResources().getColor(R.color.alpha_gray));
            canvas.drawText(mLettersShow[i],start,end,mPaint);
        }

    }

    /**
     * 获取文字高度
     * @return
     */
    private int getTextHeight(String text) {
        Rect bounds = new Rect();
        mPaint.getTextBounds(text,0,text.length(),bounds);
        return bounds.height();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
               switch (event.getAction()){
                          case MotionEvent.ACTION_DOWN:
                          case MotionEvent.ACTION_MOVE:
                              float touchY = event.getY();
                              int index = (int)(touchY / mLetterHeight);//得到当前索引
                              if(lastIndex != index){//判断上次点击的索引是否跟本次相同
                                  if(index>=0 && index<mLettersShow .length){
                                      //判断触摸的范围是否在所有字母的高度之内
                                      if(mOnTouchListeber!=null){
                                          mOnTouchListeber.onTouch(mLettersShow[index]);
                                      }
                                  }
                              }
                              lastIndex = index;
                              break;
                          case MotionEvent.ACTION_UP:
                              //滑动触目事件消失，标记的位置索引就要进行重置
                              mOnTouchListeber.dismiss();
                              lastIndex = -1;
                              break;
                          default:
                              break;
                      }
        //刷新界面进行从新绘制
        invalidate();
        return true;
    }

    private OnTouchListeber mOnTouchListeber;
    public void setOnTouchListeber(OnTouchListeber listeber){
        mOnTouchListeber = listeber;
    }
    public interface OnTouchListeber{
        void onTouch(String letter);
        void dismiss();
    }
}

package com.example.cheng.viewdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by cheng on 2018/1/27.
 */

public class HorizontalScrollViewEx extends ViewGroup {
    private int mChildIndex;
    private int mChildWidth;
    private int mChildrenSize;

    private int mLastX = 0;
    private int mLastY = 0;
    private int mLastInterceptX = 0;
    private int mLastIntercetpY = 0;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HorizontalScrollViewEx(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        if (mScroller == null){
            mScroller = new Scroller(getContext());
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastInterceptX;
                int deltaY = y - mLastIntercetpY;

                if (Math.abs(deltaX)>Math.abs(deltaY)){
                    intercepted = true;
                }else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;

        }
        mLastIntercetpY = y;
        mLastInterceptX = x;
        mLastY = y;
        mLastX = x;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x-mLastX;
                int deltaY = x-mLastY;
                scrollBy(-deltaX,0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity)>=50){
                    mChildIndex = xVelocity>0?mChildIndex-1:mChildIndex+1;
                }else {
                    mChildIndex = (scrollX+mChildWidth/2)/mChildWidth;
                }
                mChildIndex = Math.max(0,Math.min(mChildIndex,mChildrenSize-1));
                int dx = mChildIndex*mChildWidth-scrollX;
                smoothScrollBy(dx,0);
                mVelocityTracker.clear();
                break;

        }
        mLastY = y;
        mLastX = x;
        return true;
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(),0,dx,dy);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    //假设所有View宽高一致，ViewGroup padding和子View margin暂不处理
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();


        measureChildren(widthMeasureSpec,heightMeasureSpec);

        if (childCount==0){
            setMeasuredDimension(0,0);
        }else if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode==MeasureSpec.AT_MOST){
            int childWidth = getChildAt(0).getMeasuredWidth();
            int childHeight = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(childWidth*childCount,childHeight);
        }else if (widthSpecMode == MeasureSpec.AT_MOST){
            int childWidth = getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(childWidth*childCount,heightSpecSize);
        }else if(heightSpecMode == MeasureSpec.AT_MOST){
            int childHeight = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(widthSpecSize,childHeight);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int childCount =getChildCount();
        int childLeft = 0;
        mChildrenSize = childCount;

        for (int j=0 ; j<childCount ; j++){
            View childView = getChildAt(j);
            mChildWidth =childView.getWidth();
            if (childView.getVisibility()!=View.GONE){
                childView.layout(childLeft,0,childLeft+childView.getWidth(),childView.getHeight());
            }
            childLeft+=childView.getWidth();
        }
    }
}

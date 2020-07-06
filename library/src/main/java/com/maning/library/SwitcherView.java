package com.maning.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;

import androidx.annotation.AnimRes;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by maning on 16/7/17.
 * 垂直滚动的广告栏
 */
public class SwitcherView extends TextSwitcher implements ViewSwitcher.ViewFactory, View.OnTouchListener {

    private static final String TAG = "--------------";

    private Handler handler = new Handler();
    //默认文字大小
    private int defaultTextSize = 14;
    //数据源
    private ArrayList<String> dataSource = new ArrayList<>();
    //滚动的位置
    private int currentIndex = 0;
    //文字大小
    private int textSize = defaultTextSize;
    //默认颜色
    private int textColor = 0xFF000000;
    //时间周期
    private int timePeriod = 3000;
    //标记暂停滚动
    private boolean flag = true;
    //滚动的View
    private ArrayList<TextView> textViews = new ArrayList<>();
    //定时任务
    private ScheduledExecutorService mScheduledExecutorService;

    private Paint textPaint = new Paint();
    private int oneTextHeight;


    public SwitcherView(Context context) {
        this(context, null);
    }

    public SwitcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SwitcherView);
        //获取文字颜色
        textColor = ta.getColor(R.styleable.SwitcherView_switcherTextColor, textColor);
        //获取延时时间
        timePeriod = ta.getInt(R.styleable.SwitcherView_switcherRollingTime, timePeriod);
        //获取文字大小
        textSize = ta.getDimensionPixelSize(R.styleable.SwitcherView_switcherTextSize, sp2px(defaultTextSize));
        textSize = px2sp(textSize);

        ta.recycle();

        setOnTouchListener(this);

        textPaint.setAntiAlias(true);
        textPaint.setTextSize(sp2px(textSize));
        oneTextHeight = getTextHeight("中文123ABC", textPaint) + dp2px(6);
    }

    @Override
    public View makeView() {
        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        textView.setTextColor(textColor);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);
        if (textViews == null) {
            textViews = new ArrayList<>();
        }
        textViews.add(textView);
        return textView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int computedWidth = resolveMeasured(widthMeasureSpec, MeasureSpec.getSize(widthMeasureSpec));
        int computedHeight = resolveMeasured(heightMeasureSpec, oneTextHeight);
        computedHeight += getPaddingTop();
        computedHeight += getPaddingBottom();
        int width = computedWidth - getPaddingLeft() - getPaddingRight();
        int height = computedHeight - getPaddingTop() - getPaddingBottom();
        if (textViews != null && textViews.size() > 0) {
            for (int i = 0; i < textViews.size(); i++) {
                TextView textView = textViews.get(i);
                LayoutParams layoutParams = new LayoutParams(width, height);
                textView.setLayoutParams(layoutParams);
            }
        }
        setMeasuredDimension(computedWidth, computedHeight);

    }

    private int resolveMeasured(int measureSpec, int desired) {
        int result;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.UNSPECIFIED:
                result = desired;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(specSize, desired);
                break;
            case MeasureSpec.EXACTLY:
            default:
                result = specSize;
        }
        return result;
    }


    private void updateTextSwitcher(boolean flag) {
        if (dataSource != null && dataSource.size() > 0 && flag) {
            this.setText(dataSource.get(currentIndex++));
            if (currentIndex > dataSource.size() - 1) {
                currentIndex = 0;
            }
        }
    }

    /**
     * 设置间隔时间（毫秒）
     *
     * @param time 毫秒
     */
    public void setTime(int time) {
        this.timePeriod = time;
        endTimer();
        startTimer();
    }

    /**
     * 设置数据源
     *
     * @param dataSource
     */
    public void setResource(ArrayList<String> dataSource) {
        if (dataSource == null) {
            dataSource = new ArrayList<>();
        }
        this.dataSource = dataSource;
        currentIndex = 0;
    }

    /**
     * 开始滚动
     */
    public void startRolling() {
        flag = true;
        if (mScheduledExecutorService == null) {
            this.setFactory(this);
            //默认动画
            setAnimationBottom2Top();
            startTimer();
        }
    }

    private void startTimer() {
        if (mScheduledExecutorService == null) {
            //定时任务
            mScheduledExecutorService = Executors.newScheduledThreadPool(2);
            // 循环任务，按照上一次任务的发起时间计算下一次任务的开始时间
            mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            updateTextSwitcher(flag);
                        }
                    });
                }
            }, 0, timePeriod, TimeUnit.MILLISECONDS);
        }
    }

    private void endTimer() {
        if (mScheduledExecutorService != null) {
            mScheduledExecutorService.shutdown();
            mScheduledExecutorService = null;
        }
    }

    /**
     * 停止滚动
     */
    public void stopRolling() {
        flag = false;
    }

    /**
     * 手动滚动到下一个
     */
    public void rollingToNext() {
        updateTextSwitcher(true);
    }

    /**
     * 设置进入的动画
     *
     * @param resourceID
     */
    public void setInAnimation(@AnimRes int resourceID) {
        this.setInAnimation(getContext(), resourceID);
    }

    /**
     * 设置出去的动画
     *
     * @param resourceID
     */
    public void setOutAnimation(@AnimRes int resourceID) {
        this.setOutAnimation(getContext(), resourceID);
    }

    /**
     * 设置默认动画-从上到下
     */
    public void setAnimationTop2Bottom() {
        this.setInAnimation(getContext(), R.anim.m_switcher_top_in);
        this.setOutAnimation(getContext(), R.anim.m_switcher_top_out);
    }

    /**
     * 设置默认动画-从下到上
     */
    public void setAnimationBottom2Top() {
        this.setInAnimation(getContext(), R.anim.m_switcher_bottom_in);
        this.setOutAnimation(getContext(), R.anim.m_switcher_bottom_out);
    }

    /**
     * 设置默认动画-从左到右
     */
    public void setAnimationLeft2Right() {
        this.setInAnimation(getContext(), R.anim.m_switcher_left_in);
        this.setOutAnimation(getContext(), R.anim.m_switcher_left_out);
    }

    /**
     * 设置默认动画-从右到左
     */
    public void setAnimationRight2Left() {
        this.setInAnimation(getContext(), R.anim.m_switcher_right_in);
        this.setOutAnimation(getContext(), R.anim.m_switcher_right_out);
    }

    /**
     * 获取当前的Item
     *
     * @return
     */
    public String getCurrentItem() {
        if (dataSource != null && dataSource.size() > 0) {
            return dataSource.get(getCurrentIndex());
        } else {
            return "";
        }
    }

    /**
     * 获取当前View
     *
     * @return
     */
    public int getCurrentIndex() {
        int index = currentIndex - 1;
        if (index < 0) {
            index = dataSource.size() - 1;
        }
        return index;
    }

    /**
     * 销毁View
     */
    public void destroySwitcher() {
        handler.removeCallbacksAndMessages(null);
        handler = null;
        endTimer();
        if (dataSource != null && dataSource.size() > 0) {
            dataSource.clear();
            dataSource = null;
        }
        if (textViews != null && textViews.size() > 0) {
            textViews.clear();
            textViews = null;
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            flag = false;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            flag = true;
        }
        return false;
    }


    //--------------------其它工具方法------------------

    private int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

    private int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());

    }

    private int px2sp(float pxValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int getTextHeight(String text, Paint textPaint) {
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }


}
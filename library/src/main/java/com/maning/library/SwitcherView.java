package com.maning.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by maning on 16/7/17.
 * 垂直滚动的广告栏
 */
public class SwitcherView extends TextSwitcher implements ViewSwitcher.ViewFactory, View.OnTouchListener {

    private static final String TAG = "--------------";

    private Handler handler = new Handler();
    private Timer timer;   //计时器

    private ArrayList<String> dataSource = new ArrayList<>();  //数据源
    private int currentIndex = 0;   //滚动的位置
    private int textSize = 0;    //文字大小
    private static final int defaultTextSize = 16;    //默认文字大小
    private int textColor = 0xFF000000; //默认颜色
    private int timePeriod = 3000;  //时间周期
    private boolean flag = true;

    private TextView tView;


    public SwitcherView(Context context) {
        this(context, null);
    }

    public SwitcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SwitcherView);
        textColor = ta.getColor(R.styleable.SwitcherView_switcherTextColor, textColor);
        timePeriod = ta.getInt(R.styleable.SwitcherView_switcherRollingTime, timePeriod);
        textSize = ta.getDimensionPixelSize(R.styleable.SwitcherView_switcherTextSize, sp2px(defaultTextSize));
        Log.i("----", textSize + "");
        textSize = px2sp(textSize);
        Log.i("----", textSize + "");
        ta.recycle();

        setOnTouchListener(this);
    }

    @Override
    public View makeView() {
        tView = new TextView(getContext());
        tView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tView.setTextColor(textColor);
        tView.setSingleLine();
        tView.setPadding(10, 5, 10, 5);
        tView.setEllipsize(TextUtils.TruncateAt.END);
        return tView;
    }

    public void setResource(ArrayList<String> dataSource) {
        this.dataSource = dataSource;
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (flag) {
                        updateTextSwitcher();
                    }
                }
            });
        }
    };

    private void updateTextSwitcher() {
        if (dataSource != null && dataSource.size() > 0) {
            this.setText(dataSource.get(currentIndex++));
            if (currentIndex > dataSource.size() - 1) {
                currentIndex = 0;
            }
        }
    }

    public void startRolling() {
        if (timer == null) {
            this.setFactory(this);
            this.setInAnimation(getContext(), R.anim.m_switcher_vertical_in);
            this.setOutAnimation(getContext(), R.anim.m_switcher_vertical_out);
            timer = new Timer();
            timer.schedule(timerTask, 0, timePeriod);
        }
    }

    public String getCurrentItem() {
        if (dataSource != null && dataSource.size() > 0) {
            return dataSource.get(getCurrentIndex());
        } else {
            return "";
        }
    }

    public int getCurrentIndex() {
        int index = currentIndex - 1;
        if (index < 0) {
            index = dataSource.size() - 1;
        }
        return index;
    }

    public void destroySwitcher() {
        handler.removeCallbacksAndMessages(null);
        handler = null;
        if (timer != null) {
            timer.cancel();
            timerTask.cancel();
            timer = null;
            timerTask = null;
        }
        if (dataSource != null && dataSource.size() > 0) {
            dataSource.clear();
            dataSource = null;
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

    public int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

    public int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());

    }

    public int px2sp(float pxValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}
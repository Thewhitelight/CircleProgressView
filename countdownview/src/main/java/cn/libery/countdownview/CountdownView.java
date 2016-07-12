package cn.libery.countdownview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * 商品详情 倒计时
 * Created by Libery on 2016/7/12.
 * Email:libery.szq@qq.com
 */
public class CountDownView extends LinearLayout {

    private TextView dd, hh, mm, ss;

    private long mTimestamp;
    private CountDownHandler mHandler;

    public void setFinishListener(final FinishListener finishListener) {
        mFinishListener = finishListener;
    }

    private FinishListener mFinishListener;

    public long getTimeStamp() {
        return mTimestamp;
    }

    public void setTimeStamp(final long timestamp) {
        mTimestamp = timestamp * 1000;
    }

    public CountDownView(final Context context) {
        super(context);
    }

    public CountDownView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_count_down, this);
        dd = (TextView) contentView.findViewById(R.id.dd);
        hh = (TextView) contentView.findViewById(R.id.hh);
        mm = (TextView) contentView.findViewById(R.id.mm);
        ss = (TextView) contentView.findViewById(R.id.ss);
        mHandler = new CountDownHandler(this);
        mHandler.sendEmptyMessageDelayed(1, 1000);
    }

    private static class CountDownHandler extends Handler {
        private WeakReference<CountDownView> mReference;

        public CountDownHandler(final CountDownView reference) {
            mReference = new WeakReference<>(reference);
        }

        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            CountDownView view = mReference.get();
            if (view != null) {
                view.refreshTimestamp();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    public void refreshTimestamp() {
        long days, hours, minutes, seconds;
        long diff = mTimestamp - System.currentTimeMillis();
        days = diff / (1000 * 60 * 60 * 24);
        hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        seconds = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
        dd.setText(days + "天");
        hh.setText(String.valueOf(hours));
        mm.setText(String.valueOf(minutes));
        ss.setText(String.valueOf(seconds));
        if (mFinishListener != null && diff <= 0) {
            mFinishListener.finish();
            mHandler.removeCallbacksAndMessages(null);
        }
        if (diff > 0) {
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    }

    public interface FinishListener {
        void finish();
    }
}

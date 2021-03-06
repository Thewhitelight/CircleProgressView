package cn.libery.circleprogressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * Created by Libery on 2016/6/30.
 * Email:libery.szq@qq.com
 */
public class CircleProgressView extends View {
    //region geter seter
    public int getCircleWidth() {
        return circleWidth;
    }

    public void setCircleWidth(final int circleWidth) {
        this.circleWidth = circleWidth;
    }

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(final int circleColor) {
        this.circleColor = circleColor;
    }

    public int getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(final int circleRadius) {
        this.circleRadius = circleRadius;
    }

    public int getSecondCircleColor() {
        return secondCircleColor;
    }

    public void setSecondCircleColor(final int secondCircleColor) {
        this.secondCircleColor = secondCircleColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(final int textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(final int textColor) {
        this.textColor = textColor;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public float getProgress() {
        return progress;
    }

    public Paint.Style getPaintStyle() {
        return secondCirclePaintStyle;
    }

    public void setPaintStyle(final Paint.Style secondCirclePaintStyle) {
        this.secondCirclePaintStyle = secondCirclePaintStyle;
        secondCirclePaint.setStyle(secondCirclePaintStyle);
    }

    public int getProgressMode() {
        return progressMode;
    }

    //endregion
    private Paint circlePaint;
    private Paint textPaint;
    private Paint secondCirclePaint;

    private int circleWidth = 30;
    private int circleColor = Color.RED;
    private int circleRadius = 40;

    private Paint.Style secondCirclePaintStyle = Paint.Style.STROKE;
    private int secondCircleColor = Color.WHITE;

    private int textSize = 20;
    private int textColor = Color.BLACK;
    private Paint.FontMetricsInt fontMetrics;

    public void setProgress(final float progress) {
        this.progress = progress;
        if (progress > maxProgress && mFinishListener != null) {
            mFinishListener.finish();
        }
        invalidate();
    }

    private float progress = 0;
    private int maxProgress = 1000;


    public void setProgressMode(@ProgressMode final int progressMode) {
        this.progressMode = progressMode;
        if (progressMode == ASC) {
            maxProgress = 100;
        }
    }


    private int progressMode = DESC;

    @Retention(RetentionPolicy.RUNTIME)
    @IntDef({ASC, DESC})
    public @interface ProgressMode {
    }

    public static final int ASC = 0;

    public static final int DESC = 1;

    public void setFinishListener(final FinishListener finishListener) {
        mFinishListener = finishListener;
    }

    private FinishListener mFinishListener;

    private MyHandler mHandler = new MyHandler(this);

    public CircleProgressView(final Context context) {
        super(context);
        init(context, null);
    }

    public CircleProgressView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(final Context context, final AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        circleWidth = (int) array.getDimension(R.styleable.CircleProgressView_circle_width, circleWidth);
        circleColor = array.getColor(R.styleable.CircleProgressView_circle_color, circleColor);
        circleRadius = (int) array.getDimension(R.styleable.CircleProgressView_circle_radius, circleRadius);
        secondCircleColor = array.getColor(R.styleable.CircleProgressView_second_circle_color, secondCircleColor);
        textColor = array.getColor(R.styleable.CircleProgressView_text_color, textColor);
        textSize = (int) array.getDimension(R.styleable.CircleProgressView_text_size, textSize);
        if (textSize > circleRadius) {
            textSize = circleRadius / 3;
        }
        intCirclePaint();
        intTextPaint();
        initSecondCirclePaint();
        array.recycle();
    }

    private void initSecondCirclePaint() {
        secondCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        secondCirclePaint.setStyle(secondCirclePaintStyle);
        secondCirclePaint.setColor(secondCircleColor);
        secondCirclePaint.setStrokeWidth(circleWidth);
    }

    private void intCirclePaint() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(circleColor);
        circlePaint.setStrokeWidth(circleWidth);
    }

    private void intTextPaint() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        fontMetrics = textPaint.getFontMetricsInt();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        final RectF rectF = new RectF(circleWidth, circleWidth, canvas.getWidth() - circleWidth, canvas.getHeight() -
                circleWidth);
        canvas.drawOval(rectF, circlePaint);
        if (secondCirclePaintStyle == Paint.Style.FILL) {
            canvas.drawArc(rectF, -90, (progress / maxProgress) * 360, true, secondCirclePaint);
        } else {
            canvas.drawArc(rectF, -90, (progress / maxProgress) * 360, false, secondCirclePaint);
        }

        int baseline = (int) ((rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        if (progress == 0) {
            canvas.drawText(String.valueOf(maxProgress / 1000), rectF.centerX(), baseline, textPaint);
        } else if (progress == maxProgress && maxProgress != 100) {
            canvas.drawText(String.valueOf(maxProgress / 1000), rectF.centerX(), baseline, textPaint);
        } else {
            if (progressMode == ASC) {
                if (progress > 100) {
                    progress = 100;
                }
                canvas.drawText(String.valueOf(progress)+"%", rectF.centerX(), baseline, textPaint);
            } else {
                canvas.drawText(String.valueOf((int) (maxProgress - progress) / 1000 + 1), rectF.centerX(), baseline,
                        textPaint);
            }
        }
    }

    public void setMaxProgress(final int maxProgress) {
        this.maxProgress = maxProgress;
        mHandler.sendEmptyMessage(1);
    }

    private void addProgress() {
        progress += 100;
        invalidate();
        mHandler.sendEmptyMessageDelayed(1, 100);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }

    public void finish() {
        if (mFinishListener != null) {
            mFinishListener.finish();
        }
    }

    public void reset() {
        progress = 0;
        mHandler.sendEmptyMessage(1);
    }

    public interface FinishListener {
        void finish();
    }

    private static class MyHandler extends Handler {
        WeakReference<CircleProgressView> mCircleReference;

        MyHandler(CircleProgressView circleProgressView) {
            mCircleReference = new WeakReference<>(circleProgressView);
        }

        @Override
        public void handleMessage(Message msg) {
            final CircleProgressView circleProgressView = mCircleReference.get();
            if (circleProgressView == null) {
                return;
            }
            if (msg.what == 1) {
                circleProgressView.addProgress();
            }
            if (circleProgressView.getProgress() >= circleProgressView.getMaxProgress()) {
                circleProgressView.finish();
                circleProgressView.reset();
                removeMessages(1);
            }
        }
    }

}

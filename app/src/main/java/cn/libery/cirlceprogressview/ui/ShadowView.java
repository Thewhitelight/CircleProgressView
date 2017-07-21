package cn.libery.cirlceprogressview.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.ThumbnailUtils;
import android.util.AttributeSet;
import android.view.View;

import cn.libery.cirlceprogressview.R;

/**
 * Created by Libery on 2016/9/26.
 * Email:libery.szq@qq.com
 */

public class ShadowView extends View implements View.OnClickListener {

    public void setOnSelectListener(final onSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    private onSelectListener mOnSelectListener;

    public interface onSelectListener {

        void onSelect(boolean isSelect);
    }

    private Paint mPaint;
    private boolean isSelect = true;

    public ShadowView(final Context context) {
        super(context);
        init();
    }

    public ShadowView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressLint("NewApi")
    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFFcc2900);
        drawShadow();
        setOnClickListener(this);
    }

    private void drawShadow() {
        mPaint.setShadowLayer(20, 0, 0, 0xFFcc2900);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF();
        rectF.top = 100;
        rectF.left = 100;
        rectF.right = 300;
        rectF.bottom = 300;
        canvas.drawRoundRect(rectF, 20, 20, mPaint);
        Bitmap bitmap = drawableToBitmap(R.mipmap.ic_launcher);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, 200, 200);
        canvas.drawBitmap(bitmap, 100, 100, mPaint);
    }

    public Bitmap drawableToBitmap(int drawableRes) {
        return BitmapFactory.decodeResource(getResources(), drawableRes);
    }

    @Override
    public void onClick(final View v) {
        if (isSelect) {
            isSelect = false;
            mPaint.clearShadowLayer();
        } else {
            isSelect = true;
            drawShadow();
        }
        invalidate();
        mOnSelectListener.onSelect(isSelect);
    }

}

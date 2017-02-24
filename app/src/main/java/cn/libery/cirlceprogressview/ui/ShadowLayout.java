package cn.libery.cirlceprogressview.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Libery on 2016/11/7.
 * Email:libery.szq@qq.com
 */

public class ShadowLayout extends RelativeLayout {

    private Paint mPaint;

    public ShadowLayout(final Context context) {
        super(context);
        init();
    }

    public ShadowLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShadowLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFFcc2900);
        drawShadow();
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
    }

}

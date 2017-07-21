package cn.libery.cirlceprogressview.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.media.ThumbnailUtils;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

import cn.libery.cirlceprogressview.R;

/**
 * Created by Libery on 2017/7/20.
 * Email:libery.szq@qq.com
 */

public class CustomView extends View implements View.OnClickListener {

    private Paint mPaint;
    private Shader linearGradient;
    private Shader radialGradient;
    private Shader sweepGradient;
    private Shader bitmapShader1;

    public CustomView(final Context context) {
        this(context, null);
    }

    public CustomView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linearGradient = new LinearGradient(0, 0, 200, 200, Color.parseColor("#E91E63"),
                Color.BLUE, Shader.TileMode.CLAMP);
        radialGradient = new RadialGradient(100, 100, 100, Color.parseColor("#E91E63"),
                Color.BLUE, Shader.TileMode.CLAMP);
        sweepGradient = new SweepGradient(100, 100, Color.parseColor("#E91E63"),
                Color.BLUE);
        Bitmap b = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(getResources(), R.drawable.ic_msg),
                200, 200);
        bitmapShader1 = new BitmapShader(b, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(100, 100, 100, mPaint);
        PathEffect pathEffect = new DashPathEffect(new float[]{10, 20}, 0);
        mPaint.setStrokeWidth(100);
        mPaint.setPathEffect(pathEffect);
        canvas.drawLine(200, 0, 200, 200, mPaint);
    }

    @Override
    public void onClick(final View v) {
        switch (new Random().nextInt(5)) {
            case 0:
                mPaint.setShader(sweepGradient);
                break;
            case 1:
                mPaint.setShader(linearGradient);
                break;
            case 2:
                mPaint.setShader(radialGradient);
                break;
            case 3:
                mPaint.setShader(bitmapShader1);
                break;
            case 4:
                final Shader composeShader = new ComposeShader(bitmapShader1,
                        new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.ic_msg_unread),
                                Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
                        , intToMode(new Random().nextInt(16)));
                mPaint.setShader(composeShader);
                break;
        }
        invalidate();
    }

    public PorterDuff.Mode intToMode(int val) {
        switch (val) {
            default:
            case 0:
                return PorterDuff.Mode.CLEAR;
            case 1:
                return PorterDuff.Mode.SRC;
            case 2:
                return PorterDuff.Mode.DST;
            case 3:
                return PorterDuff.Mode.SRC_OVER;
            case 4:
                return PorterDuff.Mode.DST_OVER;
            case 5:
                return PorterDuff.Mode.SRC_IN;
            case 6:
                return PorterDuff.Mode.DST_IN;
            case 7:
                return PorterDuff.Mode.SRC_OUT;
            case 8:
                return PorterDuff.Mode.DST_OUT;
            case 9:
                return PorterDuff.Mode.SRC_ATOP;
            case 10:
                return PorterDuff.Mode.DST_ATOP;
            case 11:
                return PorterDuff.Mode.XOR;
            case 16:
                return PorterDuff.Mode.DARKEN;
            case 17:
                return PorterDuff.Mode.LIGHTEN;
            case 13:
                return PorterDuff.Mode.MULTIPLY;
            case 14:
                return PorterDuff.Mode.SCREEN;
            case 12:
                return PorterDuff.Mode.ADD;
            case 15:
                return PorterDuff.Mode.OVERLAY;
        }
    }

}

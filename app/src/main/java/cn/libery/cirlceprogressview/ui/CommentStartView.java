package cn.libery.cirlceprogressview.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import cn.libery.cirlceprogressview.R;

/**
 * Created by Libery on 2016/8/24.
 * Email:libery.szq@qq.com
 */
public class CommentStartView extends View {

    public void setStartNum(final int startNum) {
        this.startNum = startNum;
    }

    private int startNum = 0;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CommentStartView(final Context context) {
        super(context);
    }

    public CommentStartView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 5; i++) {
            if (i < startNum) {
                Drawable d = getContext().getResources().getDrawable(R.drawable.ic_star_sel);
                canvas.drawBitmap(drawableToBitmap(d), (d.getIntrinsicWidth() + 5) * i, 0, paint);
            } else {
                Drawable d = getContext().getResources().getDrawable(R.drawable.ic_star_unsel);
                canvas.drawBitmap(drawableToBitmap(d), (d.getIntrinsicWidth() + 5) * i, 0, paint);
            }
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

}

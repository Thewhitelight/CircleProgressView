package cn.libery.cirlceprogressview.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
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
                Bitmap b= drawableToBitmap(R.drawable.ic_star_sel);
                canvas.drawBitmap(b, (b.getWidth() + 5) * i, 0, paint);
            } else {
                Bitmap b= drawableToBitmap(R.drawable.ic_star_unsel);
                canvas.drawBitmap(b, (b.getWidth() + 5) * i, 0, paint);
            }
        }
    }

    private Bitmap drawableToBitmap(int drawableRes) {
        return BitmapFactory.decodeResource(getResources(), drawableRes);
    }

}

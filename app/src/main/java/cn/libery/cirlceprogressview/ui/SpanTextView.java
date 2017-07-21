package cn.libery.cirlceprogressview.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Libery on 2016/9/28.
 * Email:libery.szq@qq.com
 */

public class SpanTextView extends android.support.v7.widget.AppCompatTextView {

    public SpanTextView(Context context) {
        super(context);
    }

    public SpanTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SpanTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Spannable spannable(CharSequence text) {
        return new Spannable(text);
    }

    public final class Spannable {

        private final Map<Object, Integer> spans = new HashMap<>();
        SpannableStringBuilder ss;
        CharSequence text;

        Spannable(CharSequence text) {
            this.text = text;
            this.ss = new SpannableStringBuilder(text);
        }

        /**
         * 设置绝对大小
         *
         * @param size
         * @return
         */
        public Spannable absoluteSize(int size) {
            return absoluteSize(size, false);
        }

        /**
         * 设置绝对大小
         *
         * @param size
         * @param dip
         * @return
         */
        public Spannable absoluteSize(int size, boolean dip) {
            return absoluteSize(size, dip, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * 设置绝对大小
         *
         * @param size
         * @param dip
         * @param flags
         * @return
         */
        public Spannable absoluteSize(int size, boolean dip, int flags) {
            spans.put(new AbsoluteSizeSpan(size, dip), flags);
            return this;
        }

        /**
         * 设置相对大小
         *
         * @param proportion
         * @return
         */
        public Spannable relativeSize(float proportion) {
            return relativeSize(proportion, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * 设置相对大小
         *
         * @param proportion
         * @param flags
         * @return
         */
        public Spannable relativeSize(float proportion, int flags) {
            spans.put(new RelativeSizeSpan(proportion), flags);
            return this;
        }

        /**
         * 设置文字颜色
         *
         * @param color
         * @return
         */
        public Spannable color(int color) {
            return color(color, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * 设置文字颜色
         *
         * @param color
         * @param flags
         * @return
         */
        public Spannable color(int color, int flags) {
            spans.put(new ForegroundColorSpan(color), flags);
            return this;
        }

        /**
         * 设置背景
         *
         * @param drawable
         * @return
         */
        public Spannable background(Drawable drawable) {
            return background(drawable, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * @param drawable
         * @param flags
         * @return
         */
        public Spannable background(Drawable drawable, int flags) {
            return background(drawable, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), flags);
        }

        /**
         * @param drawable
         * @param flags
         * @return
         */
        public Spannable background(Drawable drawable, final int w, final int h, int flags) {
            drawable.setBounds(0, 0, w, h);
            spans.put(new ImageSpan(drawable) {
                @Override
                public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y,
                                 int bottom, Paint paint) {
                    String sequence = text.subSequence(start, end).toString();
                    Rect boundText = new Rect();
                    paint.getTextBounds(sequence, 0, sequence.length(), boundText);
                    Drawable b = getDrawable();
                    Rect bounds = b.getBounds();

                    int w = bounds.width() < boundText.width() ? boundText.width() : bounds.width();
                    int h = bounds.height();

                    float fontHeight = boundText.height();
                    int maxHeight = (int) ((bottom - y) * 2 + fontHeight);
                    if (h < fontHeight) {
                        h = (int) fontHeight;
                    } else {
                        if (h > maxHeight) {
                            h = maxHeight;
                        }
                    }

                    b.setBounds(0, 0, w, h);
                    canvas.save();
                    int transY = top + (bottom - top - maxHeight) + (maxHeight - bounds.height()) / 2;
                    canvas.translate(x, transY);
                    b.draw(canvas);
                    canvas.restore();
                    paint.setColor(Color.BLACK);
                    canvas.drawText(sequence, x + (bounds.width() - boundText.width()) / 2, y, paint);
                }
            }, flags);
            return this;
        }

        /**
         * 背景色
         *
         * @param color
         * @return
         */
        public Spannable backgroundColor(int color) {
            return backgroundColor(color, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * 背景色
         *
         * @param color
         * @param flags
         * @return
         */
        public Spannable backgroundColor(int color, int flags) {
            spans.put(new BackgroundColorSpan(color), flags);
            return this;
        }

        /**
         * 设置链接
         *
         * @param url
         * @return
         */
        public Spannable url(String url) {
            return url(url, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * 设置链接
         *
         * @param url
         * @param flags
         * @return
         */
        public Spannable url(String url, int flags) {
            spans.put(new URLSpan(url), flags);
            return this;
        }

        /**
         * 设置字体
         *
         * @param family
         * @return
         */
        public Spannable typeface(String family) {
            return typeface(family, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * 设置字体
         *
         * @param family
         * @param flags
         * @return
         */
        public Spannable typeface(String family, int flags) {
            spans.put(new TypefaceSpan(family), flags);
            return this;
        }

        /**
         * 字体样式
         *
         * @param style {@link android.graphics.Typeface}
         * @return
         */
        public Spannable type(int style) {
            return type(style, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * 字体样式
         *
         * @param style
         * @param flags
         * @return
         */
        public Spannable type(int style, int flags) {
            spans.put(new StyleSpan(style), flags);
            return this;
        }

        /**
         * @param family
         * @param style
         * @param size
         * @param color
         * @param linkColor
         * @return
         */
        public Spannable textAppearance(String family, int style, int size,
                                        ColorStateList color, ColorStateList linkColor) {
            return textAppearance(family, style, size, color, linkColor, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * @param family
         * @param style
         * @param size
         * @param color
         * @param linkColor
         * @param flags
         * @return
         */
        public Spannable textAppearance(String family, int style, int size, ColorStateList color, ColorStateList
                linkColor, int flags) {
            spans.put(new TextAppearanceSpan(family, style, size, color, linkColor), flags);
            return this;
        }

        /**
         * 下划线
         *
         * @return
         */
        public Spannable underline() {
            return underline(Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * 下划线
         *
         * @param flags
         * @return
         */
        public Spannable underline(int flags) {
            spans.put(new UnderlineSpan(), flags);
            return this;
        }

        /**
         * 删除线
         *
         * @return
         */
        public Spannable strikeThrough() {
            return strikeThrough(Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * 删除线
         *
         * @param flags
         * @return
         */
        public Spannable strikeThrough(int flags) {
            spans.put(new StrikethroughSpan(), flags);
            return this;
        }

        /**
         * 下标
         *
         * @return
         */
        public Spannable subscript() {
            return subscript(Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * 下标
         *
         * @param flags
         * @return
         */
        public Spannable subscript(int flags) {
            spans.put(new SubscriptSpan(), flags);
            return this;
        }

        /**
         * 上标
         *
         * @return
         */
        public Spannable superscript() {
            return superscript(Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * 上标
         *
         * @param flags
         * @return
         */
        public Spannable superscript(int flags) {
            spans.put(new SuperscriptSpan(), flags);
            return this;
        }

        /**
         * x缩放
         *
         * @param proportion
         * @return
         */
        public Spannable scaleX(float proportion) {
            return scaleX(proportion, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * x缩放
         *
         * @param proportion
         * @param flags
         * @return
         */
        public Spannable scaleX(float proportion, int flags) {
            spans.put(new ScaleXSpan(proportion), flags);
            return this;
        }

        /**
         * @param gapWidth
         * @param color
         * @return
         */
        public Spannable bullet(int gapWidth, int color) {
            return bullet(gapWidth, color, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * @param gapWidth {@link android.text.style.BulletSpan}
         * @param color
         * @param flags
         * @return
         * @hide
         */
        public Spannable bullet(int gapWidth, int color, int flags) {
            spans.put(new BulletSpan(gapWidth, color), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * @param drawable
         * @return
         */
        public Spannable image(Drawable drawable) {
            return image(drawable, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * @param drawable
         * @param flags
         * @return
         */
        public Spannable image(Drawable drawable, int flags) {
            return image(drawable, 0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), flags);
        }

        /**
         * @param drawable
         * @param l
         * @param t
         * @param r
         * @param b
         * @param flags
         * @return
         */
        public Spannable image(Drawable drawable, int l, int t, int r, int b, int flags) {
            drawable.setBounds(l, t, r, b);
            spans.put(new ImageSpan(drawable), flags);
            return this;
        }

        public Spannable click(OnClickListener listener) {
            return click(listener, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        public Spannable click(final OnClickListener listener, int flags) {
            spans.put(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    if (listener != null) {
                        listener.onClick(text);
                    }
                }
            }, flags);
            SpanTextView.this.setMovementMethod(LinkMovementMethod.getInstance());
            return this;
        }

        /**
         * @param obj
         * @return
         */
        public Spannable span(Object obj) {
            return span(obj, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * @param obj
         * @param flags
         * @return
         */
        public Spannable span(Object obj, int flags) {
            spans.put(obj, flags);
            return this;
        }

        public Set<Object> spans() {
            return spans.keySet();
        }

        public void remove(Object obj) {
            ss.removeSpan(obj);
        }

        public void clear() {
            Iterator<Object> iterator = spans.keySet().iterator();
            while (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }

        public TextView commit() {
            for (final Map.Entry<Object, Integer> next : spans.entrySet()) {
                ss.setSpan(next.getKey(), 0, ss.length(), next.getValue());
            }
            SpanTextView.this.append(ss);
            return SpanTextView.this;
        }
    }

    public interface OnClickListener {
        void onClick(CharSequence text);
    }

}

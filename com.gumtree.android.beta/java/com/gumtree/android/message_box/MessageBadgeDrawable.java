package com.gumtree.android.message_box;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.common.views.ThemeUtils;
import uk.co.senab.photoview.IPhotoView;

public class MessageBadgeDrawable extends Drawable {
    private static final int CENTER = 15;
    private static final int RX = 4;
    private static final int RY = 4;
    private float mBadgePadding;
    private Paint mBadgePaint;
    private String mCount = BuildConfig.FLAVOR;
    private Paint mTextPaint;
    private float mTextSize;
    private Rect mTxtRect = new Rect();
    private boolean mWillDraw;

    public MessageBadgeDrawable(Context context) {
        this.mTextSize = context.getResources().getDimension(2131231166);
        this.mBadgePadding = context.getResources().getDimension(2131230915);
        int color = ThemeUtils.getColor(context, 2130772477);
        this.mBadgePaint = new Paint();
        this.mBadgePaint.setColor(color);
        this.mBadgePaint.setAntiAlias(true);
        this.mBadgePaint.setStyle(Style.FILL);
        this.mTextPaint = new Paint();
        this.mTextPaint.setColor(-1);
        this.mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        this.mTextPaint.setTextSize(this.mTextSize);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setTextAlign(Align.CENTER);
    }

    public void draw(Canvas canvas) {
        if (this.mWillDraw) {
            Rect bounds = getBounds();
            float f = (float) (bounds.right - bounds.left);
            float min = ((Math.min(f, (float) (bounds.bottom - bounds.top)) / 2.0f) - IPhotoView.DEFAULT_MIN_SCALE) / 2.0f;
            min += IPhotoView.DEFAULT_MIN_SCALE;
            f = ((f - min) - IPhotoView.DEFAULT_MIN_SCALE) + 15.0f;
            canvas.drawRoundRect(new RectF(f - this.mBadgePadding, min - this.mBadgePadding, this.mBadgePadding + f, this.mBadgePadding + min), 4.0f, 4.0f, this.mBadgePaint);
            this.mTextPaint.getTextBounds(this.mCount, 0, this.mCount.length(), this.mTxtRect);
            canvas.drawText(this.mCount, f, min + (((float) (this.mTxtRect.bottom - this.mTxtRect.top)) / 2.0f), this.mTextPaint);
        }
    }

    public void setCount(int i) {
        this.mCount = Integer.toString(i);
        this.mWillDraw = i > 0;
        invalidateSelf();
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return 0;
    }
}

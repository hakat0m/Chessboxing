package com.gumtree.android.post_ad.gallery.crop;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

class HighlightView {
    private static final int ARGB_A = 125;
    private static final int ARGB_B = 50;
    private static final int ARGB_G = 50;
    private static final int ARGB_R = 50;
    private static final int DX = 10;
    private static final int DY = 10;
    private static final int FOUR = 4;
    public static final int GROW_BOTTOM_EDGE = 16;
    public static final int GROW_LEFT_EDGE = 2;
    public static final int GROW_NONE = 1;
    public static final int GROW_RIGHT_EDGE = 4;
    public static final int GROW_TOP_EDGE = 8;
    public static final int MOVE = 32;
    private static final int OUTLINE_PAINT_COLOR = -16777216;
    private static final float STROKE_WIDTH = 3.0f;
    private static final String TAG = "HighlightView";
    private static final int THREE = 3;
    private static final int TWO = 2;
    View mContext;
    RectF mCropRect;
    Rect mDrawRect;
    private final Paint mFocusPaint = new Paint();
    boolean mHidden;
    private RectF mImageRect;
    private float mInitialAspectRatio;
    boolean mIsFocused;
    private boolean mMaintainAspectRatio;
    Matrix mMatrix;
    private ModifyMode mMode = ModifyMode.None;
    private final Paint mNoFocusPaint = new Paint();
    private final Paint mOutlinePaint = new Paint();
    private Drawable mResizeDrawableHeight;
    private Drawable mResizeDrawableWidth;

    public HighlightView(View view) {
        this.mContext = view;
    }

    private void init() {
        this.mResizeDrawableHeight = ContextCompat.getDrawable(this.mContext.getContext(), 2130837759);
        this.mResizeDrawableWidth = ContextCompat.getDrawable(this.mContext.getContext(), 2130837759);
    }

    public boolean hasFocus() {
        return this.mIsFocused;
    }

    public void setFocus(boolean z) {
        this.mIsFocused = z;
    }

    public void setHidden(boolean z) {
        this.mHidden = z;
    }

    protected void draw(Canvas canvas) {
        if (!this.mHidden) {
            canvas.save();
            Path path = new Path();
            if (hasFocus()) {
                Rect rect = new Rect();
                this.mContext.getDrawingRect(rect);
                path.addRect(new RectF(this.mDrawRect), Direction.CW);
                this.mOutlinePaint.setColor(ContextCompat.getColor(this.mContext.getContext(), 2131492917));
                canvas.clipPath(path, Op.DIFFERENCE);
                canvas.drawRect(rect, hasFocus() ? this.mFocusPaint : this.mNoFocusPaint);
                canvas.restore();
                canvas.drawPath(path, this.mOutlinePaint);
                int i = this.mDrawRect.left + GROW_NONE;
                int i2 = this.mDrawRect.right + GROW_NONE;
                int i3 = this.mDrawRect.top + GROW_RIGHT_EDGE;
                int i4 = this.mDrawRect.bottom + THREE;
                int intrinsicWidth = this.mResizeDrawableWidth.getIntrinsicWidth() / TWO;
                int intrinsicHeight = this.mResizeDrawableWidth.getIntrinsicHeight() / TWO;
                int intrinsicHeight2 = this.mResizeDrawableHeight.getIntrinsicHeight() / TWO;
                int intrinsicWidth2 = this.mResizeDrawableHeight.getIntrinsicWidth() / TWO;
                int i5 = this.mDrawRect.left + ((this.mDrawRect.right - this.mDrawRect.left) / TWO);
                int i6 = this.mDrawRect.top + ((this.mDrawRect.bottom - this.mDrawRect.top) / TWO);
                this.mResizeDrawableWidth.setBounds(i - intrinsicWidth, i6 - intrinsicHeight, i + intrinsicWidth, i6 + intrinsicHeight);
                this.mResizeDrawableWidth.draw(canvas);
                this.mResizeDrawableWidth.setBounds(i2 - intrinsicWidth, i6 - intrinsicHeight, i2 + intrinsicWidth, i6 + intrinsicHeight);
                this.mResizeDrawableWidth.draw(canvas);
                this.mResizeDrawableHeight.setBounds(i5 - intrinsicWidth2, i3 - intrinsicHeight2, i5 + intrinsicWidth2, i3 + intrinsicHeight2);
                this.mResizeDrawableHeight.draw(canvas);
                this.mResizeDrawableHeight.setBounds(i5 - intrinsicWidth2, i4 - intrinsicHeight2, i5 + intrinsicWidth2, i4 + intrinsicHeight2);
                this.mResizeDrawableHeight.draw(canvas);
                return;
            }
            this.mOutlinePaint.setColor(OUTLINE_PAINT_COLOR);
            canvas.drawRect(this.mDrawRect, this.mOutlinePaint);
        }
    }

    public void setMode(ModifyMode modifyMode) {
        if (modifyMode != this.mMode) {
            this.mMode = modifyMode;
            this.mContext.invalidate();
        }
    }

    public int getHit(float f, float f2) {
        int i;
        Object obj = null;
        Rect computeLayout = computeLayout();
        Object obj2 = (f2 < ((float) computeLayout.top) - 20.0f || f2 >= ((float) computeLayout.bottom) + 20.0f) ? null : GROW_NONE;
        if (f >= ((float) computeLayout.left) - 20.0f && f < ((float) computeLayout.right) + 20.0f) {
            obj = GROW_NONE;
        }
        int i2 = (Math.abs(((float) computeLayout.left) - f) >= 20.0f || obj2 == null) ? GROW_NONE : THREE;
        if (Math.abs(((float) computeLayout.right) - f) < 20.0f && obj2 != null) {
            i2 |= GROW_RIGHT_EDGE;
        }
        if (Math.abs(((float) computeLayout.top) - f2) < 20.0f && r2 != null) {
            i2 |= GROW_TOP_EDGE;
        }
        if (Math.abs(((float) computeLayout.bottom) - f2) >= 20.0f || r2 == null) {
            i = i2;
        } else {
            i = i2 | GROW_BOTTOM_EDGE;
        }
        if (i == GROW_NONE && computeLayout.contains((int) f, (int) f2)) {
            return MOVE;
        }
        return i;
    }

    void handleMotion(int i, float f, float f2) {
        Rect computeLayout = computeLayout();
        if (i != GROW_NONE) {
            if (i == MOVE) {
                moveBy((this.mCropRect.width() / ((float) computeLayout.width())) * f, (this.mCropRect.height() / ((float) computeLayout.height())) * f2);
                return;
            }
            if ((i & 6) == 0) {
                f = 0.0f;
            }
            if ((i & 24) == 0) {
                f2 = 0.0f;
            }
            growBy((f * (this.mCropRect.width() / ((float) computeLayout.width()))) * ((float) ((i & TWO) != 0 ? -1 : GROW_NONE)), ((float) ((i & GROW_TOP_EDGE) != 0 ? -1 : GROW_NONE)) * (f2 * (this.mCropRect.height() / ((float) computeLayout.height()))));
        }
    }

    void moveBy(float f, float f2) {
        Rect rect = new Rect(this.mDrawRect);
        this.mCropRect.offset(f, f2);
        this.mCropRect.offset(Math.max(0.0f, this.mImageRect.left - this.mCropRect.left), Math.max(0.0f, this.mImageRect.top - this.mCropRect.top));
        this.mCropRect.offset(Math.min(0.0f, this.mImageRect.right - this.mCropRect.right), Math.min(0.0f, this.mImageRect.bottom - this.mCropRect.bottom));
        this.mDrawRect = computeLayout();
        rect.union(this.mDrawRect);
        rect.inset(-10, -10);
        this.mContext.invalidate();
    }

    void growBy(float f, float f2) {
        float f3;
        float f4;
        if (this.mMaintainAspectRatio) {
            if (f != 0.0f) {
                f2 = f / this.mInitialAspectRatio;
            } else if (f2 != 0.0f) {
                f = f2 * this.mInitialAspectRatio;
            }
        }
        RectF rectF = new RectF(this.mCropRect);
        if (f > 0.0f && rectF.width() + (2.0f * f) > this.mImageRect.width()) {
            f = (this.mImageRect.width() - rectF.width()) / 2.0f;
            if (this.mMaintainAspectRatio) {
                f3 = f / this.mInitialAspectRatio;
                f4 = f;
                if (f3 > 0.0f && rectF.height() + (2.0f * f3) > this.mImageRect.height()) {
                    f3 = (this.mImageRect.height() - rectF.height()) / 2.0f;
                    if (this.mMaintainAspectRatio) {
                        f4 = this.mInitialAspectRatio * f3;
                    }
                }
                rectF.inset(-f4, -f3);
                if (rectF.width() < 25.0f) {
                    rectF.inset((-(25.0f - rectF.width())) / 2.0f, 0.0f);
                }
                f3 = this.mMaintainAspectRatio ? 25.0f / this.mInitialAspectRatio : 25.0f;
                if (rectF.height() < f3) {
                    rectF.inset(0.0f, (-(f3 - rectF.height())) / 2.0f);
                }
                if (rectF.left < this.mImageRect.left) {
                    rectF.offset(this.mImageRect.left - rectF.left, 0.0f);
                } else if (rectF.right > this.mImageRect.right) {
                    rectF.offset(-(rectF.right - this.mImageRect.right), 0.0f);
                }
                if (rectF.top < this.mImageRect.top) {
                    rectF.offset(0.0f, this.mImageRect.top - rectF.top);
                } else if (rectF.bottom > this.mImageRect.bottom) {
                    rectF.offset(0.0f, -(rectF.bottom - this.mImageRect.bottom));
                }
                this.mCropRect.set(rectF);
                this.mDrawRect = computeLayout();
                this.mContext.invalidate();
            }
        }
        f3 = f2;
        f4 = f;
        f3 = (this.mImageRect.height() - rectF.height()) / 2.0f;
        if (this.mMaintainAspectRatio) {
            f4 = this.mInitialAspectRatio * f3;
        }
        rectF.inset(-f4, -f3);
        if (rectF.width() < 25.0f) {
            rectF.inset((-(25.0f - rectF.width())) / 2.0f, 0.0f);
        }
        if (this.mMaintainAspectRatio) {
        }
        if (rectF.height() < f3) {
            rectF.inset(0.0f, (-(f3 - rectF.height())) / 2.0f);
        }
        if (rectF.left < this.mImageRect.left) {
            rectF.offset(this.mImageRect.left - rectF.left, 0.0f);
        } else if (rectF.right > this.mImageRect.right) {
            rectF.offset(-(rectF.right - this.mImageRect.right), 0.0f);
        }
        if (rectF.top < this.mImageRect.top) {
            rectF.offset(0.0f, this.mImageRect.top - rectF.top);
        } else if (rectF.bottom > this.mImageRect.bottom) {
            rectF.offset(0.0f, -(rectF.bottom - this.mImageRect.bottom));
        }
        this.mCropRect.set(rectF);
        this.mDrawRect = computeLayout();
        this.mContext.invalidate();
    }

    public Rect getCropRect() {
        return new Rect((int) this.mCropRect.left, (int) this.mCropRect.top, (int) this.mCropRect.right, (int) this.mCropRect.bottom);
    }

    private Rect computeLayout() {
        RectF rectF = new RectF(this.mCropRect.left, this.mCropRect.top, this.mCropRect.right, this.mCropRect.bottom);
        this.mMatrix.mapRect(rectF);
        return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }

    public void invalidate() {
        this.mDrawRect = computeLayout();
    }

    public void setup(Matrix matrix, Rect rect, RectF rectF, boolean z) {
        this.mMatrix = new Matrix(matrix);
        this.mCropRect = rectF;
        this.mImageRect = new RectF(rect);
        this.mMaintainAspectRatio = z;
        this.mInitialAspectRatio = this.mCropRect.width() / this.mCropRect.height();
        this.mDrawRect = computeLayout();
        this.mFocusPaint.setARGB(ARGB_A, ARGB_R, ARGB_R, ARGB_R);
        this.mNoFocusPaint.setARGB(ARGB_A, ARGB_R, ARGB_R, ARGB_R);
        this.mOutlinePaint.setStrokeWidth(STROKE_WIDTH);
        this.mOutlinePaint.setStyle(Style.STROKE);
        this.mOutlinePaint.setAntiAlias(true);
        this.mMode = ModifyMode.None;
        init();
    }
}

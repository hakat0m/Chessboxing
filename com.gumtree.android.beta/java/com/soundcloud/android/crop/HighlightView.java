package com.soundcloud.android.crop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.os.Build.VERSION;
import android.util.TypedValue;
import android.view.View;
import uk.co.senab.photoview.IPhotoView;

class HighlightView {
    private static final int DEFAULT_HIGHLIGHT_COLOR = -13388315;
    public static final int GROW_BOTTOM_EDGE = 16;
    public static final int GROW_LEFT_EDGE = 2;
    public static final int GROW_NONE = 1;
    public static final int GROW_RIGHT_EDGE = 4;
    public static final int GROW_TOP_EDGE = 8;
    private static final float HANDLE_RADIUS_DP = 12.0f;
    public static final int MOVE = 32;
    private static final float OUTLINE_DP = 2.0f;
    RectF cropRect;
    Rect drawRect;
    private HandleMode handleMode = HandleMode.Changing;
    private final Paint handlePaint = new Paint();
    private float handleRadius;
    private int highlightColor;
    private RectF imageRect;
    private float initialAspectRatio;
    private boolean isFocused;
    private boolean maintainAspectRatio;
    Matrix matrix;
    private ModifyMode modifyMode = ModifyMode.None;
    private final Paint outlinePaint = new Paint();
    private float outlineWidth;
    private final Paint outsidePaint = new Paint();
    private boolean showCircle;
    private boolean showThirds;
    private View viewContext;

    public HighlightView(View view) {
        this.viewContext = view;
        initStyles(view.getContext());
    }

    private void initStyles(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.cropImageStyle, typedValue, true);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(typedValue.resourceId, R.styleable.CropImageView);
        try {
            this.showThirds = obtainStyledAttributes.getBoolean(R.styleable.CropImageView_showThirds, false);
            this.showCircle = obtainStyledAttributes.getBoolean(R.styleable.CropImageView_showCircle, false);
            this.highlightColor = obtainStyledAttributes.getColor(R.styleable.CropImageView_highlightColor, DEFAULT_HIGHLIGHT_COLOR);
            this.handleMode = HandleMode.values()[obtainStyledAttributes.getInt(R.styleable.CropImageView_showHandles, 0)];
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public void setup(Matrix matrix, Rect rect, RectF rectF, boolean z) {
        this.matrix = new Matrix(matrix);
        this.cropRect = rectF;
        this.imageRect = new RectF(rect);
        this.maintainAspectRatio = z;
        this.initialAspectRatio = this.cropRect.width() / this.cropRect.height();
        this.drawRect = computeLayout();
        this.outsidePaint.setARGB(125, 50, 50, 50);
        this.outlinePaint.setStyle(Style.STROKE);
        this.outlinePaint.setAntiAlias(true);
        this.outlineWidth = dpToPx(OUTLINE_DP);
        this.handlePaint.setColor(this.highlightColor);
        this.handlePaint.setStyle(Style.FILL);
        this.handlePaint.setAntiAlias(true);
        this.handleRadius = dpToPx(HANDLE_RADIUS_DP);
        this.modifyMode = ModifyMode.None;
    }

    private float dpToPx(float f) {
        return this.viewContext.getResources().getDisplayMetrics().density * f;
    }

    protected void draw(Canvas canvas) {
        canvas.save();
        Path path = new Path();
        this.outlinePaint.setStrokeWidth(this.outlineWidth);
        if (hasFocus()) {
            Rect rect = new Rect();
            this.viewContext.getDrawingRect(rect);
            path.addRect(new RectF(this.drawRect), Direction.CW);
            this.outlinePaint.setColor(this.highlightColor);
            if (isClipPathSupported(canvas)) {
                canvas.clipPath(path, Op.DIFFERENCE);
                canvas.drawRect(rect, this.outsidePaint);
            } else {
                drawOutsideFallback(canvas);
            }
            canvas.restore();
            canvas.drawPath(path, this.outlinePaint);
            if (this.showThirds) {
                drawThirds(canvas);
            }
            if (this.showCircle) {
                drawCircle(canvas);
            }
            if (this.handleMode == HandleMode.Always || (this.handleMode == HandleMode.Changing && this.modifyMode == ModifyMode.Grow)) {
                drawHandles(canvas);
                return;
            }
            return;
        }
        this.outlinePaint.setColor(-16777216);
        canvas.drawRect(this.drawRect, this.outlinePaint);
    }

    private void drawOutsideFallback(Canvas canvas) {
        canvas.drawRect(0.0f, 0.0f, (float) canvas.getWidth(), (float) this.drawRect.top, this.outsidePaint);
        canvas.drawRect(0.0f, (float) this.drawRect.bottom, (float) canvas.getWidth(), (float) canvas.getHeight(), this.outsidePaint);
        canvas.drawRect(0.0f, (float) this.drawRect.top, (float) this.drawRect.left, (float) this.drawRect.bottom, this.outsidePaint);
        canvas.drawRect((float) this.drawRect.right, (float) this.drawRect.top, (float) canvas.getWidth(), (float) this.drawRect.bottom, this.outsidePaint);
    }

    @SuppressLint({"NewApi"})
    private boolean isClipPathSupported(Canvas canvas) {
        if (VERSION.SDK_INT == 17) {
            return false;
        }
        if (VERSION.SDK_INT < 14 || VERSION.SDK_INT > 15 || !canvas.isHardwareAccelerated()) {
            return true;
        }
        return false;
    }

    private void drawHandles(Canvas canvas) {
        int i = this.drawRect.left + ((this.drawRect.right - this.drawRect.left) / GROW_LEFT_EDGE);
        int i2 = this.drawRect.top + ((this.drawRect.bottom - this.drawRect.top) / GROW_LEFT_EDGE);
        canvas.drawCircle((float) this.drawRect.left, (float) i2, this.handleRadius, this.handlePaint);
        canvas.drawCircle((float) i, (float) this.drawRect.top, this.handleRadius, this.handlePaint);
        canvas.drawCircle((float) this.drawRect.right, (float) i2, this.handleRadius, this.handlePaint);
        canvas.drawCircle((float) i, (float) this.drawRect.bottom, this.handleRadius, this.handlePaint);
    }

    private void drawThirds(Canvas canvas) {
        this.outlinePaint.setStrokeWidth(IPhotoView.DEFAULT_MIN_SCALE);
        float f = (float) ((this.drawRect.right - this.drawRect.left) / 3);
        float f2 = (float) ((this.drawRect.bottom - this.drawRect.top) / 3);
        canvas.drawLine(((float) this.drawRect.left) + f, (float) this.drawRect.top, ((float) this.drawRect.left) + f, (float) this.drawRect.bottom, this.outlinePaint);
        Canvas canvas2 = canvas;
        canvas2.drawLine((f * OUTLINE_DP) + ((float) this.drawRect.left), (float) this.drawRect.top, (f * OUTLINE_DP) + ((float) this.drawRect.left), (float) this.drawRect.bottom, this.outlinePaint);
        canvas.drawLine((float) this.drawRect.left, ((float) this.drawRect.top) + f2, (float) this.drawRect.right, ((float) this.drawRect.top) + f2, this.outlinePaint);
        canvas2 = canvas;
        canvas2.drawLine((float) this.drawRect.left, (f2 * OUTLINE_DP) + ((float) this.drawRect.top), (float) this.drawRect.right, (f2 * OUTLINE_DP) + ((float) this.drawRect.top), this.outlinePaint);
    }

    private void drawCircle(Canvas canvas) {
        this.outlinePaint.setStrokeWidth(IPhotoView.DEFAULT_MIN_SCALE);
        canvas.drawOval(new RectF(this.drawRect), this.outlinePaint);
    }

    public void setMode(ModifyMode modifyMode) {
        if (modifyMode != this.modifyMode) {
            this.modifyMode = modifyMode;
            this.viewContext.invalidate();
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
        int i2 = (Math.abs(((float) computeLayout.left) - f) >= 20.0f || obj2 == null) ? GROW_NONE : 3;
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
        if (i == MOVE) {
            moveBy((this.cropRect.width() / ((float) computeLayout.width())) * f, (this.cropRect.height() / ((float) computeLayout.height())) * f2);
            return;
        }
        if ((i & 6) == 0) {
            f = 0.0f;
        }
        if ((i & 24) == 0) {
            f2 = 0.0f;
        }
        growBy((f * (this.cropRect.width() / ((float) computeLayout.width()))) * ((float) ((i & GROW_LEFT_EDGE) != 0 ? -1 : GROW_NONE)), ((float) ((i & GROW_TOP_EDGE) != 0 ? -1 : GROW_NONE)) * (f2 * (this.cropRect.height() / ((float) computeLayout.height()))));
    }

    void moveBy(float f, float f2) {
        Rect rect = new Rect(this.drawRect);
        this.cropRect.offset(f, f2);
        this.cropRect.offset(Math.max(0.0f, this.imageRect.left - this.cropRect.left), Math.max(0.0f, this.imageRect.top - this.cropRect.top));
        this.cropRect.offset(Math.min(0.0f, this.imageRect.right - this.cropRect.right), Math.min(0.0f, this.imageRect.bottom - this.cropRect.bottom));
        this.drawRect = computeLayout();
        rect.union(this.drawRect);
        rect.inset(-((int) this.handleRadius), -((int) this.handleRadius));
        this.viewContext.invalidate(rect);
    }

    void growBy(float f, float f2) {
        float f3;
        float f4;
        if (this.maintainAspectRatio) {
            if (f != 0.0f) {
                f2 = f / this.initialAspectRatio;
            } else if (f2 != 0.0f) {
                f = f2 * this.initialAspectRatio;
            }
        }
        RectF rectF = new RectF(this.cropRect);
        if (f > 0.0f && rectF.width() + (OUTLINE_DP * f) > this.imageRect.width()) {
            f = (this.imageRect.width() - rectF.width()) / OUTLINE_DP;
            if (this.maintainAspectRatio) {
                f3 = f / this.initialAspectRatio;
                f4 = f;
                if (f3 > 0.0f && rectF.height() + (OUTLINE_DP * f3) > this.imageRect.height()) {
                    f3 = (this.imageRect.height() - rectF.height()) / OUTLINE_DP;
                    if (this.maintainAspectRatio) {
                        f4 = this.initialAspectRatio * f3;
                    }
                }
                rectF.inset(-f4, -f3);
                if (rectF.width() < 25.0f) {
                    rectF.inset((-(25.0f - rectF.width())) / OUTLINE_DP, 0.0f);
                }
                f3 = this.maintainAspectRatio ? 25.0f / this.initialAspectRatio : 25.0f;
                if (rectF.height() < f3) {
                    rectF.inset(0.0f, (-(f3 - rectF.height())) / OUTLINE_DP);
                }
                if (rectF.left < this.imageRect.left) {
                    rectF.offset(this.imageRect.left - rectF.left, 0.0f);
                } else if (rectF.right > this.imageRect.right) {
                    rectF.offset(-(rectF.right - this.imageRect.right), 0.0f);
                }
                if (rectF.top < this.imageRect.top) {
                    rectF.offset(0.0f, this.imageRect.top - rectF.top);
                } else if (rectF.bottom > this.imageRect.bottom) {
                    rectF.offset(0.0f, -(rectF.bottom - this.imageRect.bottom));
                }
                this.cropRect.set(rectF);
                this.drawRect = computeLayout();
                this.viewContext.invalidate();
            }
        }
        f3 = f2;
        f4 = f;
        f3 = (this.imageRect.height() - rectF.height()) / OUTLINE_DP;
        if (this.maintainAspectRatio) {
            f4 = this.initialAspectRatio * f3;
        }
        rectF.inset(-f4, -f3);
        if (rectF.width() < 25.0f) {
            rectF.inset((-(25.0f - rectF.width())) / OUTLINE_DP, 0.0f);
        }
        if (this.maintainAspectRatio) {
        }
        if (rectF.height() < f3) {
            rectF.inset(0.0f, (-(f3 - rectF.height())) / OUTLINE_DP);
        }
        if (rectF.left < this.imageRect.left) {
            rectF.offset(this.imageRect.left - rectF.left, 0.0f);
        } else if (rectF.right > this.imageRect.right) {
            rectF.offset(-(rectF.right - this.imageRect.right), 0.0f);
        }
        if (rectF.top < this.imageRect.top) {
            rectF.offset(0.0f, this.imageRect.top - rectF.top);
        } else if (rectF.bottom > this.imageRect.bottom) {
            rectF.offset(0.0f, -(rectF.bottom - this.imageRect.bottom));
        }
        this.cropRect.set(rectF);
        this.drawRect = computeLayout();
        this.viewContext.invalidate();
    }

    public Rect getScaledCropRect(float f) {
        return new Rect((int) (this.cropRect.left * f), (int) (this.cropRect.top * f), (int) (this.cropRect.right * f), (int) (this.cropRect.bottom * f));
    }

    private Rect computeLayout() {
        RectF rectF = new RectF(this.cropRect.left, this.cropRect.top, this.cropRect.right, this.cropRect.bottom);
        this.matrix.mapRect(rectF);
        return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }

    public void invalidate() {
        this.drawRect = computeLayout();
    }

    public boolean hasFocus() {
        return this.isFocused;
    }

    public void setFocus(boolean z) {
        this.isFocused = z;
    }
}

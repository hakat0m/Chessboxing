package com.gumtree.android.post_ad.gallery.crop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import uk.co.senab.photoview.IPhotoView;

abstract class ImageViewTouchBase extends ImageView {
    private static final int FOUR = 4;
    private static final int MATRIX_VALUES_SIZE = 9;
    static final float SCALE_RATE = 1.25f;
    private static final float SECOND_MATH_ARGUMENT = 3.0f;
    private static final String TAG = "ImageViewTouchBase";
    protected Matrix mBaseMatrix;
    protected final RotateBitmap mBitmapDisplayed;
    private final Matrix mDisplayMatrix;
    protected Handler mHandler;
    private final float[] mMatrixValues;
    float mMaxZoom;
    private Runnable mOnLayoutRunnable;
    private Recycler mRecycler;
    protected Matrix mSuppMatrix;
    int mThisHeight;
    int mThisWidth;

    public ImageViewTouchBase(Context context) {
        super(context);
        this.mBitmapDisplayed = new RotateBitmap(null);
        this.mBaseMatrix = new Matrix();
        this.mSuppMatrix = new Matrix();
        this.mHandler = new Handler();
        this.mThisWidth = -1;
        this.mThisHeight = -1;
        this.mDisplayMatrix = new Matrix();
        this.mMatrixValues = new float[MATRIX_VALUES_SIZE];
        init();
    }

    public ImageViewTouchBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBitmapDisplayed = new RotateBitmap(null);
        this.mBaseMatrix = new Matrix();
        this.mSuppMatrix = new Matrix();
        this.mHandler = new Handler();
        this.mThisWidth = -1;
        this.mThisHeight = -1;
        this.mDisplayMatrix = new Matrix();
        this.mMatrixValues = new float[MATRIX_VALUES_SIZE];
        init();
    }

    public void setRecycler(Recycler recycler) {
        this.mRecycler = recycler;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mThisWidth = i3 - i;
        this.mThisHeight = i4 - i2;
        Runnable runnable = this.mOnLayoutRunnable;
        if (runnable != null) {
            this.mOnLayoutRunnable = null;
            runnable.run();
        }
        if (this.mBitmapDisplayed.getBitmap() != null) {
            getProperBaseMatrix(this.mBitmapDisplayed, this.mBaseMatrix);
            setImageMatrix(getImageViewMatrix());
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != FOUR || keyEvent.getRepeatCount() != 0) {
            return super.onKeyDown(i, keyEvent);
        }
        keyEvent.startTracking();
        return true;
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != FOUR || !keyEvent.isTracking() || keyEvent.isCanceled() || getScale() <= IPhotoView.DEFAULT_MIN_SCALE) {
            return super.onKeyUp(i, keyEvent);
        }
        zoomTo(IPhotoView.DEFAULT_MIN_SCALE);
        return true;
    }

    public void setImageBitmap(Bitmap bitmap) {
        setImageBitmap(bitmap, 0);
    }

    private void setImageBitmap(Bitmap bitmap, int i) {
        super.setImageBitmap(bitmap);
        Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.setDither(true);
        }
        Bitmap bitmap2 = this.mBitmapDisplayed.getBitmap();
        this.mBitmapDisplayed.setBitmap(bitmap);
        this.mBitmapDisplayed.setRotation(i);
        if (bitmap2 != null && bitmap2 != bitmap && this.mRecycler != null) {
            this.mRecycler.recycle(bitmap2);
        }
    }

    public void clear() {
        setImageBitmapResetBase(null, true);
    }

    public void setImageBitmapResetBase(Bitmap bitmap, boolean z) {
        setImageRotateBitmapResetBase(new RotateBitmap(bitmap), z);
    }

    public void setImageRotateBitmapResetBase(RotateBitmap rotateBitmap, boolean z) {
        if (getWidth() <= 0) {
            this.mOnLayoutRunnable = ImageViewTouchBase$$Lambda$1.lambdaFactory$(this, rotateBitmap, z);
            return;
        }
        if (rotateBitmap.getBitmap() != null) {
            getProperBaseMatrix(rotateBitmap, this.mBaseMatrix);
            setImageBitmap(rotateBitmap.getBitmap(), rotateBitmap.getRotation());
        } else {
            this.mBaseMatrix.reset();
            setImageBitmap(null);
        }
        if (z) {
            this.mSuppMatrix.reset();
        }
        setImageMatrix(getImageViewMatrix());
        this.mMaxZoom = maxZoom();
    }

    /* synthetic */ void lambda$setImageRotateBitmapResetBase$0(RotateBitmap rotateBitmap, boolean z) {
        setImageRotateBitmapResetBase(rotateBitmap, z);
    }

    protected void center(boolean z, boolean z2) {
        float f = 0.0f;
        if (this.mBitmapDisplayed.getBitmap() != null) {
            int height;
            Matrix imageViewMatrix = getImageViewMatrix();
            RectF rectF = new RectF(0.0f, 0.0f, (float) this.mBitmapDisplayed.getBitmap().getWidth(), (float) this.mBitmapDisplayed.getBitmap().getHeight());
            imageViewMatrix.mapRect(rectF);
            float height2 = rectF.height();
            float width = rectF.width();
            if (z2) {
                height = getHeight();
                if (height2 < ((float) height)) {
                    height2 = ((((float) height) - height2) / 2.0f) - rectF.top;
                } else if (rectF.top > 0.0f) {
                    height2 = -rectF.top;
                } else if (rectF.bottom < ((float) height)) {
                    height2 = ((float) getHeight()) - rectF.bottom;
                }
                if (z) {
                    height = getWidth();
                    if (width < ((float) height)) {
                        f = ((((float) height) - width) / 2.0f) - rectF.left;
                    } else if (rectF.left > 0.0f) {
                        f = -rectF.left;
                    } else if (rectF.right < ((float) height)) {
                        f = ((float) height) - rectF.right;
                    }
                }
                postTranslate(f, height2);
                setImageMatrix(getImageViewMatrix());
            }
            height2 = 0.0f;
            if (z) {
                height = getWidth();
                if (width < ((float) height)) {
                    f = ((((float) height) - width) / 2.0f) - rectF.left;
                } else if (rectF.left > 0.0f) {
                    f = -rectF.left;
                } else if (rectF.right < ((float) height)) {
                    f = ((float) height) - rectF.right;
                }
            }
            postTranslate(f, height2);
            setImageMatrix(getImageViewMatrix());
        }
    }

    private void init() {
        setScaleType(ScaleType.MATRIX);
    }

    protected float getValue(Matrix matrix, int i) {
        matrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[i];
    }

    protected float getScale(Matrix matrix) {
        return getValue(matrix, 0);
    }

    protected float getScale() {
        return getScale(this.mSuppMatrix);
    }

    private void getProperBaseMatrix(RotateBitmap rotateBitmap, Matrix matrix) {
        float width = (float) getWidth();
        float height = (float) getHeight();
        float width2 = (float) rotateBitmap.getWidth();
        float height2 = (float) rotateBitmap.getHeight();
        matrix.reset();
        float min = Math.min(Math.min(width / width2, SECOND_MATH_ARGUMENT), Math.min(height / height2, SECOND_MATH_ARGUMENT));
        matrix.postConcat(rotateBitmap.getRotateMatrix());
        matrix.postScale(min, min);
        matrix.postTranslate((width - (width2 * min)) / 2.0f, (height - (height2 * min)) / 2.0f);
    }

    protected Matrix getImageViewMatrix() {
        this.mDisplayMatrix.set(this.mBaseMatrix);
        this.mDisplayMatrix.postConcat(this.mSuppMatrix);
        return this.mDisplayMatrix;
    }

    protected float maxZoom() {
        if (this.mBitmapDisplayed.getBitmap() == null) {
            return IPhotoView.DEFAULT_MIN_SCALE;
        }
        return Math.max(((float) this.mBitmapDisplayed.getWidth()) / ((float) this.mThisWidth), ((float) this.mBitmapDisplayed.getHeight()) / ((float) this.mThisHeight)) * 4.0f;
    }

    protected void zoomTo(float f, float f2, float f3) {
        if (f > this.mMaxZoom) {
            f = this.mMaxZoom;
        }
        float scale = f / getScale();
        this.mSuppMatrix.postScale(scale, scale, f2, f3);
        setImageMatrix(getImageViewMatrix());
        center(true, true);
    }

    protected void zoomTo(float f, float f2, float f3, float f4) {
        float scale = (f - getScale()) / f4;
        float scale2 = getScale();
        this.mHandler.post(new 1(this, f4, System.currentTimeMillis(), scale2, scale, f2, f3));
    }

    protected void zoomTo(float f) {
        zoomTo(f, ((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f);
    }

    protected void zoomToPoint(float f, float f2, float f3) {
        float width = ((float) getWidth()) / 2.0f;
        float height = ((float) getHeight()) / 2.0f;
        panBy(width - f2, height - f3);
        zoomTo(f, width, height);
    }

    protected void zoomIn() {
        zoomIn(SCALE_RATE);
    }

    protected void zoomOut() {
        zoomOut(SCALE_RATE);
    }

    protected void zoomIn(float f) {
        if (getScale() < this.mMaxZoom && this.mBitmapDisplayed.getBitmap() != null) {
            this.mSuppMatrix.postScale(f, f, ((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f);
            setImageMatrix(getImageViewMatrix());
        }
    }

    protected void zoomOut(float f) {
        if (this.mBitmapDisplayed.getBitmap() != null) {
            float width = ((float) getWidth()) / 2.0f;
            float height = ((float) getHeight()) / 2.0f;
            Matrix matrix = new Matrix(this.mSuppMatrix);
            matrix.postScale(IPhotoView.DEFAULT_MIN_SCALE / f, IPhotoView.DEFAULT_MIN_SCALE / f, width, height);
            if (getScale(matrix) < IPhotoView.DEFAULT_MIN_SCALE) {
                this.mSuppMatrix.setScale(IPhotoView.DEFAULT_MIN_SCALE, IPhotoView.DEFAULT_MIN_SCALE, width, height);
            } else {
                this.mSuppMatrix.postScale(IPhotoView.DEFAULT_MIN_SCALE / f, IPhotoView.DEFAULT_MIN_SCALE / f, width, height);
            }
            setImageMatrix(getImageViewMatrix());
            center(true, true);
        }
    }

    protected void postTranslate(float f, float f2) {
        this.mSuppMatrix.postTranslate(f, f2);
    }

    protected void panBy(float f, float f2) {
        postTranslate(f, f2);
        setImageMatrix(getImageViewMatrix());
    }
}

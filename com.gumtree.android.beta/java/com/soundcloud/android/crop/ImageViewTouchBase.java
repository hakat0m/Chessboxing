package com.soundcloud.android.crop;

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
    private static final float SCALE_RATE = 1.25f;
    protected Matrix baseMatrix;
    protected final RotateBitmap bitmapDisplayed;
    private final Matrix displayMatrix;
    protected Handler handler;
    private final float[] matrixValues;
    float maxZoom;
    private Runnable onLayoutRunnable;
    private Recycler recycler;
    protected Matrix suppMatrix;
    int thisHeight;
    int thisWidth;

    public interface Recycler {
        void recycle(Bitmap bitmap);
    }

    public ImageViewTouchBase(Context context) {
        super(context);
        this.baseMatrix = new Matrix();
        this.suppMatrix = new Matrix();
        this.displayMatrix = new Matrix();
        this.matrixValues = new float[9];
        this.bitmapDisplayed = new RotateBitmap(null, 0);
        this.thisWidth = -1;
        this.thisHeight = -1;
        this.handler = new Handler();
        init();
    }

    public ImageViewTouchBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.baseMatrix = new Matrix();
        this.suppMatrix = new Matrix();
        this.displayMatrix = new Matrix();
        this.matrixValues = new float[9];
        this.bitmapDisplayed = new RotateBitmap(null, 0);
        this.thisWidth = -1;
        this.thisHeight = -1;
        this.handler = new Handler();
        init();
    }

    public ImageViewTouchBase(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.baseMatrix = new Matrix();
        this.suppMatrix = new Matrix();
        this.displayMatrix = new Matrix();
        this.matrixValues = new float[9];
        this.bitmapDisplayed = new RotateBitmap(null, 0);
        this.thisWidth = -1;
        this.thisHeight = -1;
        this.handler = new Handler();
        init();
    }

    public void setRecycler(Recycler recycler) {
        this.recycler = recycler;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.thisWidth = i3 - i;
        this.thisHeight = i4 - i2;
        Runnable runnable = this.onLayoutRunnable;
        if (runnable != null) {
            this.onLayoutRunnable = null;
            runnable.run();
        }
        if (this.bitmapDisplayed.getBitmap() != null) {
            getProperBaseMatrix(this.bitmapDisplayed, this.baseMatrix, true);
            setImageMatrix(getImageViewMatrix());
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.getRepeatCount() != 0) {
            return super.onKeyDown(i, keyEvent);
        }
        keyEvent.startTracking();
        return true;
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4 || !keyEvent.isTracking() || keyEvent.isCanceled() || getScale() <= IPhotoView.DEFAULT_MIN_SCALE) {
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
        Bitmap bitmap2 = this.bitmapDisplayed.getBitmap();
        this.bitmapDisplayed.setBitmap(bitmap);
        this.bitmapDisplayed.setRotation(i);
        if (bitmap2 != null && bitmap2 != bitmap && this.recycler != null) {
            this.recycler.recycle(bitmap2);
        }
    }

    public void clear() {
        setImageBitmapResetBase(null, true);
    }

    public void setImageBitmapResetBase(Bitmap bitmap, boolean z) {
        setImageRotateBitmapResetBase(new RotateBitmap(bitmap, 0), z);
    }

    public void setImageRotateBitmapResetBase(RotateBitmap rotateBitmap, boolean z) {
        if (getWidth() <= 0) {
            this.onLayoutRunnable = new 1(this, rotateBitmap, z);
            return;
        }
        if (rotateBitmap.getBitmap() != null) {
            getProperBaseMatrix(rotateBitmap, this.baseMatrix, true);
            setImageBitmap(rotateBitmap.getBitmap(), rotateBitmap.getRotation());
        } else {
            this.baseMatrix.reset();
            setImageBitmap(null);
        }
        if (z) {
            this.suppMatrix.reset();
        }
        setImageMatrix(getImageViewMatrix());
        this.maxZoom = calculateMaxZoom();
    }

    protected void center() {
        Bitmap bitmap = this.bitmapDisplayed.getBitmap();
        if (bitmap != null) {
            Matrix imageViewMatrix = getImageViewMatrix();
            RectF rectF = new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight());
            imageViewMatrix.mapRect(rectF);
            float height = rectF.height();
            float width = rectF.width();
            postTranslate(centerHorizontal(rectF, width, 0.0f), centerVertical(rectF, height, 0.0f));
            setImageMatrix(getImageViewMatrix());
        }
    }

    private float centerVertical(RectF rectF, float f, float f2) {
        int height = getHeight();
        if (f < ((float) height)) {
            return ((((float) height) - f) / 2.0f) - rectF.top;
        }
        if (rectF.top > 0.0f) {
            return -rectF.top;
        }
        if (rectF.bottom < ((float) height)) {
            return ((float) getHeight()) - rectF.bottom;
        }
        return f2;
    }

    private float centerHorizontal(RectF rectF, float f, float f2) {
        int width = getWidth();
        if (f < ((float) width)) {
            return ((((float) width) - f) / 2.0f) - rectF.left;
        }
        if (rectF.left > 0.0f) {
            return -rectF.left;
        }
        if (rectF.right < ((float) width)) {
            return ((float) width) - rectF.right;
        }
        return f2;
    }

    private void init() {
        setScaleType(ScaleType.MATRIX);
    }

    protected float getValue(Matrix matrix, int i) {
        matrix.getValues(this.matrixValues);
        return this.matrixValues[i];
    }

    protected float getScale(Matrix matrix) {
        return getValue(matrix, 0);
    }

    protected float getScale() {
        return getScale(this.suppMatrix);
    }

    private void getProperBaseMatrix(RotateBitmap rotateBitmap, Matrix matrix, boolean z) {
        float width = (float) getWidth();
        float height = (float) getHeight();
        float width2 = (float) rotateBitmap.getWidth();
        float height2 = (float) rotateBitmap.getHeight();
        matrix.reset();
        float min = Math.min(Math.min(width / width2, IPhotoView.DEFAULT_MAX_SCALE), Math.min(height / height2, IPhotoView.DEFAULT_MAX_SCALE));
        if (z) {
            matrix.postConcat(rotateBitmap.getRotateMatrix());
        }
        matrix.postScale(min, min);
        matrix.postTranslate((width - (width2 * min)) / 2.0f, (height - (height2 * min)) / 2.0f);
    }

    protected Matrix getImageViewMatrix() {
        this.displayMatrix.set(this.baseMatrix);
        this.displayMatrix.postConcat(this.suppMatrix);
        return this.displayMatrix;
    }

    public Matrix getUnrotatedMatrix() {
        Matrix matrix = new Matrix();
        getProperBaseMatrix(this.bitmapDisplayed, matrix, false);
        matrix.postConcat(this.suppMatrix);
        return matrix;
    }

    protected float calculateMaxZoom() {
        if (this.bitmapDisplayed.getBitmap() == null) {
            return IPhotoView.DEFAULT_MIN_SCALE;
        }
        return Math.max(((float) this.bitmapDisplayed.getWidth()) / ((float) this.thisWidth), ((float) this.bitmapDisplayed.getHeight()) / ((float) this.thisHeight)) * 4.0f;
    }

    protected void zoomTo(float f, float f2, float f3) {
        if (f > this.maxZoom) {
            f = this.maxZoom;
        }
        float scale = f / getScale();
        this.suppMatrix.postScale(scale, scale, f2, f3);
        setImageMatrix(getImageViewMatrix());
        center();
    }

    protected void zoomTo(float f, float f2, float f3, float f4) {
        float scale = (f - getScale()) / f4;
        float scale2 = getScale();
        this.handler.post(new 2(this, f4, System.currentTimeMillis(), scale2, scale, f2, f3));
    }

    protected void zoomTo(float f) {
        zoomTo(f, ((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f);
    }

    protected void zoomIn() {
        zoomIn(SCALE_RATE);
    }

    protected void zoomOut() {
        zoomOut(SCALE_RATE);
    }

    protected void zoomIn(float f) {
        if (getScale() < this.maxZoom && this.bitmapDisplayed.getBitmap() != null) {
            this.suppMatrix.postScale(f, f, ((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f);
            setImageMatrix(getImageViewMatrix());
        }
    }

    protected void zoomOut(float f) {
        if (this.bitmapDisplayed.getBitmap() != null) {
            float width = ((float) getWidth()) / 2.0f;
            float height = ((float) getHeight()) / 2.0f;
            Matrix matrix = new Matrix(this.suppMatrix);
            matrix.postScale(IPhotoView.DEFAULT_MIN_SCALE / f, IPhotoView.DEFAULT_MIN_SCALE / f, width, height);
            if (getScale(matrix) < IPhotoView.DEFAULT_MIN_SCALE) {
                this.suppMatrix.setScale(IPhotoView.DEFAULT_MIN_SCALE, IPhotoView.DEFAULT_MIN_SCALE, width, height);
            } else {
                this.suppMatrix.postScale(IPhotoView.DEFAULT_MIN_SCALE / f, IPhotoView.DEFAULT_MIN_SCALE / f, width, height);
            }
            setImageMatrix(getImageViewMatrix());
            center();
        }
    }

    protected void postTranslate(float f, float f2) {
        this.suppMatrix.postTranslate(f, f2);
    }

    protected void panBy(float f, float f2) {
        postTranslate(f, f2);
        setImageMatrix(getImageViewMatrix());
    }
}

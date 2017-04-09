package com.soundcloud.android.crop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.soundcloud.android.crop.HighlightView.ModifyMode;
import com.soundcloud.android.crop.ImageViewTouchBase.Recycler;
import java.util.ArrayList;
import java.util.Iterator;
import uk.co.senab.photoview.IPhotoView;

public class CropImageView extends ImageViewTouchBase {
    Context context;
    ArrayList<HighlightView> highlightViews = new ArrayList();
    private float lastX;
    private float lastY;
    private int motionEdge;
    HighlightView motionHighlightView;
    private int validPointerId;

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ Matrix getUnrotatedMatrix() {
        return super.getUnrotatedMatrix();
    }

    public /* bridge */ /* synthetic */ boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    public /* bridge */ /* synthetic */ boolean onKeyUp(int i, KeyEvent keyEvent) {
        return super.onKeyUp(i, keyEvent);
    }

    public /* bridge */ /* synthetic */ void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
    }

    public /* bridge */ /* synthetic */ void setImageBitmapResetBase(Bitmap bitmap, boolean z) {
        super.setImageBitmapResetBase(bitmap, z);
    }

    public /* bridge */ /* synthetic */ void setImageRotateBitmapResetBase(RotateBitmap rotateBitmap, boolean z) {
        super.setImageRotateBitmapResetBase(rotateBitmap, z);
    }

    public /* bridge */ /* synthetic */ void setRecycler(Recycler recycler) {
        super.setRecycler(recycler);
    }

    public CropImageView(Context context) {
        super(context);
    }

    public CropImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CropImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.bitmapDisplayed.getBitmap() != null) {
            Iterator it = this.highlightViews.iterator();
            while (it.hasNext()) {
                HighlightView highlightView = (HighlightView) it.next();
                highlightView.matrix.set(getUnrotatedMatrix());
                highlightView.invalidate();
                if (highlightView.hasFocus()) {
                    centerBasedOnHighlightView(highlightView);
                }
            }
        }
    }

    protected void zoomTo(float f, float f2, float f3) {
        super.zoomTo(f, f2, f3);
        Iterator it = this.highlightViews.iterator();
        while (it.hasNext()) {
            HighlightView highlightView = (HighlightView) it.next();
            highlightView.matrix.set(getUnrotatedMatrix());
            highlightView.invalidate();
        }
    }

    protected void zoomIn() {
        super.zoomIn();
        Iterator it = this.highlightViews.iterator();
        while (it.hasNext()) {
            HighlightView highlightView = (HighlightView) it.next();
            highlightView.matrix.set(getUnrotatedMatrix());
            highlightView.invalidate();
        }
    }

    protected void zoomOut() {
        super.zoomOut();
        Iterator it = this.highlightViews.iterator();
        while (it.hasNext()) {
            HighlightView highlightView = (HighlightView) it.next();
            highlightView.matrix.set(getUnrotatedMatrix());
            highlightView.invalidate();
        }
    }

    protected void postTranslate(float f, float f2) {
        super.postTranslate(f, f2);
        Iterator it = this.highlightViews.iterator();
        while (it.hasNext()) {
            HighlightView highlightView = (HighlightView) it.next();
            highlightView.matrix.postTranslate(f, f2);
            highlightView.invalidate();
        }
    }

    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        if (((CropImageActivity) this.context).isSaving()) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                Iterator it = this.highlightViews.iterator();
                while (it.hasNext()) {
                    HighlightView highlightView = (HighlightView) it.next();
                    int hit = highlightView.getHit(motionEvent.getX(), motionEvent.getY());
                    if (hit != 1) {
                        this.motionEdge = hit;
                        this.motionHighlightView = highlightView;
                        this.lastX = motionEvent.getX();
                        this.lastY = motionEvent.getY();
                        this.validPointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                        this.motionHighlightView.setMode(hit == 32 ? ModifyMode.Move : ModifyMode.Grow);
                        break;
                    }
                }
                break;
            case HighlightView.GROW_NONE /*1*/:
                if (this.motionHighlightView != null) {
                    centerBasedOnHighlightView(this.motionHighlightView);
                    this.motionHighlightView.setMode(ModifyMode.None);
                }
                this.motionHighlightView = null;
                center();
                break;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                if (this.motionHighlightView != null && motionEvent.getPointerId(motionEvent.getActionIndex()) == this.validPointerId) {
                    this.motionHighlightView.handleMotion(this.motionEdge, motionEvent.getX() - this.lastX, motionEvent.getY() - this.lastY);
                    this.lastX = motionEvent.getX();
                    this.lastY = motionEvent.getY();
                }
                if (getScale() == IPhotoView.DEFAULT_MIN_SCALE) {
                    center();
                    break;
                }
                break;
        }
        return true;
    }

    private void ensureVisible(HighlightView highlightView) {
        Rect rect = highlightView.drawRect;
        int max = Math.max(0, getLeft() - rect.left);
        int min = Math.min(0, getRight() - rect.right);
        int max2 = Math.max(0, getTop() - rect.top);
        int min2 = Math.min(0, getBottom() - rect.bottom);
        if (max == 0) {
            max = min;
        }
        if (max2 == 0) {
            max2 = min2;
        }
        if (max != 0 || max2 != 0) {
            panBy((float) max, (float) max2);
        }
    }

    private void centerBasedOnHighlightView(HighlightView highlightView) {
        Rect rect = highlightView.drawRect;
        float width = (float) getWidth();
        float max = Math.max(IPhotoView.DEFAULT_MIN_SCALE, Math.min((width / ((float) rect.width())) * 0.6f, (((float) getHeight()) / ((float) rect.height())) * 0.6f) * getScale());
        if (((double) (Math.abs(max - getScale()) / max)) > 0.1d) {
            float[] fArr = new float[]{highlightView.cropRect.centerX(), highlightView.cropRect.centerY()};
            getUnrotatedMatrix().mapPoints(fArr);
            zoomTo(max, fArr[0], fArr[1], 300.0f);
        }
        ensureVisible(highlightView);
    }

    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        Iterator it = this.highlightViews.iterator();
        while (it.hasNext()) {
            ((HighlightView) it.next()).draw(canvas);
        }
    }

    public void add(HighlightView highlightView) {
        this.highlightViews.add(highlightView);
        invalidate();
    }
}

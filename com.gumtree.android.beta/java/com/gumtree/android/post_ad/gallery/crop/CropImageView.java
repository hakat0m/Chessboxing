package com.gumtree.android.post_ad.gallery.crop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import com.gumtree.android.post_ad.gallery.crop.ImageViewTouchBase.Recycler;
import java.util.ArrayList;
import java.util.Iterator;

public class CropImageView extends ImageViewTouchBase {
    ArrayList<HighlightView> mHighlightViews = new ArrayList();
    float mLastX;
    float mLastY;
    int mMotionEdge;
    HighlightView mMotionHighlightView;

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
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

    public CropImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mBitmapDisplayed.getBitmap() != null) {
            Iterator it = this.mHighlightViews.iterator();
            while (it.hasNext()) {
                HighlightView highlightView = (HighlightView) it.next();
                highlightView.mMatrix.set(getImageMatrix());
                highlightView.invalidate();
                if (highlightView.mIsFocused) {
                    centerBasedOnHighlightView(highlightView);
                }
            }
        }
    }

    protected void zoomTo(float f, float f2, float f3) {
        super.zoomTo(f, f2, f3);
        Iterator it = this.mHighlightViews.iterator();
        while (it.hasNext()) {
            HighlightView highlightView = (HighlightView) it.next();
            highlightView.mMatrix.set(getImageMatrix());
            highlightView.invalidate();
        }
    }

    protected void zoomIn() {
        super.zoomIn();
        Iterator it = this.mHighlightViews.iterator();
        while (it.hasNext()) {
            HighlightView highlightView = (HighlightView) it.next();
            highlightView.mMatrix.set(getImageMatrix());
            highlightView.invalidate();
        }
    }

    protected void zoomOut() {
        super.zoomOut();
        Iterator it = this.mHighlightViews.iterator();
        while (it.hasNext()) {
            HighlightView highlightView = (HighlightView) it.next();
            highlightView.mMatrix.set(getImageMatrix());
            highlightView.invalidate();
        }
    }

    protected void postTranslate(float f, float f2) {
        super.postTranslate(f, f2);
        for (int i = 0; i < this.mHighlightViews.size(); i++) {
            HighlightView highlightView = (HighlightView) this.mHighlightViews.get(i);
            highlightView.mMatrix.postTranslate(f, f2);
            highlightView.invalidate();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
        r6 = this;
        r1 = 0;
        r2 = 1;
        r0 = r6.getContext();
        r0 = (com.gumtree.android.post_ad.gallery.crop.CropImageActivity) r0;
        r0 = r0.mSaving;
        if (r0 == 0) goto L_0x000d;
    L_0x000c:
        return r1;
    L_0x000d:
        r0 = r7.getAction();
        switch(r0) {
            case 0: goto L_0x0020;
            case 1: goto L_0x005d;
            case 2: goto L_0x0071;
            default: goto L_0x0014;
        };
    L_0x0014:
        r0 = r7.getAction();
        switch(r0) {
            case 1: goto L_0x009d;
            case 2: goto L_0x00a2;
            default: goto L_0x001b;
        };
    L_0x001b:
        r1 = r2;
        goto L_0x000c;
    L_0x001d:
        r0 = r1 + 1;
        r1 = r0;
    L_0x0020:
        r0 = r6.mHighlightViews;
        r0 = r0.size();
        if (r1 >= r0) goto L_0x0014;
    L_0x0028:
        r0 = r6.mHighlightViews;
        r0 = r0.get(r1);
        r0 = (com.gumtree.android.post_ad.gallery.crop.HighlightView) r0;
        r3 = r7.getX();
        r4 = r7.getY();
        r3 = r0.getHit(r3, r4);
        if (r3 == r2) goto L_0x001d;
    L_0x003e:
        r6.mMotionEdge = r3;
        r6.mMotionHighlightView = r0;
        r0 = r7.getX();
        r6.mLastX = r0;
        r0 = r7.getY();
        r6.mLastY = r0;
        r1 = r6.mMotionHighlightView;
        r0 = 32;
        if (r3 != r0) goto L_0x005a;
    L_0x0054:
        r0 = com.gumtree.android.post_ad.gallery.crop.HighlightView.ModifyMode.Move;
    L_0x0056:
        r1.setMode(r0);
        goto L_0x0014;
    L_0x005a:
        r0 = com.gumtree.android.post_ad.gallery.crop.HighlightView.ModifyMode.Grow;
        goto L_0x0056;
    L_0x005d:
        r0 = r6.mMotionHighlightView;
        if (r0 == 0) goto L_0x006d;
    L_0x0061:
        r0 = r6.mMotionHighlightView;
        r6.centerBasedOnHighlightView(r0);
        r0 = r6.mMotionHighlightView;
        r1 = com.gumtree.android.post_ad.gallery.crop.HighlightView.ModifyMode.None;
        r0.setMode(r1);
    L_0x006d:
        r0 = 0;
        r6.mMotionHighlightView = r0;
        goto L_0x0014;
    L_0x0071:
        r0 = r6.mMotionHighlightView;
        if (r0 == 0) goto L_0x0014;
    L_0x0075:
        r0 = r6.mMotionHighlightView;
        r1 = r6.mMotionEdge;
        r3 = r7.getX();
        r4 = r6.mLastX;
        r3 = r3 - r4;
        r4 = r7.getY();
        r5 = r6.mLastY;
        r4 = r4 - r5;
        r0.handleMotion(r1, r3, r4);
        r0 = r7.getX();
        r6.mLastX = r0;
        r0 = r7.getY();
        r6.mLastY = r0;
        r0 = r6.mMotionHighlightView;
        r6.ensureVisible(r0);
        goto L_0x0014;
    L_0x009d:
        r6.center(r2, r2);
        goto L_0x001b;
    L_0x00a2:
        r0 = r6.getScale();
        r1 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1));
        if (r0 != 0) goto L_0x001b;
    L_0x00ac:
        r6.center(r2, r2);
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gumtree.android.post_ad.gallery.crop.CropImageView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void ensureVisible(HighlightView highlightView) {
        Rect rect = highlightView.mDrawRect;
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
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < this.mHighlightViews.size(); i++) {
            ((HighlightView) this.mHighlightViews.get(i)).draw(canvas);
        }
    }

    public void set(HighlightView highlightView) {
        this.mHighlightViews.clear();
        this.mHighlightViews.add(highlightView);
        invalidate();
    }
}

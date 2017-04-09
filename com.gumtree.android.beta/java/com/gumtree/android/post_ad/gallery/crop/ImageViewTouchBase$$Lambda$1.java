package com.gumtree.android.post_ad.gallery.crop;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ImageViewTouchBase$$Lambda$1 implements Runnable {
    private final ImageViewTouchBase arg$1;
    private final RotateBitmap arg$2;
    private final boolean arg$3;

    private ImageViewTouchBase$$Lambda$1(ImageViewTouchBase imageViewTouchBase, RotateBitmap rotateBitmap, boolean z) {
        this.arg$1 = imageViewTouchBase;
        this.arg$2 = rotateBitmap;
        this.arg$3 = z;
    }

    public static Runnable lambdaFactory$(ImageViewTouchBase imageViewTouchBase, RotateBitmap rotateBitmap, boolean z) {
        return new ImageViewTouchBase$$Lambda$1(imageViewTouchBase, rotateBitmap, z);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$setImageRotateBitmapResetBase$0(this.arg$2, this.arg$3);
    }
}

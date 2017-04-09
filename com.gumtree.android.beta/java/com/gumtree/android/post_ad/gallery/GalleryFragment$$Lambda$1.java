package com.gumtree.android.post_ad.gallery;

import android.content.Intent;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GalleryFragment$$Lambda$1 implements Runnable {
    private final GalleryFragment arg$1;
    private final int arg$2;
    private final Intent arg$3;

    private GalleryFragment$$Lambda$1(GalleryFragment galleryFragment, int i, Intent intent) {
        this.arg$1 = galleryFragment;
        this.arg$2 = i;
        this.arg$3 = intent;
    }

    public static Runnable lambdaFactory$(GalleryFragment galleryFragment, int i, Intent intent) {
        return new GalleryFragment$$Lambda$1(galleryFragment, i, intent);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$rescheduleResultHandling$0(this.arg$2, this.arg$3);
    }
}

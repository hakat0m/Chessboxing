package com.gumtree.android.post_ad.preview;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PreviewGalleryActivity$$Lambda$1 implements Runnable {
    private final PreviewGalleryActivity arg$1;

    private PreviewGalleryActivity$$Lambda$1(PreviewGalleryActivity previewGalleryActivity) {
        this.arg$1 = previewGalleryActivity;
    }

    public static Runnable lambdaFactory$(PreviewGalleryActivity previewGalleryActivity) {
        return new PreviewGalleryActivity$$Lambda$1(previewGalleryActivity);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$onBackPressed$0();
    }
}

package com.gumtree.android.vip;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VIPGalleryActivity$$Lambda$1 implements Runnable {
    private final VIPGalleryActivity arg$1;

    private VIPGalleryActivity$$Lambda$1(VIPGalleryActivity vIPGalleryActivity) {
        this.arg$1 = vIPGalleryActivity;
    }

    public static Runnable lambdaFactory$(VIPGalleryActivity vIPGalleryActivity) {
        return new VIPGalleryActivity$$Lambda$1(vIPGalleryActivity);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$onBackPressed$0();
    }
}

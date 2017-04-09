package com.gumtree.android.vip_treebay;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VIPGalleryActivityTreebay$$Lambda$1 implements Runnable {
    private final VIPGalleryActivityTreebay arg$1;

    private VIPGalleryActivityTreebay$$Lambda$1(VIPGalleryActivityTreebay vIPGalleryActivityTreebay) {
        this.arg$1 = vIPGalleryActivityTreebay;
    }

    public static Runnable lambdaFactory$(VIPGalleryActivityTreebay vIPGalleryActivityTreebay) {
        return new VIPGalleryActivityTreebay$$Lambda$1(vIPGalleryActivityTreebay);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$onBackPressed$0();
    }
}

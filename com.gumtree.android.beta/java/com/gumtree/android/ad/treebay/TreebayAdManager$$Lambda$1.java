package com.gumtree.android.ad.treebay;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class TreebayAdManager$$Lambda$1 implements Action0 {
    private final TreebayAdManager arg$1;

    private TreebayAdManager$$Lambda$1(TreebayAdManager treebayAdManager) {
        this.arg$1 = treebayAdManager;
    }

    public static Action0 lambdaFactory$(TreebayAdManager treebayAdManager) {
        return new TreebayAdManager$$Lambda$1(treebayAdManager);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$fetchAds$0();
    }
}

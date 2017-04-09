package com.gumtree.android.ad.treebay;

import com.gumtree.android.api.treebay.SearchResult;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class TreebayAdManager$$Lambda$4 implements Action1 {
    private final TreebayAdManager arg$1;

    private TreebayAdManager$$Lambda$4(TreebayAdManager treebayAdManager) {
        this.arg$1 = treebayAdManager;
    }

    public static Action1 lambdaFactory$(TreebayAdManager treebayAdManager) {
        return new TreebayAdManager$$Lambda$4(treebayAdManager);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$fetchAds$1((SearchResult) obj);
    }
}

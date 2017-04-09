package com.gumtree.android.deeplinking.services;

import com.ebay.classifieds.capi.seo.Seo;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class SeoDeepLinkingService$$Lambda$1 implements Func1 {
    private static final SeoDeepLinkingService$$Lambda$1 instance = new SeoDeepLinkingService$$Lambda$1();

    private SeoDeepLinkingService$$Lambda$1() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return SeoDeepLinkingService.lambda$retrieveDetails$0((Seo) obj);
    }
}

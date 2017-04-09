package com.gumtree.android.location.services;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class CachedLocationService$$Lambda$1 implements Action1 {
    private final CachedLocationService arg$1;
    private final String arg$2;

    private CachedLocationService$$Lambda$1(CachedLocationService cachedLocationService, String str) {
        this.arg$1 = cachedLocationService;
        this.arg$2 = str;
    }

    public static Action1 lambdaFactory$(CachedLocationService cachedLocationService, String str) {
        return new CachedLocationService$$Lambda$1(cachedLocationService, str);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$getSuggestedLocations$0(this.arg$2, (Throwable) obj);
    }
}

package com.gumtree.android.auth;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultApplicationDataManager$$Lambda$2 implements Action1 {
    private static final DefaultApplicationDataManager$$Lambda$2 instance = new DefaultApplicationDataManager$$Lambda$2();

    private DefaultApplicationDataManager$$Lambda$2() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        DefaultApplicationDataManager.lambda$clearDraftAd$1((Throwable) obj);
    }
}

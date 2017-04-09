package com.gumtree.android.auth;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultApplicationDataManager$$Lambda$1 implements Action1 {
    private static final DefaultApplicationDataManager$$Lambda$1 instance = new DefaultApplicationDataManager$$Lambda$1();

    private DefaultApplicationDataManager$$Lambda$1() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        DefaultApplicationDataManager.lambda$clearDraftAd$0((Void) obj);
    }
}

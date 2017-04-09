package com.gumtree.android.location.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class DefaultLocationPresenter$$Lambda$1 implements Action0 {
    private final DefaultLocationPresenter arg$1;

    private DefaultLocationPresenter$$Lambda$1(DefaultLocationPresenter defaultLocationPresenter) {
        this.arg$1 = defaultLocationPresenter;
    }

    public static Action0 lambdaFactory$(DefaultLocationPresenter defaultLocationPresenter) {
        return new DefaultLocationPresenter$$Lambda$1(defaultLocationPresenter);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$getCurrentLocation$0();
    }
}

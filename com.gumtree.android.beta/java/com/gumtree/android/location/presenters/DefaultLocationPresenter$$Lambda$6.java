package com.gumtree.android.location.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultLocationPresenter$$Lambda$6 implements Action1 {
    private final DefaultLocationPresenter arg$1;

    private DefaultLocationPresenter$$Lambda$6(DefaultLocationPresenter defaultLocationPresenter) {
        this.arg$1 = defaultLocationPresenter;
    }

    public static Action1 lambdaFactory$(DefaultLocationPresenter defaultLocationPresenter) {
        return new DefaultLocationPresenter$$Lambda$6(defaultLocationPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$getCurrentLocation$3((Throwable) obj);
    }
}

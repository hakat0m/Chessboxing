package com.gumtree.android.location.presenters;

import com.gumtree.android.location.model.LocationData;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultLocationPresenter$$Lambda$5 implements Action1 {
    private final DefaultLocationPresenter arg$1;

    private DefaultLocationPresenter$$Lambda$5(DefaultLocationPresenter defaultLocationPresenter) {
        this.arg$1 = defaultLocationPresenter;
    }

    public static Action1 lambdaFactory$(DefaultLocationPresenter defaultLocationPresenter) {
        return new DefaultLocationPresenter$$Lambda$5(defaultLocationPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$getCurrentLocation$2((LocationData) obj);
    }
}

package com.gumtree.android.postad.confirmation;

import com.gumtree.android.postad.DraftAd;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultPostAdConfirmationScreenPresenter$$Lambda$1 implements Action1 {
    private final DefaultPostAdConfirmationScreenPresenter arg$1;

    private DefaultPostAdConfirmationScreenPresenter$$Lambda$1(DefaultPostAdConfirmationScreenPresenter defaultPostAdConfirmationScreenPresenter) {
        this.arg$1 = defaultPostAdConfirmationScreenPresenter;
    }

    public static Action1 lambdaFactory$(DefaultPostAdConfirmationScreenPresenter defaultPostAdConfirmationScreenPresenter) {
        return new DefaultPostAdConfirmationScreenPresenter$$Lambda$1(defaultPostAdConfirmationScreenPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$configureScreen$0((DraftAd) obj);
    }
}

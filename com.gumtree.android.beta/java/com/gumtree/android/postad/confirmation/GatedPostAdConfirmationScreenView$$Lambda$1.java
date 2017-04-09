package com.gumtree.android.postad.confirmation;

import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedPostAdConfirmationScreenView$$Lambda$1 implements Action1 {
    private final GatedPostAdConfirmationScreenView arg$1;

    private GatedPostAdConfirmationScreenView$$Lambda$1(GatedPostAdConfirmationScreenView gatedPostAdConfirmationScreenView) {
        this.arg$1 = gatedPostAdConfirmationScreenView;
    }

    public static Action1 lambdaFactory$(GatedPostAdConfirmationScreenView gatedPostAdConfirmationScreenView) {
        return new GatedPostAdConfirmationScreenView$$Lambda$1(gatedPostAdConfirmationScreenView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}

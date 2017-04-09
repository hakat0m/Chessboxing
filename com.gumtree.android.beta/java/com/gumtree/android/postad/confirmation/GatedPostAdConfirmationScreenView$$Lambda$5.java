package com.gumtree.android.postad.confirmation;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenPresenter.View;
import com.gumtree.android.postad.confirmation.models.ConfirmationScreenResults;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedPostAdConfirmationScreenView$$Lambda$5 implements Action {
    private final View arg$1;

    private GatedPostAdConfirmationScreenView$$Lambda$5(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedPostAdConfirmationScreenView$$Lambda$5(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.completeWithResult((ConfirmationScreenResults) obj);
    }
}

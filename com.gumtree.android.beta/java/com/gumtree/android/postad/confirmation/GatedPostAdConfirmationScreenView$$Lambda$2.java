package com.gumtree.android.postad.confirmation;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedPostAdConfirmationScreenView$$Lambda$2 implements Action {
    private final View arg$1;

    private GatedPostAdConfirmationScreenView$$Lambda$2(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedPostAdConfirmationScreenView$$Lambda$2(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showFailedPost();
    }
}

package com.gumtree.android.postad.confirmation.dialogs;

import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedSuccessPostDialogView$$Lambda$5 implements Action {
    private final SuccessPostDialogPresenter$View arg$1;

    private GatedSuccessPostDialogView$$Lambda$5(SuccessPostDialogPresenter$View successPostDialogPresenter$View) {
        this.arg$1 = successPostDialogPresenter$View;
    }

    public static Action lambdaFactory$(SuccessPostDialogPresenter$View successPostDialogPresenter$View) {
        return new GatedSuccessPostDialogView$$Lambda$5(successPostDialogPresenter$View);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showItemImage((String) obj);
    }
}

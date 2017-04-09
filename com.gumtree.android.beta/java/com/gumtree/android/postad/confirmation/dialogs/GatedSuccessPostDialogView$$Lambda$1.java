package com.gumtree.android.postad.confirmation.dialogs;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedSuccessPostDialogView$$Lambda$1 implements Action1 {
    private final GatedSuccessPostDialogView arg$1;

    private GatedSuccessPostDialogView$$Lambda$1(GatedSuccessPostDialogView gatedSuccessPostDialogView) {
        this.arg$1 = gatedSuccessPostDialogView;
    }

    public static Action1 lambdaFactory$(GatedSuccessPostDialogView gatedSuccessPostDialogView) {
        return new GatedSuccessPostDialogView$$Lambda$1(gatedSuccessPostDialogView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((SuccessPostDialogPresenter$View) obj);
    }
}

package com.gumtree.android.postad.confirmation;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class GetDraftAdHelper$$Lambda$5 implements Action0 {
    private final GetDraftAdHelper arg$1;

    private GetDraftAdHelper$$Lambda$5(GetDraftAdHelper getDraftAdHelper) {
        this.arg$1 = getDraftAdHelper;
    }

    public static Action0 lambdaFactory$(GetDraftAdHelper getDraftAdHelper) {
        return new GetDraftAdHelper$$Lambda$5(getDraftAdHelper);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$onCreate$4();
    }
}

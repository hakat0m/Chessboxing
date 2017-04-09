package com.gumtree.android.postad.confirmation;

import com.gumtree.android.postad.DraftAd;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GetDraftAdHelper$$Lambda$6 implements Action1 {
    private final GetDraftAdHelper arg$1;

    private GetDraftAdHelper$$Lambda$6(GetDraftAdHelper getDraftAdHelper) {
        this.arg$1 = getDraftAdHelper;
    }

    public static Action1 lambdaFactory$(GetDraftAdHelper getDraftAdHelper) {
        return new GetDraftAdHelper$$Lambda$6(getDraftAdHelper);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$onCreate$5((DraftAd) obj);
    }
}

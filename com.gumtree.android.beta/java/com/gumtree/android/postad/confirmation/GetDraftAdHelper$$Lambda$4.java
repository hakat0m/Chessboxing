package com.gumtree.android.postad.confirmation;

import com.gumtree.android.postad.DraftAd;
import java.lang.invoke.LambdaForm.Hidden;
import rx.Scheduler;
import rx.functions.Func1;

final /* synthetic */ class GetDraftAdHelper$$Lambda$4 implements Func1 {
    private final Scheduler arg$1;
    private final Func1 arg$2;
    private final String arg$3;

    private GetDraftAdHelper$$Lambda$4(Scheduler scheduler, Func1 func1, String str) {
        this.arg$1 = scheduler;
        this.arg$2 = func1;
        this.arg$3 = str;
    }

    public static Func1 lambdaFactory$(Scheduler scheduler, Func1 func1, String str) {
        return new GetDraftAdHelper$$Lambda$4(scheduler, func1, str);
    }

    @Hidden
    public Object call(Object obj) {
        return GetDraftAdHelper.lambda$onCreate$3(this.arg$1, this.arg$2, this.arg$3, (DraftAd) obj);
    }
}

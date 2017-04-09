package com.gumtree.android.postad.confirmation;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class GetDraftAdHelper$$Lambda$8 implements Func1 {
    private final Func1 arg$1;
    private final String arg$2;

    private GetDraftAdHelper$$Lambda$8(Func1 func1, String str) {
        this.arg$1 = func1;
        this.arg$2 = str;
    }

    public static Func1 lambdaFactory$(Func1 func1, String str) {
        return new GetDraftAdHelper$$Lambda$8(func1, str);
    }

    @Hidden
    public Object call(Object obj) {
        return GetDraftAdHelper.lambda$null$2(this.arg$1, this.arg$2, (Long) obj);
    }
}

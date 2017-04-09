package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class MetadataBee$$Lambda$5 implements Func1 {
    private final MetadataBee arg$1;

    private MetadataBee$$Lambda$5(MetadataBee metadataBee) {
        this.arg$1 = metadataBee;
    }

    public static Func1 lambdaFactory$(MetadataBee metadataBee) {
        return new MetadataBee$$Lambda$5(metadataBee);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$load$4((String) obj);
    }
}

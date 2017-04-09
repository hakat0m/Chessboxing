package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class MetadataBee$$Lambda$3 implements Action1 {
    private final MetadataBee arg$1;

    private MetadataBee$$Lambda$3(MetadataBee metadataBee) {
        this.arg$1 = metadataBee;
    }

    public static Action1 lambdaFactory$(MetadataBee metadataBee) {
        return new MetadataBee$$Lambda$3(metadataBee);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$fetchMetadata$2((Throwable) obj);
    }
}

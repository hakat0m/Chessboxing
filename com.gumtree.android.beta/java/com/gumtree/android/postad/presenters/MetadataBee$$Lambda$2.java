package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class MetadataBee$$Lambda$2 implements Action0 {
    private final MetadataBee arg$1;

    private MetadataBee$$Lambda$2(MetadataBee metadataBee) {
        this.arg$1 = metadataBee;
    }

    public static Action0 lambdaFactory$(MetadataBee metadataBee) {
        return new MetadataBee$$Lambda$2(metadataBee);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$fetchMetadata$1();
    }
}

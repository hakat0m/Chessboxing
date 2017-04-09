package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$5 implements Action1 {
    private final MetadataBee arg$1;

    private DefaultPostAdSummaryPresenter$$Lambda$5(MetadataBee metadataBee) {
        this.arg$1 = metadataBee;
    }

    public static Action1 lambdaFactory$(MetadataBee metadataBee) {
        return new DefaultPostAdSummaryPresenter$$Lambda$5(metadataBee);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.cancel();
    }
}

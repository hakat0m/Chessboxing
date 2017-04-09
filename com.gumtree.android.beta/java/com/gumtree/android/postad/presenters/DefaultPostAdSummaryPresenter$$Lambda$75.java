package com.gumtree.android.postad.presenters;

import com.gumtree.android.userprofile.User;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$75 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$75 instance = new DefaultPostAdSummaryPresenter$$Lambda$75();

    private DefaultPostAdSummaryPresenter$$Lambda$75() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return PostAdSummaryUtils.createEmptyDraftAd((User) obj);
    }
}

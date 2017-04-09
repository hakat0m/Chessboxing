package com.gumtree.android.ad.search.presenters.results;

import com.gumtree.android.services.NetworkState;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultSearchResultsPresenter$$Lambda$1 implements Action1 {
    private final DefaultSearchResultsPresenter arg$1;

    private DefaultSearchResultsPresenter$$Lambda$1(DefaultSearchResultsPresenter defaultSearchResultsPresenter) {
        this.arg$1 = defaultSearchResultsPresenter;
    }

    public static Action1 lambdaFactory$(DefaultSearchResultsPresenter defaultSearchResultsPresenter) {
        return new DefaultSearchResultsPresenter$$Lambda$1(defaultSearchResultsPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$attachView$0((NetworkState) obj);
    }
}

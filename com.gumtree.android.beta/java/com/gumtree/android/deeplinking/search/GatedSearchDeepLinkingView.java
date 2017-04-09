package com.gumtree.android.deeplinking.search;

import android.support.annotation.Nullable;
import com.gumtree.android.deeplinking.SearchParameters;
import com.gumtree.android.deeplinking.search.SearchDeepLinkingPresenter.View;
import com.gumtree.android.mvp.Gate;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedSearchDeepLinkingView implements View {
    private final Gate<String> showError = new Gate();
    private final Gate<Void> showHomeScreen = new Gate();
    private final Gate<SearchParameters> showSRP = new Gate();
    private Subscription subscription = this.trigger.subscribe(GatedSearchDeepLinkingView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    private void open(View view) {
        this.showSRP.open(GatedSearchDeepLinkingView$$Lambda$2.lambdaFactory$(view));
        this.showError.open(GatedSearchDeepLinkingView$$Lambda$3.lambdaFactory$(view));
        this.showHomeScreen.open(GatedSearchDeepLinkingView$$Lambda$4.lambdaFactory$(view));
    }

    private void close() {
        this.showError.close();
        this.showSRP.close();
        this.showHomeScreen.close();
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    public void showHomeScreen() {
        this.showHomeScreen.perform(null);
    }

    public void showError(String str) {
        this.showError.perform(str);
    }

    public void showSRP(SearchParameters searchParameters) {
        this.showSRP.perform(searchParameters);
    }
}

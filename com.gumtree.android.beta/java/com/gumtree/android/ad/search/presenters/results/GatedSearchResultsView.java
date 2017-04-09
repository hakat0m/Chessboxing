package com.gumtree.android.ad.search.presenters.results;

import android.support.annotation.Nullable;
import com.gumtree.android.ad.search.presenters.results.SearchResultsPresenter.View;
import com.gumtree.android.mvp.Gate;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedSearchResultsView implements View {
    private Gate<String> showMessage = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedSearchResultsView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    void sealIt() {
        this.subscription.unsubscribe();
    }

    private void open(View view) {
        this.showMessage.open(GatedSearchResultsView$$Lambda$2.lambdaFactory$(view));
    }

    private void close() {
        this.showMessage.close();
    }

    public void showMessage(String str) {
        this.showMessage.perform(str);
    }
}

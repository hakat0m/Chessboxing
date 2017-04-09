package com.gumtree.android.ad.search.presenters.refine;

import android.support.annotation.Nullable;
import com.gumtree.android.ad.search.presenters.refine.RefineSearchPresenter.View;
import com.gumtree.android.mvp.Gate;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class RefineSearchGatedView implements View {
    private final Gate<Void> refineCategory = new Gate();
    private final Gate<Void> refineLocation = new Gate();
    private final Gate<Void> showSearchResults = new Gate();
    private final Subscription subscription = this.trigger.subscribe(RefineSearchGatedView$$Lambda$1.lambdaFactory$(this));
    private final BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    private void open(View view) {
        this.refineCategory.open(RefineSearchGatedView$$Lambda$4.lambdaFactory$(view));
        this.refineLocation.open(RefineSearchGatedView$$Lambda$5.lambdaFactory$(view));
        this.showSearchResults.open(RefineSearchGatedView$$Lambda$6.lambdaFactory$(view));
    }

    protected void close() {
        this.refineCategory.close();
        this.refineLocation.close();
        this.showSearchResults.close();
    }

    public void refineCategory() {
        this.refineCategory.perform(null);
    }

    public void refineLocation() {
        this.refineLocation.perform(null);
    }

    public void showSearchResults() {
        this.showSearchResults.perform(null);
    }
}

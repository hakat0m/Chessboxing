package com.gumtree.android.location.presenters;

import android.support.annotation.Nullable;
import com.gumtree.android.location.model.LocationData;
import com.gumtree.android.location.presenters.LocationPresenter.View;
import com.gumtree.android.mvp.Gate;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedLocationView implements View {
    private final Gate<LocationData> completeWithLocation = new Gate();
    private final Gate<Boolean> enableCurrentLocation = new Gate();
    private final Gate<String> showErrorMessage = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedLocationView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

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

    private void close() {
        this.completeWithLocation.close();
        this.showErrorMessage.close();
        this.enableCurrentLocation.close();
    }

    private void open(View view) {
        this.completeWithLocation.open(GatedLocationView$$Lambda$2.lambdaFactory$(view));
        this.showErrorMessage.open(GatedLocationView$$Lambda$3.lambdaFactory$(view));
        this.enableCurrentLocation.open(GatedLocationView$$Lambda$4.lambdaFactory$(view));
    }

    public void showErrorMessage(String str) {
        this.showErrorMessage.perform(str);
    }

    public void completeWithLocation(LocationData locationData) {
        this.completeWithLocation.perform(locationData);
    }

    public void enableCurrentLocation(boolean z) {
        this.enableCurrentLocation.perform(Boolean.valueOf(z));
    }
}

package com.gumtree.android.postad.customdetails;

import android.support.annotation.Nullable;
import com.gumtree.android.mvp.Gate;
import com.gumtree.android.postad.customdetails.CustomDetailsPresenter.View;
import com.gumtree.android.postad.customdetails.models.CustomAttributeData;
import com.gumtree.android.postad.customdetails.models.CustomDetailsData;
import java.util.List;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedCustomDetailsView implements View {
    private final Gate<Void> clearScreen = new Gate();
    private final Gate<CustomDetailsData> closingScreen = new Gate();
    private final Gate<Void> hideManualEntry = new Gate();
    private final Gate<List<CustomAttributeData>> populateCustomAttributes = new Gate();
    private final Gate<String> showErrorMessage = new Gate();
    private final Gate<Void> showHiddenFields = new Gate();
    private final Gate<Boolean> showLoadingSpinner = new Gate();
    private final Gate<Void> showNotRecognised = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedCustomDetailsView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();
    private final Gate<Void> validateAllFields = new Gate();

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
        this.populateCustomAttributes.open(GatedCustomDetailsView$$Lambda$2.lambdaFactory$(view));
        this.closingScreen.open(GatedCustomDetailsView$$Lambda$3.lambdaFactory$(view));
        this.validateAllFields.open(GatedCustomDetailsView$$Lambda$4.lambdaFactory$(view));
        this.showHiddenFields.open(GatedCustomDetailsView$$Lambda$5.lambdaFactory$(view));
        this.clearScreen.open(GatedCustomDetailsView$$Lambda$6.lambdaFactory$(view));
        this.showLoadingSpinner.open(GatedCustomDetailsView$$Lambda$7.lambdaFactory$(view));
        this.showErrorMessage.open(GatedCustomDetailsView$$Lambda$8.lambdaFactory$(view));
        this.hideManualEntry.open(GatedCustomDetailsView$$Lambda$9.lambdaFactory$(view));
        this.showNotRecognised.open(GatedCustomDetailsView$$Lambda$10.lambdaFactory$(view));
    }

    private void close() {
        this.populateCustomAttributes.close();
        this.closingScreen.close();
        this.validateAllFields.close();
        this.showHiddenFields.close();
        this.clearScreen.close();
        this.showLoadingSpinner.close();
        this.showErrorMessage.close();
        this.hideManualEntry.close();
        this.showNotRecognised.close();
    }

    public void populateCustomAttributes(List<CustomAttributeData> list) {
        this.populateCustomAttributes.perform(list);
    }

    public void closingScreen(CustomDetailsData customDetailsData) {
        this.closingScreen.perform(customDetailsData);
    }

    public void validateAllFields() {
        this.validateAllFields.perform(null);
    }

    public void showHiddenFields() {
        this.showHiddenFields.perform(null);
    }

    public void clearScreen() {
        this.clearScreen.perform(null);
    }

    public void showLoading(boolean z) {
        this.showLoadingSpinner.perform(Boolean.valueOf(z));
    }

    public void showErrorMessage(String str) {
        this.showErrorMessage.perform(str);
    }

    public void showVrmNotRecognised() {
        this.showNotRecognised.perform(null);
    }

    public void hideVrmManualEntry() {
        this.hideManualEntry.perform(null);
    }
}

package com.gumtree.android.postad.contactdetails;

import android.support.annotation.Nullable;
import com.gumtree.android.location.model.LocationData;
import com.gumtree.android.mvp.Gate;
import com.gumtree.android.postad.contactdetails.PostAdContactDetailsPresenter.View;
import com.gumtree.android.postad.contactdetails.models.ContactDetailsData;
import org.apache.commons.lang3.tuple.Pair;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedPostAdContactDetailsView implements View {
    private final Gate<ContactDetailsData> forwardContactDetailsDataToCaller = new Gate();
    private final Gate<Void> openContactEmailChange = new Gate();
    private final Gate<Void> openContactEmailDialog = new Gate();
    private final Gate<LocationData> openLocationSelection = new Gate();
    private final Gate<String> showContactDetailsError = new Gate();
    private final Gate<String> showEmail = new Gate();
    private final Gate<Boolean> showEmailChecked = new Gate();
    private final Gate<Pair<String, Boolean>> showLocation = new Gate();
    private final Gate<String> showLocationError = new Gate();
    private final Gate<Boolean> showMapInAd = new Gate();
    private final Gate<Boolean> showMapInAdChecked = new Gate();
    private final Gate<String> showPhone = new Gate();
    private final Gate<Boolean> showPhoneChecked = new Gate();
    private final Gate<String> showPhoneError = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedPostAdContactDetailsView$$Lambda$1.lambdaFactory$(this));
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
        this.showLocation.close();
        this.showLocationError.close();
        this.openLocationSelection.close();
        this.showMapInAd.close();
        this.showMapInAdChecked.close();
        this.forwardContactDetailsDataToCaller.close();
        this.showPhoneChecked.close();
        this.showEmailChecked.close();
        this.showPhone.close();
        this.showEmail.close();
        this.openContactEmailDialog.close();
        this.openContactEmailChange.close();
        this.showPhoneError.close();
        this.showContactDetailsError.close();
    }

    private void open(View view) {
        this.showLocation.open(GatedPostAdContactDetailsView$$Lambda$2.lambdaFactory$(view));
        this.showLocationError.open(GatedPostAdContactDetailsView$$Lambda$3.lambdaFactory$(view));
        this.openLocationSelection.open(GatedPostAdContactDetailsView$$Lambda$4.lambdaFactory$(view));
        this.showMapInAd.open(GatedPostAdContactDetailsView$$Lambda$5.lambdaFactory$(view));
        this.showMapInAdChecked.open(GatedPostAdContactDetailsView$$Lambda$6.lambdaFactory$(view));
        this.forwardContactDetailsDataToCaller.open(GatedPostAdContactDetailsView$$Lambda$7.lambdaFactory$(view));
        this.showPhoneChecked.open(GatedPostAdContactDetailsView$$Lambda$8.lambdaFactory$(view));
        this.showEmailChecked.open(GatedPostAdContactDetailsView$$Lambda$9.lambdaFactory$(view));
        this.showPhone.open(GatedPostAdContactDetailsView$$Lambda$10.lambdaFactory$(view));
        this.showEmail.open(GatedPostAdContactDetailsView$$Lambda$11.lambdaFactory$(view));
        this.openContactEmailDialog.open(GatedPostAdContactDetailsView$$Lambda$12.lambdaFactory$(view));
        this.openContactEmailChange.open(GatedPostAdContactDetailsView$$Lambda$13.lambdaFactory$(view));
        this.showPhoneError.open(GatedPostAdContactDetailsView$$Lambda$14.lambdaFactory$(view));
        this.showContactDetailsError.open(GatedPostAdContactDetailsView$$Lambda$15.lambdaFactory$(view));
    }

    public void showLocation(String str, boolean z) {
        this.showLocation.perform(Pair.of(str, Boolean.valueOf(z)));
    }

    public void showLocationError(String str) {
        this.showLocationError.perform(str);
    }

    public void showMapInAdField(boolean z) {
        this.showMapInAd.perform(Boolean.valueOf(z));
    }

    public void showMapInAdChecked(boolean z) {
        this.showMapInAdChecked.perform(Boolean.valueOf(z));
    }

    public void openLocationSelection(LocationData locationData) {
        this.openLocationSelection.perform(locationData);
    }

    public void setContactDetailsDataResult(ContactDetailsData contactDetailsData) {
        this.forwardContactDetailsDataToCaller.perform(contactDetailsData);
    }

    public void showEmail(String str) {
        this.showEmail.perform(str);
    }

    public void showEmailChecked(boolean z) {
        this.showEmailChecked.perform(Boolean.valueOf(z));
    }

    public void showPhone(String str) {
        this.showPhone.perform(str);
    }

    public void showPhoneChecked(boolean z) {
        this.showPhoneChecked.perform(Boolean.valueOf(z));
    }

    public void openContactEmailDialog() {
        this.openContactEmailDialog.perform(null);
    }

    public void openContactEmailChange() {
        this.openContactEmailChange.perform(null);
    }

    public void showPhoneError(String str) {
        this.showPhoneError.perform(str);
    }

    public void showContactDetailsError(String str) {
        this.showContactDetailsError.perform(str);
    }
}

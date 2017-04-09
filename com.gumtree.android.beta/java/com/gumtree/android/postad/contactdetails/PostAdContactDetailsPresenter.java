package com.gumtree.android.postad.contactdetails;

import com.gumtree.android.location.model.LocationData;
import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;
import com.gumtree.android.postad.contactdetails.models.ContactDetailsData;

public interface PostAdContactDetailsPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void openContactEmailChange();

        void openContactEmailDialog();

        void openLocationSelection(LocationData locationData);

        void setContactDetailsDataResult(ContactDetailsData contactDetailsData);

        void showContactDetailsError(String str);

        void showEmail(String str);

        void showEmailChecked(boolean z);

        void showLocation(String str, boolean z);

        void showLocationError(String str);

        void showMapInAdChecked(boolean z);

        void showMapInAdField(boolean z);

        void showPhone(String str);

        void showPhoneChecked(boolean z);

        void showPhoneError(String str);
    }

    void onChangeContactEmailClicked();

    void onContactDetailsFinished();

    void onEmailChecked(boolean z);

    void onEmailClicked();

    void onLocationChanged(LocationData locationData);

    void onLocationClicked();

    void onPhoneChanged(String str);

    void onPhoneChecked(boolean z);

    void onShowMapInAdChecked(boolean z);
}

package com.gumtree.android.postad.customdetails;

import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;
import com.gumtree.android.postad.customdetails.models.CustomAttributeData;
import com.gumtree.android.postad.customdetails.models.CustomDetailsData;
import java.util.List;

public interface CustomDetailsPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void clearScreen();

        void closingScreen(CustomDetailsData customDetailsData);

        void hideVrmManualEntry();

        void populateCustomAttributes(List<CustomAttributeData> list);

        void showErrorMessage(String str);

        void showHiddenFields();

        void showLoading(boolean z);

        void showVrmNotRecognised();

        void validateAllFields();
    }

    boolean getPopupShown();

    void onAttributeValueUpdated(String str, String str2, String str3, String str4);

    void onClosingScreen();

    void onInitialise();

    void onPopupShown();

    void onVrmLookupRequested(String str);

    void onVrmManualEntry();
}

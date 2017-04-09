package com.gumtree.android.location.presenters;

import com.gumtree.android.location.model.LocationData;
import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;
import java.util.List;

public interface LocationPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void completeWithLocation(LocationData locationData);

        void enableCurrentLocation(boolean z);

        void showErrorMessage(String str);
    }

    List<LocationData> getBlockingLocations(String str);

    void getCurrentLocation();

    void onCurrentLocationClicked();

    void onSuggestionClicked(LocationData locationData);

    void onTextChanged(String str);
}

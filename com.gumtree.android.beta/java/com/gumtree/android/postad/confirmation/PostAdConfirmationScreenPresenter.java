package com.gumtree.android.postad.confirmation;

import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;
import com.gumtree.android.postad.confirmation.models.ConfirmationScreenResults;
import com.gumtree.android.postad.confirmation.models.SuccessPostResult;

public interface PostAdConfirmationScreenPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void completeWithResult(ConfirmationScreenResults confirmationScreenResults);

        void showFailedPost();

        void showPostOnHold();

        void showSuccessPost(SuccessPostResult successPostResult);
    }

    void onCloseSelected();

    void onFindOutMoreSelected();

    void onManageAdSelected();

    void onPostAnotherAdSelected();
}

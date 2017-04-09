package com.gumtree.android.manageads.presenter;

import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;

public interface ManageAdsPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void refreshContent();

        void showActiveFragment();

        void showInactiveFragment();

        void showLoginPage();

        void showPostAdActivity();
    }

    void navigateTo(ManageAdsNavView manageAdsNavView);

    void onNewAdFound();

    void onPostAdClick();
}

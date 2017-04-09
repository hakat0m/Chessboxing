package com.gumtree.android.auth.presenter;

import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;

public interface AuthPresenter<A> extends AuthNav<A>, Presenter<View> {

    public interface View extends PresenterView {
        void hideTabs();

        void showHomeActivity();

        void showLogin();

        void showPreviousActivity();

        void showRegistration();

        void showSplash(boolean z);

        void showTabs();

        void showToolbarTitle(String str);

        void showUserActivationMessage(String str);
    }

    void navigateTo(NavView navView);

    void onBack(int i);
}

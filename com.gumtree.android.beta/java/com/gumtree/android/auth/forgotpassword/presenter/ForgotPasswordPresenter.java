package com.gumtree.android.auth.forgotpassword.presenter;

import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;

public interface ForgotPasswordPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void hideProgress();

        void showEmailSentPage(String str);

        void showError(String str);

        void showForgotPasswordPage();

        void showProgress();
    }

    void back();

    void forgotPassword(String str);
}

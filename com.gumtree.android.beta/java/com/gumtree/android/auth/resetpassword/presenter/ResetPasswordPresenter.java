package com.gumtree.android.auth.resetpassword.presenter;

import com.gumtree.android.auth.services.validators.PasswordStrengthService$PasswordStrength;
import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;

public interface ResetPasswordPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void hidePasswordError();

        void hideProgress();

        void showError(String str);

        void showLoginFragment();

        void showPasswordError();

        void showPasswordStrength(PasswordStrengthService$PasswordStrength passwordStrengthService$PasswordStrength);

        void showProgress();
    }

    void onPasswordChanged(String str);

    void resetPassword(String str);
}

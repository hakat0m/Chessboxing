package com.gumtree.android.auth.google.presenter;

import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.google.model.SmartLockCredential;
import com.gumtree.android.auth.model.AuthResult;
import com.gumtree.android.auth.presenter.AuthNav;
import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;

public interface GoogleLoginPresenter<A> extends AuthNav<A>, Presenter<View> {

    public interface View extends PresenterView {
        void hideProgress();

        void onAccountAdded(AuthIdentifier authIdentifier, AuthResult authResult);

        void showError(String str);

        void showLogo();

        void showNoNetwork();

        void showPage(String str, String str2);

        void showPreviousScreen();

        void showProgress();
    }

    void fillRegistrationFormWithHint(A a, SmartLockCredential smartLockCredential);

    void onGoogleLoginClicked(A a);

    void onGoogleResult(String str, String str2);

    void onResolutionDone(A a);
}

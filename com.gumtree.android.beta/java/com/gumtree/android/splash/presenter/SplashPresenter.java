package com.gumtree.android.splash.presenter;

import com.gumtree.android.auth.google.model.SmartLockCredential;
import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;

public interface SplashPresenter<A> extends Presenter<View> {

    public interface View extends PresenterView {
        void showSmartLockLoginFailure();
    }

    void onResolutionDone(A a);

    void performSmartLockLoginAfterResolve(A a, SmartLockCredential smartLockCredential, boolean z);

    void trySmartLockRequest(A a);
}

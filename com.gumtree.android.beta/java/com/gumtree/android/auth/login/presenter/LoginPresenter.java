package com.gumtree.android.auth.login.presenter;

import com.gumtree.android.mvp.Presenter;

public interface LoginPresenter<T> extends Presenter<View> {
    void forgotPassword(String str);

    void login(T t, String str, String str2);

    void onPasswordChanged(String str);

    void onSmartLockSaveResolved(String str);

    void onUsernameChanged(String str);

    void onViewVisible(boolean z);
}

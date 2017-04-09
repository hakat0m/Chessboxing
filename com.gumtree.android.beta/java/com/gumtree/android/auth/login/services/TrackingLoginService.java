package com.gumtree.android.auth.login.services;

public interface TrackingLoginService {
    void eventLoginAttempt(String str);

    void eventLoginBeginFromActivateLink();

    void eventLoginBeginFromFavourites();

    void eventLoginBeginFromManageAds();

    void eventLoginBeginFromMessages();

    void eventLoginBeginFromPostAd();

    void eventLoginBeginFromResetPassword();

    void eventLoginBeginFromSavedSearches();

    void eventLoginBeginFromSearch();

    void eventLoginBeginFromSettings();

    void eventLoginBeginFromTokenExpired();

    void eventLoginCancel();

    void eventLoginFail(String str, String str2);

    void eventLoginScreenBegin();

    void eventLoginScreenCancel();

    void eventLoginSuccess(String str);

    void eventSaveSmartLockAttempt();

    void eventSaveSmartLockBegin();

    void eventSaveSmartLockCanceled();

    void eventSaveSmartLockFailure();

    void eventSaveSmartLockSuccess();

    void eventSmartLockLoginAttempt(String str);

    void eventSmartLockLoginCancel();

    void eventSmartLockLoginFail(String str, String str2);

    void eventSmartLockLoginSuccess(String str);

    void passwordResetBegin();

    void screenViewSplashScreen();
}

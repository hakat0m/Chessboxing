package com.gumtree.android.deeplinking.activate;

public interface TrackingActivateService {
    void eventActivateAccountAttempt();

    void eventActivateAccountBegin();

    void eventActivateAccountFail();

    void eventActivateAccountLoggedInUser();

    void eventActivateAccountSuccess();
}

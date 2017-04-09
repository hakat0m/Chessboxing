package com.gumtree.android.auth.resetpassword.services;

public interface TrackingResetPasswordService {
    void createPasswordAttempt();

    void createPasswordBegin();

    void createPasswordFail();

    void createPasswordSuccess();
}

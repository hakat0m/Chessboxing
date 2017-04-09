package com.gumtree.android.auth.forgotpassword.services;

public interface TrackingForgotPasswordService {
    void passwordResetAttempt();

    void passwordResetCancel();

    void passwordResetFail();

    void passwordResetSuccess();

    void viewPasswordResetConfirm();
}

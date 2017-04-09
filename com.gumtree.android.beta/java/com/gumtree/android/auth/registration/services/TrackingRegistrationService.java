package com.gumtree.android.auth.registration.services;

import com.gumtree.android.auth.services.validators.PasswordStrengthService$PasswordStrength;

public interface TrackingRegistrationService {
    void eventRegistrationAttempt();

    void eventRegistrationBegin();

    void eventRegistrationCancel();

    void eventRegistrationFail(String str);

    void eventRegistrationPasswordFail(PasswordStrengthService$PasswordStrength passwordStrengthService$PasswordStrength);

    void eventRegistrationRetry();

    void eventRegistrationSuccess();

    void viewRegistration();

    void viewRegistrationConfirm();
}

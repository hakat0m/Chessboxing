package com.gumtree.android.auth.services.validators;

import java.io.Serializable;

public enum PasswordStrengthService$PasswordStrength implements Serializable {
    INVALID_EMPTY,
    OK,
    STRONG,
    INVALID_WEAK,
    INVALID_VERY_WEAK,
    INVALID,
    INVALID_TOO_LONG;

    public boolean isValid() {
        return this == OK || this == STRONG;
    }

    public boolean isWeak() {
        return this == INVALID_WEAK;
    }

    public boolean isVeryWeak() {
        return this == INVALID_VERY_WEAK;
    }

    public boolean isTooLong() {
        return this == INVALID_TOO_LONG;
    }
}

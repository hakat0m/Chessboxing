package com.ebay.classifieds.capi.error;

import java.io.Serializable;

public interface GenericError extends Serializable {
    public static final int TYPE_API = 1;
    public static final int TYPE_EXECUTION = 2;

    int getErrorType();

    String getMessage();
}

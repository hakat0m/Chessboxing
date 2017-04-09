package com.ebay.classifieds.capi.exceptions;

import android.support.annotation.NonNull;

public abstract class ApiException extends Exception {
    @Deprecated
    public abstract int getStatusCode();

    public ApiException(@NonNull String str) {
        super(str);
    }

    public ApiException(@NonNull String str, Throwable th) {
        super(str, th);
    }
}

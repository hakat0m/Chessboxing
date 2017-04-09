package com.facebook.android;

public class FacebookError extends RuntimeException {
    private static final long serialVersionUID = 1;
    private int mErrorCode = 0;
    private String mErrorType;

    @Deprecated
    public FacebookError(String str) {
        super(str);
    }

    @Deprecated
    public FacebookError(String str, String str2, int i) {
        super(str);
        this.mErrorType = str2;
        this.mErrorCode = i;
    }

    @Deprecated
    public int getErrorCode() {
        return this.mErrorCode;
    }

    @Deprecated
    public String getErrorType() {
        return this.mErrorType;
    }
}

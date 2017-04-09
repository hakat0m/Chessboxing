package com.ebay.classifieds.capi.executor;

@Deprecated
public final class ResultError {
    private static final int AUTHORIZATION_ERROR = 401;
    private static final int LIMIT_REACHED = 403;
    private static final int NETWORK = 2;
    private static final int SERVER_ERROR = 500;
    private static final int UNKNOWN = 1;
    private String message;
    private int type = UNKNOWN;

    public static ResultError build(int i) {
        ResultError resultError = new ResultError();
        resultError.type = i;
        return resultError;
    }

    public static ResultError build(Exception exception) {
        ResultError resultError = new ResultError();
        if (exception instanceof ResponseException) {
            resultError.type = NETWORK;
        } else {
            resultError.type = SERVER_ERROR;
        }
        resultError.setMessage(exception.getMessage());
        return resultError;
    }

    public static ResultError authorization() {
        return build((int) AUTHORIZATION_ERROR);
    }

    public static ResultError server() {
        return build((int) SERVER_ERROR);
    }

    public static ResultError unknown() {
        return build((int) UNKNOWN);
    }

    public static ResultError limitReached() {
        return build((int) LIMIT_REACHED);
    }

    public static ResultError network() {
        return build((int) NETWORK);
    }

    public boolean isAuthorization() {
        return this.type == AUTHORIZATION_ERROR;
    }

    public boolean isServer() {
        return this.type == SERVER_ERROR;
    }

    public boolean isLimitReached() {
        return this.type == LIMIT_REACHED;
    }

    public boolean isUnknown() {
        return this.type == UNKNOWN;
    }

    public boolean isNetwork() {
        return this.type == NETWORK;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }
}

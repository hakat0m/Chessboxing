package com.ebay.classifieds.capi.executor;

@Deprecated
public class ResponseException extends RuntimeException {
    private final ResultError error;

    public ResponseException(ResultError resultError) {
        this.error = resultError;
    }

    public ResponseException(String str) {
        if (str == null || !str.toLowerCase().contains("authentication")) {
            this.error = ResultError.network();
        } else {
            this.error = ResultError.authorization();
        }
    }

    public ResultError getError() {
        return this.error;
    }
}

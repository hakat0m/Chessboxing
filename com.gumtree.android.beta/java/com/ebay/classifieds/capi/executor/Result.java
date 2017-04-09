package com.ebay.classifieds.capi.executor;

import com.ebay.classifieds.capi.error.GenericError;
import com.ebay.classifieds.capi.exceptions.ApiException;
import uk.co.senab.photoview.IPhotoView;

@Deprecated
public class Result<T> {
    private final T data;
    private final GenericError error;
    private final int statusCode;

    public Result(T t) {
        this.data = t;
        this.error = null;
        this.statusCode = IPhotoView.DEFAULT_ZOOM_DURATION;
    }

    public Result(ApiException apiException) {
        this.error = new ExecutionError(apiException.getMessage());
        this.statusCode = apiException.getStatusCode();
        this.data = null;
    }

    @Deprecated
    public Result(ResultError resultError) {
        this.data = null;
        this.error = new ExecutionError(resultError.getMessage());
        this.statusCode = resultError.getType();
    }

    public boolean hasError() {
        return this.error != null;
    }

    public GenericError getError() {
        return this.error;
    }

    public T getData() {
        return this.data;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}

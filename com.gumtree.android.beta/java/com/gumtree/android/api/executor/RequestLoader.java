package com.gumtree.android.api.executor;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import com.ebay.classifieds.capi.executor.Request;
import com.ebay.classifieds.capi.executor.Result;

public class RequestLoader<T> extends AsyncTaskLoader<Result<T>> {
    private final Request<T> request;
    private Result<T> result;

    public RequestLoader(Context context, Request<T> request) {
        super(context.getApplicationContext());
        this.request = request;
    }

    public Result<T> loadInBackground() {
        return this.request.execute();
    }

    protected void onStartLoading() {
        if (takeContentChanged() || requiresRefresh()) {
            forceLoad();
        } else {
            deliverResult(this.result);
        }
    }

    private boolean requiresRefresh() {
        if (this.result == null) {
            return true;
        }
        return this.result.hasError();
    }

    public void deliverResult(Result<T> result) {
        this.result = result;
        super.deliverResult(result);
    }

    public Request<T> getRequest() {
        return this.request;
    }
}

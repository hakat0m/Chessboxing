package com.gumtree.android.events;

import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.android.ads.PostAdResult;

public class OnPostAdEvent {
    private final Result<PostAdResult> mResult;

    public OnPostAdEvent(Result<PostAdResult> result) {
        this.mResult = result;
    }

    public Result<PostAdResult> getResult() {
        return this.mResult;
    }
}

package com.gumtree.android.events;

import com.gumtree.android.post_ad.model.PostAdData;

public class OnPostAdLoadEvent {
    private final Exception mExc;
    private final PostAdData mPostAdData;

    public OnPostAdLoadEvent(PostAdData postAdData, Exception exception) {
        this.mPostAdData = postAdData;
        this.mExc = exception;
    }

    public PostAdData getPostAdData() {
        return this.mPostAdData;
    }

    public Exception getmExc() {
        return this.mExc;
    }

    public boolean hasError() {
        return this.mExc != null;
    }
}

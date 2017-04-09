package com.gumtree.android.events;

import com.ebay.classifieds.capi.executor.ResultError;
import com.gumtree.android.metadata.SearchMetadata;
import java.util.List;

public class OnMetadataReceivedEvent {
    private final ResultError mResultError;
    private final List<SearchMetadata> mSearchMetadata;

    public OnMetadataReceivedEvent(List<SearchMetadata> list, ResultError resultError) {
        this.mSearchMetadata = list;
        this.mResultError = resultError;
    }

    public List<SearchMetadata> getSearchMetadata() {
        return this.mSearchMetadata;
    }

    public ResultError getResultError() {
        return this.mResultError;
    }
}

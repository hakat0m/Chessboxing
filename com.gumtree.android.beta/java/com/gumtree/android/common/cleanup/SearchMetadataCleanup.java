package com.gumtree.android.common.cleanup;

import android.content.Context;
import com.gumtree.android.model.SearchMetadata;

public class SearchMetadataCleanup implements Cleanup {
    private Context context;

    public SearchMetadataCleanup(Context context) {
        this.context = context;
    }

    public void startCleanup() {
        this.context.getContentResolver().delete(SearchMetadata.URI, "category_id not in (select distinct category_id from ads)", null);
    }
}

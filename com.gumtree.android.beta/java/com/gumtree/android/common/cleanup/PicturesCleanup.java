package com.gumtree.android.common.cleanup;

import android.content.Context;
import com.gumtree.android.model.Pictures;

public class PicturesCleanup implements Cleanup {
    private Context context;

    public PicturesCleanup(Context context) {
        this.context = context;
    }

    public void startCleanup() {
        this.context.getContentResolver().delete(Pictures.URI, "ad_id not in (select distinct uid from ads)", null);
    }
}

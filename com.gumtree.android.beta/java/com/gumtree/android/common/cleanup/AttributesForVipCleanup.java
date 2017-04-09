package com.gumtree.android.common.cleanup;

import android.content.Context;
import com.gumtree.android.model.AttributesForVip;

public class AttributesForVipCleanup implements Cleanup {
    private Context context;

    public AttributesForVipCleanup(Context context) {
        this.context = context;
    }

    public void startCleanup() {
        this.context.getContentResolver().delete(AttributesForVip.URI, "ad_id not in (select _id from saved_ads)", null);
    }
}

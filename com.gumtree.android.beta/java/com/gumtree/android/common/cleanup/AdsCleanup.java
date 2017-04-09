package com.gumtree.android.common.cleanup;

import android.content.Context;
import com.gumtree.android.model.Ads;

public class AdsCleanup implements Cleanup {
    private Context context;

    public AdsCleanup(Context context) {
        this.context = context;
    }

    public void startCleanup() {
        this.context.getContentResolver().delete(Ads.URI, "uid not in (select _id from saved_ads) and  inserted_at < (select inserted_at from ads order by inserted_at desc limit 1) and  inserted_at + 10000 < " + System.currentTimeMillis(), null);
    }
}

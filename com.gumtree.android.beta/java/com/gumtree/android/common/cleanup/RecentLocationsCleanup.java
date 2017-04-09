package com.gumtree.android.common.cleanup;

import android.content.Context;
import com.gumtree.android.model.RecentLocations;

public class RecentLocationsCleanup implements Cleanup {
    private Context context;

    public RecentLocationsCleanup(Context context) {
        this.context = context;
    }

    public void startCleanup() {
        this.context.getContentResolver().delete(RecentLocations.URI, "_id not in (select _id from recent_locations order by inserted_at desc limit 10)", null);
    }
}

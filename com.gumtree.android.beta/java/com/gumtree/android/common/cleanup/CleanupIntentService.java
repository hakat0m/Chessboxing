package com.gumtree.android.common.cleanup;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.model.Configurations;

public class CleanupIntentService extends IntentService {
    public CleanupIntentService() {
        super("com.gumtree.android.CLEANUP");
    }

    protected void onHandleIntent(Intent intent) {
        if (isCleanupSuspended()) {
            Log.v("Cleanup suspended");
            return;
        }
        new AdsCleanup(this).startCleanup();
        new PicturesCleanup(this).startCleanup();
        new AttributesForVipCleanup(this).startCleanup();
        new RecentLocationsCleanup(this).startCleanup();
        new SearchMetadataCleanup(this).startCleanup();
        new ServiceCallRegistryCleanup(this).startCleanup();
    }

    private boolean isCleanupSuspended() {
        try {
            Cursor query = getContentResolver().query(Configurations.URI, null, "type=?", new String[]{"cleanup"}, null);
            if (query.moveToNext()) {
                String string = query.getString(query.getColumnIndex("config_value"));
                Log.v("value is : " + string);
                if ("suspended".equals(string)) {
                    return true;
                }
            }
            Log.v("Cleanup not suspended");
            query.close();
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}

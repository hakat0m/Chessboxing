package com.gumtree.android.locations;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.location.AppLocation;
import com.gumtree.android.common.location.GumtreeLocation;
import com.gumtree.android.model.Locations;
import java.util.Locale;

public class SetLocationActivity extends BaseActivity {
    public static final String IS_NAV = "isNav";

    public static Intent buildIntent(boolean z, String str) {
        if (str.equals("android.intent.action.PICK") || str.equals(StatefulActivity.ACTION_PICK_FOR_POST)) {
            Intent intent = new Intent(str, Locations.URI);
            intent.putExtra(IS_NAV, z);
            intent.setPackage(GumtreeApplication.getPackageNameForIntent());
            return intent;
        }
        throw new IllegalStateException("Unsupported action " + str);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903112);
        findViewById(2131624248).setOnClickListener(SetLocationActivity$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$onCreate$0(View view) {
        LocationActivity.start("android.intent.action.PICK", this);
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == LocationActivity.ACTIVITY_REQUEST_CODE && i2 == -1) {
            try {
                String stringExtra = intent.getStringExtra(StatefulActivity.NAME_LOCATION_ID);
                String stringExtra2 = intent.getStringExtra(StatefulActivity.EXTRA_LOCATION_NAME);
                AppLocation appLocation = GumtreeLocation.get(null, stringExtra, stringExtra2, stringExtra2.toLowerCase(Locale.UK).replace(" ", "-"), BuildConfig.FLAVOR);
                Track.eventSetLocationManually(appLocation);
                LocationsRequest.setAsCurrentLocation(getApplicationContext(), appLocation);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    protected void onStart() {
        super.onStart();
        Track.viewLocation();
    }
}

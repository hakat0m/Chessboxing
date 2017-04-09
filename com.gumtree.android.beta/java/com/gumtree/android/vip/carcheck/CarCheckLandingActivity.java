package com.gumtree.android.vip.carcheck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.analytics.Track;

public class CarCheckLandingActivity extends BaseActivity {
    public static Intent createIntent(Context context) {
        return new Intent(context, CarCheckLandingActivity.class);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903071);
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onBackPressed() {
        Track.eventMotorsCheckCancel();
        super.onBackPressed();
    }
}

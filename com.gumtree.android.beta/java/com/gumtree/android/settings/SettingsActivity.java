package com.gumtree.android.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.gumtree.android.common.activities.BaseActivity;

public class SettingsActivity extends BaseActivity {
    public static Intent createIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903320);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    protected boolean isHomeAsUp() {
        return true;
    }
}

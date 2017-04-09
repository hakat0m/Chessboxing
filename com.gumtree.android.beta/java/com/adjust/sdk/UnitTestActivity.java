package com.adjust.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class UnitTestActivity extends Activity {
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return super.onOptionsItemSelected(menuItem);
    }
}

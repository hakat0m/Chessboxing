package com.gumtree.android.common.activities;

import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseEditActivity extends BaseModalActivity {
    private boolean showSave = true;

    public boolean onCreateOptionsMenu(Menu menu) {
        if (this.showSave) {
            getMenuInflater().inflate(2131755027, menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131624955:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    protected boolean onSave() {
        return false;
    }

    protected void setShowSave(boolean z) {
        this.showSave = z;
    }

    private void save() {
        if (onSave()) {
            finish();
        }
    }
}

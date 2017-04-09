package com.gumtree.android.post_ad.gallery;

import android.os.Bundle;
import android.view.MenuItem;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.post_ad.PostAdBaseActivity;
import com.gumtree.android.post_ad.model.PostAdMemDAO;

public class GalleryActivity extends PostAdBaseActivity {
    protected void onCreate(Bundle bundle) {
        GumtreeApplication.component().inject(this);
        super.onCreate(bundle);
        setContentView(2130903094);
    }

    public void refreshContent(PostAdMemDAO postAdMemDAO) {
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

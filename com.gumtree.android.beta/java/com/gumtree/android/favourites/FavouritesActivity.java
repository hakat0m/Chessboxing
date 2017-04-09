package com.gumtree.android.favourites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.common.AbundanceActivity;
import com.gumtree.android.common.activities.NavigationActivity;
import com.gumtree.android.common.drawer.NavigationItem;
import com.gumtree.android.locations.LocationActivity;

public class FavouritesActivity extends NavigationActivity implements AbundanceActivity {
    private FavouritesFragment mFavouritesFragment;
    private boolean mHasActivityRestarted;
    private boolean mHasLoaderBeenReset;

    public static Intent createIntent(Context context) {
        return new Intent(context, FavouritesActivity.class);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903078, bundle);
        this.mFavouritesFragment = (FavouritesFragment) getSupportFragmentManager().findFragmentById(2131624130);
        initState(bundle);
    }

    private void initState(Bundle bundle) {
        this.mFavouritesFragment.startFavouritesSynchronization(false, bundle);
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != LocationActivity.ACTIVITY_REQUEST_CODE) {
            return;
        }
        if (i2 == -1) {
            if (DevelopmentFlags.getInstance().isNewLoginEnabled() && intent.hasExtra("displayname")) {
                showSnackBar(getString(2131165552, new Object[]{intent.getStringExtra("displayname")}));
            }
            this.mFavouritesFragment.resetLoader(true);
            this.mHasLoaderBeenReset = true;
            return;
        }
        finish();
    }

    protected void onRestart() {
        super.onRestart();
        this.mHasActivityRestarted = true;
    }

    protected void onResume() {
        super.onResume();
        if (this.mHasActivityRestarted && !this.mHasLoaderBeenReset) {
            this.mFavouritesFragment.resetLoader(false);
        }
        this.mHasLoaderBeenReset = false;
    }

    protected NavigationItem getNavigationItem() {
        return NavigationItem.FAVORITES;
    }

    protected String getTrackingView() {
        return "MySavedAds";
    }

    public void setAbundanceInTitle(String str) {
        getSupportActionBar().setSubtitle(Html.fromHtml(str));
    }
}

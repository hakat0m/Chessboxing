package com.gumtree.android.locations.postad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.locations.Location;
import com.ebay.classifieds.capi.locations.Locations;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.api.executor.RequestLoader;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.contracts.HorizontalProgressDisplay;
import com.gumtree.android.common.location.AppLocation;

public class PostAdLocationActivity extends BaseActivity implements LoaderCallbacks<Result<Locations>>, HorizontalProgressDisplay {
    private static final String KEY_LOADING_STATE = "key_loading_state";
    private static final String LOCATION_FREE_TEXT = "location_free_text";
    private static final String LOCATION_LABEL = "location_label";
    private static final String LOCATION_POSTCODE = "location_postcode";
    private static final String LOCATION_VALUE = "location_value";
    private static final String LOCATION_VISIBLE_MAP = "visible_on_map";
    boolean isLoading;
    private String locationFreeText;
    private String locationId;
    private String locationName;
    private String postcode;
    private final OnClickListener selectPostcodeListener = new OnClickListener() {
        public void onClick(View view) {
            PostAdLocationActivity.this.locationFreeText = PostAdLocationActivity.this.getPostcodeLookupFragment().getLocationFreeText();
            PostAdLocationActivity.this.visibleOnMap = Boolean.toString(PostAdLocationActivity.this.getPostcodeLookupFragment().getVisibleOnMapCheckbox());
            PostAdLocationActivity.this.setResult(-1, PostAdLocationActivity.this.getIntentForResult(true));
            PostAdLocationActivity.this.finish();
        }
    };
    private String visibleOnMap;

    public static Intent createIntent(String str, String str2, String str3, String str4, String str5) {
        Intent intent = new Intent(GumtreeApplication.getContext(), PostAdLocationActivity.class);
        intent.putExtra(LOCATION_VALUE, str);
        intent.putExtra(LOCATION_LABEL, str2);
        intent.putExtra(LOCATION_POSTCODE, str3);
        intent.putExtra(LOCATION_VISIBLE_MAP, str4);
        intent.putExtra(LOCATION_FREE_TEXT, str5);
        return intent;
    }

    public static void startForResult(Fragment fragment, String str, String str2, String str3, String str4, String str5) {
        fragment.startActivityForResult(createIntent(str, str2, str3, str4, str5), 0);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903099);
        setupSpinner(bundle);
        this.postcode = getIntent().getStringExtra(LOCATION_POSTCODE);
        this.visibleOnMap = getIntent().getStringExtra(LOCATION_VISIBLE_MAP);
        this.locationName = getIntent().getStringExtra(LOCATION_LABEL);
        this.locationFreeText = getIntent().getStringExtra(LOCATION_FREE_TEXT);
        setupDoneButton();
        initLoader();
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    private void setupDoneButton() {
        getDoneButton().setEnabled(isPostcodeValid());
        getDoneButton().setOnClickListener(this.selectPostcodeListener);
    }

    private void setupSpinner(Bundle bundle) {
        if (bundle == null) {
            showProgress(false);
        } else {
            showProgress(bundle.getBoolean(KEY_LOADING_STATE));
        }
    }

    private void initLoader() {
        getSupportLoaderManager().initLoader(0, null, this);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(KEY_LOADING_STATE, this.isLoading);
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

    public void onActivityResult(int i, int i2, Intent intent) {
        try {
            this.locationId = intent.getStringExtra(StatefulActivity.NAME_LOCATION_ID);
            this.locationName = intent.getStringExtra(StatefulActivity.EXTRA_LOCATION_NAME);
            setResult(-1, getIntentForResult(false));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Intent getIntentForResult(boolean z) {
        String str = null;
        Intent intent = new Intent();
        intent.putExtra(StatefulActivity.NAME_LOCATION_ID, this.locationId);
        intent.putExtra(StatefulActivity.EXTRA_LOCATION_NAME, this.locationName);
        intent.putExtra(AppLocation.ADMOB_DATA_POSTCODE, z ? this.postcode : null);
        String str2 = LOCATION_VISIBLE_MAP;
        if (z) {
            str = this.visibleOnMap;
        }
        intent.putExtra(str2, str);
        if (isLocalAreaValid() && z) {
            intent.putExtra(LOCATION_FREE_TEXT, this.locationFreeText);
        }
        return intent;
    }

    public void onQueryTextSubmit(String str) {
        showProgress(true);
        setPostcode(str);
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    public Loader<Result<Locations>> onCreateLoader(int i, Bundle bundle) {
        return new RequestLoader(getApplicationContext(), new PostcodeLookupRequest(this.postcode));
    }

    public void onLoadFinished(Loader<Result<Locations>> loader, Result<Locations> result) {
        showProgress(false);
        if (result != null) {
            if (result.hasError()) {
                getDoneButton().setEnabled(false);
                setPostcode(null);
                this.locationId = null;
                setLocationName(null);
                setVisibleOnMap(null);
                if (TextUtils.isEmpty(result.getError().getMessage())) {
                    Toast.makeText(this, getString(2131165663), 0).show();
                    return;
                } else {
                    Toast.makeText(this, result.getError().getMessage(), 0).show();
                    return;
                }
            }
            PostcodeLookupFragment postcodeLookupFragment = getPostcodeLookupFragment();
            this.locationId = BuildConfig.FLAVOR + ((Location) ((Locations) result.getData()).getLocations().get(0)).getId();
            setLocationName(((Location) ((Locations) result.getData()).getLocations().get(0)).getLocalizedName());
            this.visibleOnMap = this.visibleOnMap != null ? this.visibleOnMap : Boolean.TRUE.toString();
            postcodeLookupFragment.populatePostcodeLookup((Location) ((Locations) result.getData()).getLocations().get(0));
            getDoneButton().setEnabled(true);
        }
    }

    public void onLoaderReset(Loader<Result<Locations>> loader) {
    }

    public void showProgress(boolean z) {
        this.isLoading = z;
        findViewById(2131624105).setVisibility(this.isLoading ? 0 : 8);
    }

    public String getPostcode() {
        return this.postcode;
    }

    public void setPostcode(String str) {
        this.postcode = str;
    }

    public String getVisibleOnMap() {
        return this.visibleOnMap;
    }

    public void setVisibleOnMap(String str) {
        this.visibleOnMap = str;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public void setLocationName(String str) {
        this.locationName = str;
    }

    private PostcodeLookupFragment getPostcodeLookupFragment() {
        return (PostcodeLookupFragment) getSupportFragmentManager().findFragmentById(2131624212);
    }

    public String getLocationText() {
        return this.locationFreeText;
    }

    public void setLocationText(String str) {
        this.locationFreeText = str;
    }

    public void setLocationId(String str) {
        this.locationId = str;
    }

    public boolean isPostcodeValid() {
        return this.postcode != null && this.postcode.length() > 0;
    }

    public boolean isVisibleOnMapValid() {
        return this.visibleOnMap != null && this.visibleOnMap.length() > 0;
    }

    public boolean isLocalAreaValid() {
        return this.locationFreeText != null && this.locationFreeText.length() > 0;
    }

    public boolean isLocationNameValid() {
        return (getLocationName() == null || getLocationName().length() <= 0 || "Location".equals(getLocationName())) ? false : true;
    }

    public View getDoneButton() {
        return getPostcodeLookupFragment().getDoneButton();
    }
}

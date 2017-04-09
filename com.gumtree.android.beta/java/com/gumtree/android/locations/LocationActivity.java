package com.gumtree.android.locations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentBreadCrumbs;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import com.apptentive.android.sdk.BuildConfig;
import com.apptentive.android.sdk.R;
import com.ebay.classifieds.capi.executor.TreeNode;
import com.ebay.classifieds.capi.locations.Location;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.TreePicker;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.model.Locations;
import java.util.Arrays;
import java.util.List;

public class LocationActivity extends BaseActivity implements TreePicker {
    public static final int ACTIVITY_REQUEST_CODE = 999;

    public static Intent createIntent(String str, String str2, String str3) {
        Intent intent = new Intent(str, Locations.URI.buildUpon().appendPath(str2).build());
        intent.putExtra(StatefulActivity.NAME_LOCATION_ID, str2);
        intent.putExtra(StatefulActivity.EXTRA_LOCATION_NAME, str3);
        intent.setPackage(GumtreeApplication.getPackageNameForIntent());
        return intent;
    }

    public static void start(String str, Activity activity) {
        activity.startActivityForResult(createIntent(str, "10000392", activity.getString(2131165844)), ACTIVITY_REQUEST_CODE);
    }

    public boolean isConnected(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903072);
        initActionBar();
        initLocationItem();
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    private void initLocationItem() {
        if (isFragmentBackstackEmpty()) {
            updateFragments(Arrays.asList(new Location[]{new Location(Long.valueOf(Long.parseLong("10000392")), getResources().getString(2131165844))}));
        }
    }

    private void initActionBar() {
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) getLayoutInflater().inflate(2130903172, null);
        ((FragmentBreadCrumbs) horizontalScrollView.findViewById(2131624396)).setActivity(this);
        getSupportActionBar().setCustomView(horizontalScrollView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void addLocationFragment(Location location) {
        if (isConnected(getApplicationContext())) {
            LocationFragment newInstance = LocationFragment.newInstance(location);
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.setBreadCrumbTitle(location.getLocalizedName());
            beginTransaction.setCustomAnimations(2130968591, 2130968590, 2130968589, 2130968592);
            beginTransaction.replace(2131624115, newInstance, location.getLocalizedName());
            beginTransaction.addToBackStack(location.getLocalizedName());
            beginTransaction.commitAllowingStateLoss();
            return;
        }
        Snackbar.make(findViewById(R.id.content), 2131165663, 0).setAction(2131165891, LocationActivity$$Lambda$1.lambdaFactory$(this, location)).setActionTextColor(ContextCompat.getColor(this, 2131493375)).show();
    }

    /* synthetic */ void lambda$addLocationFragment$0(Location location, View view) {
        onTreeSelect(location);
    }

    public void onBackPressed() {
        finish();
    }

    private void onUpPressed() {
        super.onBackPressed();
        if (isFragmentBackstackEmpty()) {
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onUpPressed();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onTreeSelect(TreeNode treeNode) {
        addLocationFragment((Location) treeNode);
    }

    public void onLeafSelect(TreeNode treeNode) {
        if (treeNode != null) {
            setResult(-1, getIntentForPick(treeNode.getId().longValue(), treeNode.getLocalizedName(), ((Location) treeNode).getLocationBreadcrumb() != null ? ((Location) treeNode).getLocationBreadcrumb().split(";") : null));
            finish();
        }
    }

    public void onParentSelect(TreeNode treeNode) {
        onBackPressed();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    public boolean isTreeSelectionEnabled() {
        return !getIntent().getAction().equals(StatefulActivity.ACTION_PICK_FOR_POST);
    }

    private Intent getIntentForPick(long j, String str, String[] strArr) {
        Intent intent = new Intent();
        intent.putExtra(StatefulActivity.NAME_LOCATION_ID, String.valueOf(j));
        intent.putExtra(StatefulActivity.EXTRA_LOCATION_NAME, str);
        String str2 = BuildConfig.FLAVOR;
        if (strArr != null) {
            int length = strArr.length;
            int i = 0;
            String str3 = str2;
            while (i < length) {
                str2 = strArr[i];
                if (!TextUtils.isEmpty(str3)) {
                    str2 = str3 + ">" + str2;
                }
                i++;
                str3 = str2;
            }
            str2 = str3;
        }
        intent.putExtra(StatefulActivity.EXTRA_PATH_NAMES, str2);
        return intent;
    }

    private void updateFragments(List<Location> list) {
        if (list != null) {
            for (Location addLocationFragment : list) {
                addLocationFragment(addLocationFragment);
            }
        }
    }
}

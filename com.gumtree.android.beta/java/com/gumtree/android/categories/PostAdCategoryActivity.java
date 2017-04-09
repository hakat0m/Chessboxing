package com.gumtree.android.categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentBreadCrumbs;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.category.CategoryItem;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.contracts.HorizontalProgressDisplay;

public class PostAdCategoryActivity extends BaseActivity implements HorizontalProgressDisplay {
    public static final String ADTITLE = "adtitle";
    public static final String PROGRESS = "progress";
    private FragmentBreadCrumbs crumbs;
    private boolean progressVisible;

    public static Intent createIntent(String str) {
        Intent intent = new Intent(GumtreeApplication.getContext(), PostAdCategoryActivity.class);
        intent.putExtra(ADTITLE, str);
        return intent;
    }

    public static void startForResult(Fragment fragment, String str) {
        fragment.startActivityForResult(createIntent(str), 1);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903074);
        if (bundle == null) {
            initHolder();
            showProgress(false);
        } else {
            showProgress(bundle.getBoolean(PROGRESS));
        }
        initActionBar();
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(PROGRESS, this.progressVisible);
    }

    private void initActionBar() {
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) getLayoutInflater().inflate(2130903172, null);
        this.crumbs = (FragmentBreadCrumbs) horizontalScrollView.findViewById(2131624396);
        this.crumbs.setActivity(this);
        this.crumbs.setParentTitle(getResources().getString(2131165920), BuildConfig.FLAVOR, PostAdCategoryActivity$$Lambda$1.lambdaFactory$(this));
        getSupportActionBar().setCustomView(horizontalScrollView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /* synthetic */ void lambda$initActionBar$0(View view) {
        getSupportFragmentManager().popBackStack(null, 1);
    }

    private void initHolder() {
        SuggestedCategoryFragment newInstance = SuggestedCategoryFragment.newInstance(getIntent().getStringExtra(ADTITLE));
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(17432578, 17432579, 17432578, 17432579);
        beginTransaction.replace(2131624122, newInstance, "suggestion");
        beginTransaction.commit();
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

    public void onLeafCategorySelect(CategoryItem categoryItem, Boolean bool, Integer num) {
        Intent intentForPick;
        if (categoryItem.getPath().length >= 2) {
            intentForPick = getIntentForPick(categoryItem.getId().longValue(), categoryItem.getName(), categoryItem.getPath()[1]);
        } else {
            intentForPick = getIntentForPick(categoryItem.getId().longValue(), categoryItem.getName(), BuildConfig.FLAVOR);
        }
        if (bool.booleanValue()) {
            Track.eventPostAdCategorySuggested(categoryItem.getName(), BuildConfig.FLAVOR + num);
        } else {
            Track.eventPostAdCategoryTree(categoryItem.getName());
        }
        setResult(-1, intentForPick);
        finish();
    }

    public void onTreeCategorySelect(CategoryItem categoryItem) {
        addCategoryFragment(categoryItem);
    }

    private void addCategoryFragment(CategoryItem categoryItem) {
        PrimaryCategoryFragment newInstance = PrimaryCategoryFragment.newInstance(categoryItem);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setBreadCrumbTitle(categoryItem.getName());
        beginTransaction.setCustomAnimations(2130968591, 2130968590, 2130968589, 2130968592);
        beginTransaction.replace(2131624122, newInstance, categoryItem.getName());
        beginTransaction.addToBackStack(categoryItem.getName());
        beginTransaction.commit();
    }

    private Intent getIntentForPick(long j, String str, String str2) {
        Intent intent = new Intent();
        intent.putExtra(StatefulActivity.NAME_CATEGORY_ID, String.valueOf(j));
        intent.putExtra(StatefulActivity.EXTRA_CATEGORY_NAME, str);
        intent.putExtra(StatefulActivity.EXTRA_L1_CATEGORY_NAME, str2);
        return intent;
    }

    public void showProgress(boolean z) {
        this.progressVisible = z;
        findViewById(2131624105).setVisibility(this.progressVisible ? 0 : 8);
    }
}

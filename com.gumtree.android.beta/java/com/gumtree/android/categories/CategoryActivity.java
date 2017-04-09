package com.gumtree.android.categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentBreadCrumbs;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.executor.TreeNode;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.ad.search.results.SearchResultsActivity;
import com.gumtree.android.api.executor.RequestLoader;
import com.gumtree.android.category.CategoryItem;
import com.gumtree.android.common.TreePicker;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.location.AppLocation;
import com.gumtree.android.common.location.PreferenceBasedRadiusDAO;
import com.gumtree.android.common.search.Search;
import com.gumtree.android.model.Categories;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import java.util.List;

@Deprecated
public class CategoryActivity extends BaseActivity implements LoaderCallbacks<Result<List<CategoryItem>>>, TreePicker {
    private long categoryId;
    private boolean flag;

    private static Intent getIntent(String str, String str2) {
        Intent intent = new Intent(str, Categories.URI);
        intent.putExtra("parent_id", str2);
        intent.setPackage(GumtreeApplication.getPackageNameForIntent());
        return intent;
    }

    public static void startForResult(Fragment fragment, String str, String str2) {
        fragment.startActivityForResult(getIntent(str, str2), 1);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903072);
        initActionBar();
        initCategoryItem(bundle);
        this.flag = getIntent().getBooleanExtra("flag", false);
    }

    private void initActionBar() {
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) getLayoutInflater().inflate(2130903172, null);
        ((FragmentBreadCrumbs) horizontalScrollView.findViewById(2131624396)).setActivity(this);
        getSupportActionBar().setCustomView(horizontalScrollView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    private void addCategoryFragment(CategoryItem categoryItem) {
        CategoryFragment newInstance = CategoryFragment.newInstance(categoryItem);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setBreadCrumbTitle(categoryItem.getName());
        beginTransaction.setCustomAnimations(2130968591, 2130968590, 2130968589, 2130968592);
        beginTransaction.replace(2131624115, newInstance, categoryItem.getName());
        beginTransaction.addToBackStack(categoryItem.getName());
        beginTransaction.commitAllowingStateLoss();
    }

    public void onBackPressed() {
        onUpPressed();
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

    private void initCategoryItem(Bundle bundle) {
        if (bundle == null) {
            try {
                this.categoryId = Long.parseLong(getIntent().getStringExtra("parent_id"));
            } catch (NumberFormatException e) {
                this.categoryId = Long.parseLong(PaymentConverter.DEFAULT_PAYMENT_METHOD_ID);
            }
        }
        getSupportLoaderManager().initLoader(0, null, this);
    }

    public void onTreeSelect(TreeNode treeNode) {
        addCategoryFragment((CategoryItem) treeNode);
    }

    public void onLeafSelect(TreeNode treeNode) {
        Intent intentForPick;
        if (((CategoryItem) treeNode).getPath().length >= 2) {
            intentForPick = getIntentForPick(treeNode.getId().longValue(), treeNode.getLocalizedName(), ((CategoryItem) treeNode).getPath()[1], ((CategoryItem) treeNode).getPathNamesInString());
        } else {
            intentForPick = getIntentForPick(treeNode.getId().longValue(), treeNode.getLocalizedName(), BuildConfig.FLAVOR, ((CategoryItem) treeNode).getPathNamesInString());
        }
        if (this.flag) {
            redirectToSearch(treeNode);
            return;
        }
        setResult(-1, intentForPick);
        finish();
    }

    private void redirectToSearch(TreeNode treeNode) {
        if (DevelopmentFlags.getInstance().isNewSearchResultsEnabled()) {
            startActivity(SearchResultsActivity.createLaunchIntent(this));
            return;
        }
        AppLocation globalBuyerLocation = ((GumtreeApplication) getApplication()).getGlobalBuyerLocation();
        Search create = Search.create(getApplicationContext());
        create.addLocation(globalBuyerLocation);
        create.addRadius(getApplicationContext(), PreferenceBasedRadiusDAO.get(getApplicationContext()), globalBuyerLocation);
        create.addCategory(treeNode.getId() + BuildConfig.FLAVOR, treeNode.getLocalizedName());
        Search.invoke(this, create);
    }

    public void onParentSelect(TreeNode treeNode) {
        onBackPressed();
    }

    public boolean isTreeSelectionEnabled() {
        return !getIntent().getAction().equals(StatefulActivity.ACTION_PICK_FOR_POST);
    }

    private Intent getIntentForPick(long j, String str, String str2, String str3) {
        Intent intent = new Intent();
        intent.setData(Categories.URI);
        intent.putExtra(StatefulActivity.NAME_CATEGORY_ID, String.valueOf(j));
        intent.putExtra(StatefulActivity.EXTRA_CATEGORY_NAME, str);
        intent.putExtra(StatefulActivity.EXTRA_L1_CATEGORY_NAME, str2);
        intent.putExtra(StatefulActivity.EXTRA_CATEGORY_PATH, str3);
        return intent;
    }

    public Loader<Result<List<CategoryItem>>> onCreateLoader(int i, Bundle bundle) {
        return new RequestLoader(getApplicationContext(), new CategoryRequest(this.categoryId, getApplicationContext()));
    }

    public void onLoadFinished(Loader<Result<List<CategoryItem>>> loader, Result<List<CategoryItem>> result) {
        if (isFragmentBackstackEmpty()) {
            updateFragments((List) result.getData());
        }
    }

    private void updateFragments(List<CategoryItem> list) {
        if (list != null) {
            for (CategoryItem addCategoryFragment : list) {
                addCategoryFragment(addCategoryFragment);
            }
        }
    }

    public void onLoaderReset(Loader<Result<List<CategoryItem>>> loader) {
    }
}

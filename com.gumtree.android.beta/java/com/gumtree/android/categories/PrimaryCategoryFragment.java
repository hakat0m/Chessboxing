package com.gumtree.android.categories;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import com.apptentive.android.sdk.R;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.category.CategoryItem;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.fragments.BaseListFragment;
import com.gumtree.android.model.Categories;

public class PrimaryCategoryFragment extends BaseListFragment implements LoaderCallbacks<Cursor> {
    private static final String CASE_NOT_SUPPORTED = "case not supported ";
    private static final String CAT_ITEM = "cat_item";
    private PostAdCategoryActivity activity;
    private CategoryItem catItem;

    public static PrimaryCategoryFragment newInstance(CategoryItem categoryItem) {
        PrimaryCategoryFragment primaryCategoryFragment = new PrimaryCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CAT_ITEM, categoryItem);
        primaryCategoryFragment.setArguments(bundle);
        return primaryCategoryFragment;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (PostAdCategoryActivity) activity;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.catItem = (CategoryItem) getArguments().getSerializable(CAT_ITEM);
        getLoaderManager().initLoader(10, getBundleForLoader(this.catItem.getId().longValue()), this);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    protected int getLayoutId() {
        return 2130903212;
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case R.styleable.TextInputLayout_hintAnimationEnabled /*10*/:
                return new CursorLoader(this.context, Categories.URI, null, "parent_id=? ", new String[]{String.valueOf(bundle.getLong(StatefulActivity.NAME_CATEGORY_ID))}, "value ASC");
            default:
                Log.e(getClass().getSimpleName(), CASE_NOT_SUPPORTED + i);
                return null;
        }
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case R.styleable.TextInputLayout_hintAnimationEnabled /*10*/:
                Track.viewCategory(String.valueOf(this.catItem.getId()), this.catItem.getName(), ((GumtreeApplication) this.context).getGlobalBuyerLocation());
                setListContent(cursor);
                return;
            default:
                Log.e(getClass().getSimpleName(), CASE_NOT_SUPPORTED + loader.getId());
                return;
        }
    }

    private Bundle getBundleForLoader(long j) {
        Bundle bundle = new Bundle();
        bundle.putLong(StatefulActivity.NAME_CATEGORY_ID, j);
        return bundle;
    }

    public void onListItemClick(ListView listView, View view, int i, long j) {
        super.onListItemClick(listView, view, i, j);
        if (getListAdapter().getChildCount(i) == 0) {
            this.activity.onLeafCategorySelect(getListAdapter().getCategoryItem(i), Boolean.valueOf(false), null);
        } else {
            this.activity.onTreeCategorySelect(getListAdapter().getCategoryItem(i));
        }
    }

    private void setListContent(Cursor cursor) {
        if (getListAdapter() == null) {
            setListAdapter(new CategoryPickerItemAdapter(getActivity(), cursor));
        } else {
            getListAdapter().changeCursor(cursor);
        }
        setListShown(true);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        if (!isAdded()) {
            getListAdapter().swapCursor(null);
            setListAdapter(null);
        }
    }

    public CategoryPickerItemAdapter getListAdapter() {
        return (CategoryPickerItemAdapter) super.getListAdapter();
    }
}

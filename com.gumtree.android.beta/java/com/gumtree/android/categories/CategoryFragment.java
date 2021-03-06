package com.gumtree.android.categories;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.apptentive.android.sdk.R;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.category.CategoryItem;
import com.gumtree.android.common.TreePicker;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.fragments.BaseListFragment;
import com.gumtree.android.model.Categories;

public class CategoryFragment extends BaseListFragment implements LoaderCallbacks<Cursor> {
    private static final String CASE_NOT_SUPPORTED = "case not supported ";
    private static final String CAT_ITEM = "cat_item";
    private static final String SPACE = " ";
    private TreePicker activity;
    private CategoryPickerItemAdapter adapter;
    private CategoryItem catItem;

    public static CategoryFragment newInstance(CategoryItem categoryItem) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CAT_ITEM, categoryItem);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (TreePicker) activity;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.catItem = (CategoryItem) getArguments().getSerializable(CAT_ITEM);
        getLoaderManager().initLoader(10, getBundleForLoader(this.catItem.getId().longValue()), this);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setListContentDescription("List content for " + this.catItem.getName());
    }

    protected int getLayoutId() {
        return 2130903212;
    }

    protected View onCreateHeaderView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        if (this.activity.isTreeSelectionEnabled()) {
            return layoutInflater.inflate(2130903333, null);
        }
        return null;
    }

    protected void onHeaderViewCreated(View view) {
        super.onHeaderViewCreated(view);
        ((TextView) view.findViewById(16908308)).setText(getString(2131165618) + SPACE + getString(2131165861) + SPACE + this.catItem.getName());
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
        if (this.activity.isTreeSelectionEnabled()) {
            i--;
        }
        if (i == -1) {
            this.activity.onLeafSelect(this.catItem);
        } else if (this.adapter.getChildCount(i) == 0) {
            this.activity.onLeafSelect(this.adapter.getCategoryItem(i));
        } else {
            this.activity.onTreeSelect(this.adapter.getCategoryItem(i));
        }
    }

    private void setListContent(Cursor cursor) {
        if (this.adapter == null) {
            this.adapter = new CategoryPickerItemAdapter(getActivity(), cursor);
            setListAdapter(this.adapter);
        } else {
            this.adapter.changeCursor(cursor);
        }
        setListShown(true);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        if (isAdded()) {
            getListAdapter().swapCursor(null);
            setListAdapter(null);
        }
    }

    public CategoryPickerItemAdapter getListAdapter() {
        return (CategoryPickerItemAdapter) super.getListAdapter();
    }
}

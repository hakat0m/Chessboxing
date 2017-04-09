package com.gumtree.android.locations;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.locations.Location;
import com.gumtree.android.api.executor.RequestLoader;
import com.gumtree.android.common.TreePicker;
import com.gumtree.android.common.fragments.BaseListFragment;
import java.util.List;

public class LocationFragment extends BaseListFragment implements LoaderCallbacks<Result<Location>> {
    private TreePicker activity;
    private PickerItemArrayAdapter adapter;
    private Location locItem;

    public static LocationFragment newInstance(Location location) {
        LocationFragment locationFragment = new LocationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Location.class.getSimpleName(), location);
        locationFragment.setArguments(bundle);
        return locationFragment;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (TreePicker) activity;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.locItem = (Location) getArguments().getSerializable(Location.class.getSimpleName());
        getLoaderManager().initLoader(0, null, this);
    }

    protected View onCreateHeaderView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        if (this.activity.isTreeSelectionEnabled()) {
            return layoutInflater.inflate(2130903333, null);
        }
        return null;
    }

    protected void onHeaderViewCreated(View view) {
        super.onHeaderViewCreated(view);
        ((TextView) view.findViewById(16908308)).setText(getString(2131165611) + " " + this.locItem.getLocalizedName());
    }

    protected int getLayoutId() {
        return 2130903213;
    }

    public Loader<Result<Location>> onCreateLoader(int i, Bundle bundle) {
        return new RequestLoader(this.context, new LocationChildrenRequest(this.locItem.getId().longValue()));
    }

    public void onLoadFinished(Loader<Result<Location>> loader, Result<Location> result) {
        if (result == null || result.hasError()) {
            setListShown(true);
            if (result.hasError()) {
                Toast.makeText(getActivity(), result.getError().getMessage(), 1).show();
                return;
            }
            return;
        }
        setListContent(((Location) result.getData()).getLocations());
    }

    public void onLoaderReset(Loader<Result<Location>> loader) {
    }

    public void onListItemClick(ListView listView, View view, int i, long j) {
        super.onListItemClick(listView, view, i, j);
        if (this.activity.isTreeSelectionEnabled()) {
            i--;
        }
        if (i == -1) {
            this.activity.onLeafSelect(this.locItem);
        } else if (this.adapter.hasChildren(i)) {
            this.activity.onTreeSelect(this.adapter.getItem(i));
        } else {
            this.activity.onLeafSelect(this.adapter.getItem(i));
        }
    }

    private void setListContent(List<Location> list) {
        this.adapter = new PickerItemArrayAdapter(list);
        setListAdapter(this.adapter);
        setListShown(true);
    }
}

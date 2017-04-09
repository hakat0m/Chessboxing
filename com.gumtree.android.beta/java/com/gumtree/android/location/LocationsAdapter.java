package com.gumtree.android.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import com.gumtree.android.location.model.LocationData;
import com.gumtree.android.location.presenters.LocationPresenter;
import java.util.List;

public class LocationsAdapter extends ArrayAdapter<LocationData> {
    private final LayoutInflater inflater;
    private LocationPresenter locationPresenter;
    private String previousSearch;

    public LocationsAdapter(Context context, int i, List<LocationData> list, LocationPresenter locationPresenter, String str) {
        super(context, i, list);
        this.inflater = LayoutInflater.from(context);
        this.locationPresenter = locationPresenter;
        this.previousSearch = str;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = this.inflater.inflate(2130903342, viewGroup, false);
            ViewHolder viewHolder2 = new ViewHolder(view);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.displayName(((LocationData) getItem(i)).getDisplayName());
        return view;
    }

    public Filter getFilter() {
        return new 1(this);
    }

    public String getPreviousSearch() {
        return this.previousSearch;
    }
}

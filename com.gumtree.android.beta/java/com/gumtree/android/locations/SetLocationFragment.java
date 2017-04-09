package com.gumtree.android.locations;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.executor.Request;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.kleinanzeigen.imagepicker.R$id;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.api.executor.RequestLoader;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.location.AppLocation;
import com.gumtree.android.common.views.ExpandableHeightListView;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.gumloc.Gumloc;
import com.gumtree.android.gumloc.Gumloc.GumlocRequest;
import com.gumtree.android.gumloc.Location;
import com.gumtree.android.gumloc.LocationUpdateEvent;
import com.gumtree.android.gumloc.async.GeocoderRequest;
import com.gumtree.android.gumloc.async.LatLngRequest;
import com.gumtree.android.gumloc.exception.LocationServiceNotAvailable;
import com.gumtree.android.locations.GeocoderDataWatcher.OnPostcodeEntryListener;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SetLocationFragment extends BaseFragment implements LoaderCallbacks<Result<LocationInfo>>, OnPostcodeEntryListener {
    private static final int TEXT_MIN_LENGTH = 3;
    private LatLngRequest currentLocationQuery;
    @Inject
    EventBus eventBus;
    private GeocoderDataWatcher geocoderDataWatcher;
    private Gumloc gumloc;

    public SetLocationFragment() {
        ComponentsManager.get().getAppComponent().inject(this);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.geocoderDataWatcher = GeocoderDataWatcher.create();
        enableLocation(activity);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130903319, viewGroup, false);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.currentLocationQuery != null) {
            bundle.putSerializable(LatLngRequest.class.getSimpleName(), this.currentLocationQuery);
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        SetLocationFragmentPermissionsDispatcher.onRequestPermissionsResult(this, i, iArr);
    }

    public void onStart() {
        super.onStart();
        this.eventBus.register(this);
    }

    public void onStop() {
        super.onStop();
        this.eventBus.unregister(this);
        if (this.gumloc != null) {
            try {
                this.gumloc.stopUpdates();
            } catch (IllegalStateException e) {
            }
        }
    }

    private void disableLocation() {
        if (this.gumloc != null) {
            this.gumloc.disconnect();
        }
    }

    public void onDestroy() {
        disableLocation();
        super.onDestroy();
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (bundle != null) {
            this.currentLocationQuery = (LatLngRequest) bundle.getSerializable(LatLngRequest.class.getSimpleName());
        }
        SetLocationFragmentPermissionsDispatcher.startUpdatesWithCheck(this);
        this.geocoderDataWatcher.attach(getLoaderManager(), this);
        getLoaderManager().restartLoader(2, null, this);
        this.geocoderDataWatcher.enable(getPostcodeSearch(), this);
        getClearRecentLocations().setOnClickListener(SetLocationFragment$$Lambda$1.lambdaFactory$(this));
        getRecentContainer().setExpanded(true);
        getRecentContainer().setOnItemClickListener(SetLocationFragment$$Lambda$2.lambdaFactory$(this));
        getClearPostCodeSearchBtn().setOnClickListener(SetLocationFragment$$Lambda$3.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$onViewCreated$0(View view) {
        clearLocations();
    }

    /* synthetic */ void lambda$onViewCreated$1(AdapterView adapterView, View view, int i, long j) {
        AppLocation item = ((LocationsAdapter) getRecentContainer().getAdapter()).getItem(i);
        if (i == 0 && item.getIsRecent() && !GumtreeApplication.isLocationServicesAvailable()) {
            LocationServicesDialog.newInstance().show(getChildFragmentManager(), "DIALOG");
            return;
        }
        Track.eventSetLocationPostcode(item);
        LocationsRequest.setAsCurrentLocation(this.context, item);
        getActivity().finish();
    }

    /* synthetic */ void lambda$onViewCreated$2(View view) {
        getPostcodeSearch().setText(BuildConfig.FLAVOR);
    }

    private void clearLocations() {
        LocationsRequest.clearAllRecentLocations(this.context);
        if (this.currentLocationQuery == null) {
            getLoaderManager().restartLoader(0, null, this);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(GeocoderRequest.class.getSimpleName(), this.currentLocationQuery);
        getLoaderManager().restartLoader(0, bundle, this);
    }

    public void onResume() {
        super.onResume();
        hideKeyboard(getPostcodeSearch());
    }

    public void onDestroyView() {
        this.geocoderDataWatcher.detach();
        this.geocoderDataWatcher.disable(getPostcodeSearch());
        super.onDestroyView();
    }

    private EditText getPostcodeSearch() {
        return (EditText) getView().findViewById(2131624799);
    }

    private TextView getPostcodeInfo() {
        return (TextView) getView().findViewById(16908292);
    }

    private View getClearRecentLocations() {
        return getView().findViewById(2131624802);
    }

    private ExpandableHeightListView getRecentContainer() {
        return (ExpandableHeightListView) getView().findViewById(16908298);
    }

    private View getProgressLoader() {
        return getView().findViewById(R$id.progress);
    }

    private View getClearPostCodeSearchBtn() {
        return getView().findViewById(2131624800);
    }

    @NeedsPermission({"android.permission.ACCESS_FINE_LOCATION"})
    public void startUpdates() {
        if (this.gumloc != null) {
            this.gumloc.startUpdates(GumlocRequest.create());
        }
    }

    public Loader<Result<LocationInfo>> onCreateLoader(int i, Bundle bundle) {
        Request locationsRequest;
        getProgressLoader().setVisibility(0);
        if (bundle == null || !bundle.containsKey(GeocoderRequest.class.getSimpleName())) {
            locationsRequest = new LocationsRequest(this.context);
        } else {
            locationsRequest = new LocationsRequest(this.context, (GeocoderRequest) bundle.getSerializable(GeocoderRequest.class.getSimpleName()));
        }
        return new RequestLoader(this.context, locationsRequest);
    }

    @Subscribe
    public void onLocationUpdateEvent(LocationUpdateEvent locationUpdateEvent) {
        if (locationUpdateEvent.isLocationFound()) {
            Location location = locationUpdateEvent.getLocation();
            this.currentLocationQuery = LatLngRequest.from(location.getLatitude(), location.getLongitude());
            Bundle bundle = new Bundle();
            bundle.putSerializable(GeocoderRequest.class.getSimpleName(), this.currentLocationQuery);
            getLoaderManager().restartLoader(0, bundle, this);
            this.eventBus.unregister(this);
        }
    }

    public void onLoadFinished(Loader<Result<LocationInfo>> loader, Result<LocationInfo> result) {
        getProgressLoader().setVisibility(8);
        if (getRecentContainer().getAdapter() == null) {
            getRecentContainer().setAdapter(new LocationsAdapter(getActivity(), (LocationInfo) result.getData()));
        } else {
            ((LocationsAdapter) getRecentContainer().getAdapter()).changeContent((LocationInfo) result.getData());
        }
        displayClearRecentButton((LocationInfo) result.getData());
        if (((LocationInfo) result.getData()).hints.size() == 0 && ((LocationInfo) result.getData()).recentLocations.size() == 0) {
            getPostcodeInfo().setVisibility(0);
        } else {
            getPostcodeInfo().setVisibility(8);
        }
    }

    private void displayClearRecentButton(LocationInfo locationInfo) {
        if (locationInfo.recentLocations.size() <= 1) {
            getClearRecentLocations().setVisibility(8);
        } else {
            getClearRecentLocations().setVisibility(0);
        }
    }

    public void enableLocation(Activity activity) {
        try {
            this.gumloc = Gumloc.with(activity).connectWith(new 1(this));
        } catch (LocationServiceNotAvailable e) {
        }
    }

    public void onLoaderReset(Loader<Result<LocationInfo>> loader) {
    }

    public void onPostcodeEntry(boolean z, String str) {
        if (str.length() < TEXT_MIN_LENGTH || z) {
            showRecentIfNoInput(str);
            hideKeyboardIfValidPostcodeEntered(z);
            getPostcodeInfo().setVisibility(4);
            return;
        }
        getClearPostCodeSearchBtn().setVisibility(0);
    }

    private void hideKeyboardIfValidPostcodeEntered(boolean z) {
        if (z) {
            ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(getPostcodeSearch().getWindowToken(), 0);
        }
    }

    private void showRecentIfNoInput(String str) {
        if (str.length() < TEXT_MIN_LENGTH) {
            getClearPostCodeSearchBtn().setVisibility(8);
            getLoaderManager().restartLoader(0, null, this);
            return;
        }
        getClearPostCodeSearchBtn().setVisibility(0);
    }
}

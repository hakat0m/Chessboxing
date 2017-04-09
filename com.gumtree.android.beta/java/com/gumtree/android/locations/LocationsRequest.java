package com.gumtree.android.locations;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.ebay.classifieds.capi.executor.Request;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.loaders.AsyncQuery;
import com.gumtree.android.common.location.AppLocation;
import com.gumtree.android.common.location.GumtreeLocation;
import com.gumtree.android.common.location.LocationDAO;
import com.gumtree.android.gumloc.async.GeocoderRequest;
import com.gumtree.android.gumloc.async.LatLngRequest;
import com.gumtree.android.gumloc.async.loaders.SupportGeocoderLoader;
import com.gumtree.android.gumloc.geocoder.APIAddress;
import com.gumtree.android.gumloc.geocoder.Address;
import com.gumtree.android.gumloc.geocoder.GeocoderResult;
import com.gumtree.android.model.RecentLocations;
import java.util.ArrayList;
import java.util.List;

public class LocationsRequest implements Request<LocationInfo> {
    private final Context context;
    private final GeocoderRequest geoRequest;

    public LocationsRequest(Context context) {
        this.context = context.getApplicationContext();
        this.geoRequest = null;
    }

    public LocationsRequest(Context context, GeocoderRequest geocoderRequest) {
        this.context = context.getApplicationContext();
        this.geoRequest = geocoderRequest;
    }

    public static void setAsCurrentLocation(Context context, AppLocation appLocation) {
        ((GumtreeApplication) context.getApplicationContext()).setGlobalBuyerLocation(appLocation);
        addSelectedLocationAsCurrentLocation(context, appLocation);
        displayLocationSetToast(context, appLocation);
    }

    public static void sync(Context context) {
        addSelectedLocationAsCurrentLocation(context, ((GumtreeApplication) context.getApplicationContext()).getGlobalBuyerLocation());
    }

    private static void displayLocationSetToast(Context context, AppLocation appLocation) {
        Toast.makeText(context.getApplicationContext(), context.getString(2131165629) + " " + appLocation.getDisplayText(false), 1).show();
    }

    private static void addSelectedLocationAsCurrentLocation(Context context, AppLocation appLocation) {
        new AsyncQuery(context.getContentResolver(), null).startInsert(0, Integer.valueOf(0), RecentLocations.URI, LocationDAO.create().toContentValues(appLocation));
    }

    public static void clearAllRecentLocations(Context context) {
        new AsyncQuery(context.getContentResolver(), null).startDelete(0, Integer.valueOf(0), RecentLocations.URI, null, null);
    }

    public Result<LocationInfo> execute() {
        Object locationInfo = new LocationInfo();
        if (this.geoRequest == null || (this.geoRequest instanceof LatLngRequest)) {
            locationInfo.recentLocations = getRecentLocations(this.geoRequest);
            locationInfo.hints = new ArrayList();
        } else {
            locationInfo.hints = getSuggestions();
            locationInfo.recentLocations = new ArrayList();
        }
        return new Result(locationInfo);
    }

    private List<AppLocation> getSuggestions() {
        List arrayList = new ArrayList();
        arrayList.addAll(getPostCodeSuggestions());
        return arrayList;
    }

    private List<AppLocation> getPostCodeSuggestions() {
        ArrayList arrayList = new ArrayList();
        if (this.geoRequest == null || (this.geoRequest instanceof LatLngRequest)) {
            return arrayList;
        }
        GeocoderResult geocoderResult = getGeocoderResult();
        if (geocoderResult.isSuccessful() && geocoderResult.getAddresses() != null) {
            for (Address address : geocoderResult.getAddresses()) {
                if ((address instanceof APIAddress) && !TextUtils.isEmpty(((APIAddress) address).getKeyword())) {
                    arrayList.add(GumtreeLocation.get(((APIAddress) address).getKeyword(), ((APIAddress) address).getLocationId(), ((APIAddress) address).getLocationName(), ((APIAddress) address).getIdName(), ((APIAddress) address).getType()));
                }
            }
        }
        return arrayList;
    }

    private GeocoderResult getGeocoderResult() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("request", this.geoRequest);
        return SupportGeocoderLoader.newLoader(this.context, bundle).loadInBackground();
    }

    private List<AppLocation> getRecentLocations(GeocoderRequest geocoderRequest) {
        List arrayList = new ArrayList();
        if (geocoderRequest != null) {
            GeocoderResult geocoderResult = getGeocoderResult();
            if (geocoderResult.isSuccessful() && geocoderResult.getAddresses().size() > 0) {
                arrayList.add(LocationDAO.create().from((Address) geocoderResult.getAddresses().get(0)));
            }
        }
        arrayList.addAll(LocationDAO.create().getRecentLocations(this.context));
        return arrayList;
    }
}

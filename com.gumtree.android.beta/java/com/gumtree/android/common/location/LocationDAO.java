package com.gumtree.android.common.location;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.Log;
import com.gumtree.android.common.location.GumtreeLocation.GumtreeTypeLocation;
import com.gumtree.android.gumloc.geocoder.Address;
import com.gumtree.android.model.RecentLocations;
import java.util.ArrayList;
import java.util.List;

public final class LocationDAO {
    private static final String DESC = " DESC";
    private static final String LIMIT = "limit";
    private static final int RECENT_LOC_LIMIT = 5;

    private LocationDAO() {
    }

    public static LocationDAO create() {
        return new LocationDAO();
    }

    public AppLocation from(Context context) {
        return getCurrentSavedLocation(context, getDefaultLocation(context));
    }

    public AppLocation from(Context context, AppLocation appLocation) {
        return getCurrentSavedLocation(context, appLocation);
    }

    public AppLocation from(Address address) {
        return GeocoderLocation.get(address);
    }

    private AppLocation getDefaultLocation(Context context) {
        return GumtreeLocation.get(null, "10000392", context.getString(2131165844), "uk", BuildConfig.FLAVOR);
    }

    public List<AppLocation> getRecentLocations(Context context) {
        Cursor query = context.getContentResolver().query(RecentLocations.URI.buildUpon().appendQueryParameter(LIMIT, String.valueOf(RECENT_LOC_LIMIT)).build(), null, null, null, "inserted_at DESC");
        List arrayList = new ArrayList();
        while (query.moveToNext()) {
            arrayList.add(from(query));
        }
        query.close();
        return arrayList;
    }

    private AppLocation getCurrentSavedLocation(Context context, AppLocation appLocation) {
        Cursor query = context.getContentResolver().query(RecentLocations.URI.buildUpon().appendQueryParameter(LIMIT, String.valueOf(1)).build(), null, null, null, "inserted_at DESC");
        if (query != null && query.moveToFirst()) {
            appLocation = from(query);
        }
        if (query != null) {
            query.close();
        }
        return appLocation;
    }

    private AppLocation from(Cursor cursor) {
        String string = cursor.getString(cursor.getColumnIndex("location_id"));
        if (TextUtils.isEmpty(string)) {
            return GeocoderLocation.get(cursor.getDouble(cursor.getColumnIndex("lat")), cursor.getDouble(cursor.getColumnIndex("lng")), cursor.getString(cursor.getColumnIndex("address")), cursor.getString(cursor.getColumnIndex(AppLocation.ADMOB_DATA_POSTCODE)));
        }
        String string2 = cursor.getString(cursor.getColumnIndex(AppLocation.ADMOB_DATA_POSTCODE));
        String string3 = cursor.getString(cursor.getColumnIndex("address"));
        String string4 = cursor.getString(cursor.getColumnIndex("type_location"));
        String string5 = cursor.getString(cursor.getColumnIndex("id_name"));
        if (GumtreeTypeLocation.OUTCODE_POSTCODE.name().equals(string4)) {
            return GumtreeLocation.get(string3, string, string2, string5, string4);
        }
        return GumtreeLocation.get(null, string, string3, string5, string4);
    }

    public ContentValues toContentValues(AppLocation appLocation) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("inserted_at", Long.valueOf(System.currentTimeMillis()));
        switch (1.$SwitchMap$com$gumtree$android$common$location$AppLocation$LocationType[appLocation.getType().ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                GeocoderLocation geocoderLocation = (GeocoderLocation) appLocation;
                contentValues.put("address", geocoderLocation.getName());
                contentValues.put("lat", geocoderLocation.getLat());
                contentValues.put("lng", geocoderLocation.getLng());
                contentValues.put(AppLocation.ADMOB_DATA_POSTCODE, geocoderLocation.getPostCode());
                contentValues.put("_id", geocoderLocation.getLat() + "," + geocoderLocation.getLng());
                break;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                GumtreeLocation gumtreeLocation = (GumtreeLocation) appLocation;
                if (gumtreeLocation instanceof GumtreePostcodeLocation) {
                    contentValues.put(AppLocation.ADMOB_DATA_POSTCODE, gumtreeLocation.getName());
                    contentValues.put("address", ((GumtreePostcodeLocation) gumtreeLocation).getKeyword());
                } else {
                    contentValues.put("address", gumtreeLocation.getName());
                }
                contentValues.put("location_id", gumtreeLocation.getLocationId());
                contentValues.put("type_location", gumtreeLocation.getTypeLocation());
                contentValues.put("id_name", gumtreeLocation.getIdName());
                contentValues.put("_id", gumtreeLocation.getLocationId());
                break;
            default:
                Log.e(getClass().getSimpleName(), "case not supported " + appLocation.getType());
                break;
        }
        return contentValues;
    }
}

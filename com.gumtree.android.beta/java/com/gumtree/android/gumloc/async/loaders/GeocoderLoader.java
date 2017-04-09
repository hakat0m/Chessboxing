package com.gumtree.android.gumloc.async.loaders;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.gumtree.android.gumloc.async.GeocoderRequest;
import com.gumtree.android.gumloc.async.LatLngRequest;
import com.gumtree.android.gumloc.async.QueryRequest;
import com.gumtree.android.gumloc.geocoder.GeocoderException;
import com.gumtree.android.gumloc.geocoder.GeocoderFactory;
import com.gumtree.android.gumloc.geocoder.GeocoderResult;
import java.io.IOException;

@Deprecated
public final class GeocoderLoader extends AsyncTaskLoader<GeocoderResult> {
    private static final int LOADER_ID = 6507;
    private GeocoderResult geocoderResult;
    private final GeocoderRequest request;

    private GeocoderLoader(Context context, GeocoderRequest geocoderRequest) {
        super(context.getApplicationContext());
        this.request = geocoderRequest;
    }

    public static GeocoderLoader newLoader(Context context, Bundle bundle) {
        return new GeocoderLoader(context, (GeocoderRequest) bundle.getSerializable("request"));
    }

    public static void init(String str, LoaderManager loaderManager, LoaderCallbacks<GeocoderResult> loaderCallbacks) {
        loaderManager.initLoader(LOADER_ID, QueryRequest.from(str).getBundle(), loaderCallbacks);
    }

    public static void restart(String str, LoaderManager loaderManager, LoaderCallbacks<GeocoderResult> loaderCallbacks) {
        loaderManager.restartLoader(LOADER_ID, QueryRequest.from(str).getBundle(), loaderCallbacks);
    }

    public static void init(double d, double d2, LoaderManager loaderManager, LoaderCallbacks<GeocoderResult> loaderCallbacks) {
        loaderManager.initLoader(LOADER_ID, LatLngRequest.from(d, d2).getBundle(), loaderCallbacks);
    }

    public static void restart(double d, double d2, LoaderManager loaderManager, LoaderCallbacks<GeocoderResult> loaderCallbacks) {
        loaderManager.restartLoader(LOADER_ID, LatLngRequest.from(d, d2).getBundle(), loaderCallbacks);
    }

    protected void onStartLoading() {
        if (takeContentChanged() || isResultNull()) {
            forceLoad();
        } else {
            deliverResult(this.geocoderResult);
        }
    }

    private boolean isResultNull() {
        if (this.geocoderResult != null && this.geocoderResult.isSuccessful()) {
            return false;
        }
        return true;
    }

    public GeocoderResult loadInBackground() {
        try {
            switch (1.$SwitchMap$com$gumtree$android$gumloc$async$GeocoderRequest$RequestType[this.request.getType().ordinal()]) {
                case HighlightView.GROW_NONE /*1*/:
                    Object query = ((QueryRequest) this.request).getQuery();
                    if (!TextUtils.isEmpty(query)) {
                        this.geocoderResult = successfulGeocode(query);
                        break;
                    }
                    throw new IOException("Invalid input");
                case HighlightView.GROW_LEFT_EDGE /*2*/:
                    LatLngRequest latLngRequest = (LatLngRequest) this.request;
                    double lat = latLngRequest.getLat();
                    double lng = latLngRequest.getLng();
                    if (isLocationValid(lat, lng)) {
                        this.geocoderResult = successfulReverseGeocode(lat, lng);
                        break;
                    }
                    throw new IOException("Invalid input");
                default:
                    throw new IOException("Invalid request");
            }
        } catch (GeocoderException e) {
            e.printStackTrace();
            this.geocoderResult = GeocoderResult.unsuccessful();
        } catch (IOException e2) {
            e2.printStackTrace();
            this.geocoderResult = GeocoderResult.unsuccessful();
        }
        return this.geocoderResult;
    }

    private boolean isLocationValid(double d, double d2) {
        if (d == 0.0d && d2 == 0.0d) {
            return false;
        }
        return true;
    }

    private GeocoderResult successfulGeocode(String str) {
        return GeocoderFactory.getInstance(getContext()).getFromLocationName(str);
    }

    private GeocoderResult successfulReverseGeocode(double d, double d2) {
        return GeocoderFactory.getInstance(getContext()).getFromLocation(d, d2);
    }
}

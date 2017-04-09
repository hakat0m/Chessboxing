package com.gumtree.android.common.location;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import com.gumtree.android.common.location.AppLocation.LocationType;
import com.gumtree.android.common.location.RadiusDAO.RadiusDecimal;

public class PreferenceBasedRadiusDAO implements RadiusDAO {
    private Context context;

    public PreferenceBasedRadiusDAO(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    public static RadiusDAO get(@NonNull Context context) {
        return new PreferenceBasedRadiusDAO(context);
    }

    public String getRadius(AppLocation appLocation) {
        return PreferenceManager.getDefaultSharedPreferences(this.context).getString(RadiusDAO.RADIUS_PREF, getDefault(appLocation));
    }

    public String getRadiusInDecimal(AppLocation appLocation) {
        return RadiusDecimal.getDecimalFromLiteral(PreferenceManager.getDefaultSharedPreferences(this.context).getString(RadiusDAO.RADIUS_PREF, getDefault(appLocation)));
    }

    private String getDefault(AppLocation appLocation) {
        if (LocationType.GeocoderLocation.equals(appLocation.getType())) {
            return GEOCODER_DEFAULT_RADIUS;
        }
        return GUMTREE_DEFAULT_RADIUS;
    }
}

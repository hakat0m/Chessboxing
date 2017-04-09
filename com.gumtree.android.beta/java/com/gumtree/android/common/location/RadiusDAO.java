package com.gumtree.android.common.location;

public interface RadiusDAO {
    public static final String GEOCODER_DEFAULT_RADIUS = RadiusDecimal.FIVE.toString();
    public static final String GUMTREE_DEFAULT_RADIUS = RadiusDecimal.FIVE.toString();
    public static final String RADIUS_PREF = "radius";

    String getRadius(AppLocation appLocation);

    String getRadiusInDecimal(AppLocation appLocation);
}

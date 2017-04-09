package com.gumtree.android.model;

import android.net.Uri;

public interface ManagedAds extends Ads {
    public static final String DIR_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.gumtree.managed_ads";
    public static final String ITEM_CONTENT_TYPE = "vnd.android.cursor.item/vnd.com.gumtree.managed_ads";
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_CREATED = "CREATED";
    public static final String STATUS_PAUSED = "PAUSED";
    public static final String STATUS_PENDING = "PENDING";
    public static final String TABLE_REF = "/managed_ads";
    public static final Uri URI = Uri.parse("content://com.gumtree.android.beta/managed_ads");
}

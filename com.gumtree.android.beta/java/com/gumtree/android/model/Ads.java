package com.gumtree.android.model;

import android.net.Uri;

public interface Ads {
    public static final String DIR_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.gumtree.ads";
    public static final String ITEM_CONTENT_TYPE = "vnd.android.cursor.item/vnd.com.gumtree.ads";
    public static final String TABLE_REF = "/ads";
    public static final Uri URI = Uri.parse("content://com.gumtree.android.beta/ads");
}

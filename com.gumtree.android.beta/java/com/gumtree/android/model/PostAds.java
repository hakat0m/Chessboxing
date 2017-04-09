package com.gumtree.android.model;

import android.net.Uri;

public interface PostAds {
    public static final String ATTRIBUTE_DISABLED = "0";
    public static final String ATTRIBUTE_ENABLED = "1";
    public static final String DIR_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.gumtree.post_ads";
    public static final String ITEM_CONTENT_TYPE = "vnd.android.cursor.item/vnd.com.gumtree.post_ads";
    public static final String NEW_AD = "0";
    public static final String TABLE_REF = "/post_ads";
    public static final Uri URI = Uri.parse("content://com.gumtree.android.beta/post_ads");
}

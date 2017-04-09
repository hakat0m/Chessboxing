package com.gumtree.android.model;

import android.net.Uri;

public interface Configurations {
    public static final String ITEM_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.gumtree.configurations";
    public static final Uri URI = Uri.parse("content://com.gumtree.android.beta/configurations");
}

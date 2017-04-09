package com.gumtree.android.model;

import android.net.Uri;

public interface Messages {
    public static final String DIRECTION_BUYER = "TO_BUYER";
    public static final String DIRECTION_SELLER = "TO_OWNER";
    public static final String ITEM_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.gumtree.messages";
    public static final String TABLE_REF = "/messages";
    public static final Uri URI = Uri.parse("content://com.gumtree.android.beta/messages");
}

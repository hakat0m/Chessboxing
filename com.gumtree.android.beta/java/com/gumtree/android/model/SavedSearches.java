package com.gumtree.android.model;

import android.net.Uri;

public interface SavedSearches {
    public static final String ITEM_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.gumtree.savedsearches";
    public static final String TABLE_REF = "/saved_searches";
    public static final Uri URI = Uri.parse("content://com.gumtree.android.beta/saved_searches");
}

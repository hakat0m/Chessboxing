package com.gumtree.android.common.loaders;

import android.database.Cursor;

public interface AsyncQuery$QueryListener {
    void onComplete(int i, Object obj);

    void onQueryComplete(int i, Object obj, Cursor cursor);
}

package com.gumtree.android.savedsearches;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import com.gumtree.android.model.NewSavedSearches;
import com.gumtree.android.model.SavedSearches;

public class NewSavedSearchesDao {
    private ContentResolver mContentResolver;

    public NewSavedSearchesDao(ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;
    }

    public void addNewSavedSearch(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("uid", str);
        this.mContentResolver.insert(NewSavedSearches.URI, contentValues);
        this.mContentResolver.notifyChange(SavedSearches.URI, null);
    }

    public void removeNewSavedSearchFromLocalId(long j) {
        this.mContentResolver.delete(NewSavedSearches.URI, "_id=?", new String[]{String.valueOf(j)});
        this.mContentResolver.notifyChange(SavedSearches.URI, null);
    }

    public void clearNewSavedSearches() {
        this.mContentResolver.delete(NewSavedSearches.URI, null, null);
        this.mContentResolver.notifyChange(SavedSearches.URI, null);
    }

    public int getNumNewSavedSearches() {
        Cursor query = this.mContentResolver.query(NewSavedSearches.URI, null, null, null, null);
        int count = query.getCount();
        query.close();
        return count;
    }
}

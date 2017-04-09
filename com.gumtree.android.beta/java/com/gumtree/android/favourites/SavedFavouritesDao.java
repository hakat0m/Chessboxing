package com.gumtree.android.favourites;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.gumtree.android.ads.Ad;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.model.SavedAds;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SavedFavouritesDao {
    private static final String EQUAL = "=";
    private static final String ID = "_id=";
    private static final String IN_S = " IN (%s);";
    private static final int NO_STATUS = -1;
    private Context context;

    public class SavedAd {
        private String id;
        private long insertedAt;
        private int syncStatus;

        public String getId() {
            return this.id;
        }

        public void setId(String str) {
            this.id = str;
        }

        public long getInsertedAt() {
            return this.insertedAt;
        }

        public void setInsertedAt(long j) {
            this.insertedAt = j;
        }

        public int getSyncStatus() {
            return this.syncStatus;
        }

        public void setSyncStatus(int i) {
            this.syncStatus = i;
        }
    }

    public SavedFavouritesDao(Context context) {
        this.context = context;
    }

    public List<SavedAd> all() {
        return getFavouriteAds(null);
    }

    public List<SavedAd> synced() {
        return getFavouriteAds("=1");
    }

    private List<SavedAd> getFavouriteAds(String str) {
        String str2 = str != null ? "sync_status" + str : null;
        List<SavedAd> arrayList = new ArrayList();
        Cursor query = getContentResolver().query(SavedAds.URI, null, str2, null, "inserted_at DESC");
        while (query.moveToNext()) {
            SavedAd savedAd = new SavedAd();
            savedAd.setId(query.getString(query.getColumnIndex("_id")));
            savedAd.setInsertedAt(query.getLong(query.getColumnIndex("inserted_at")));
            savedAd.setSyncStatus(query.getInt(query.getColumnIndex("sync_status")));
            arrayList.add(savedAd);
        }
        return arrayList;
    }

    public void persist(String str) {
        persist(str, System.nanoTime());
    }

    public void persist(String str, long j) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", str);
        contentValues.put("inserted_at", Long.valueOf(j));
        contentValues.put("sync_status", Integer.valueOf(1));
        getContentResolver().insert(SavedAds.URI, contentValues);
    }

    public boolean updateFavourite(String str, boolean z) {
        int i;
        Cursor query = getContentResolver().query(SavedAds.URI, null, "_id = " + str, null, null);
        if (query.getCount() != 0) {
            query.moveToFirst();
            i = query.getInt(query.getColumnIndex("sync_status"));
        } else {
            i = NO_STATUS;
        }
        if (z) {
            if (i == 2) {
                updateFavourite(str, 0);
            } else {
                persistFavourite(str, 0);
            }
        } else if (i == 0) {
            delete(str);
            return true;
        } else if (i != NO_STATUS) {
            updateFavourite(str, 2);
        }
        return false;
    }

    public void confirmFavourite(String str, boolean z) {
        if (z) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", str);
            contentValues.put("inserted_at", Long.valueOf(System.nanoTime()));
            contentValues.put("sync_status", Integer.valueOf(1));
            getContentResolver().insert(SavedAds.URI, contentValues);
            return;
        }
        getContentResolver().delete(SavedAds.URI, ID + str, null);
    }

    public void persist(List<String> list) {
        List arrayList = new ArrayList();
        for (String str : list) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", str);
            contentValues.put("inserted_at", Long.valueOf(System.nanoTime()));
            contentValues.put("sync_status", Integer.valueOf(1));
            arrayList.add(contentValues);
        }
        getContentResolver().bulkInsert(SavedAds.URI, (ContentValues[]) arrayList.toArray(new ContentValues[arrayList.size()]));
    }

    public void persist(Collection<Ad> collection) {
        List arrayList = new ArrayList();
        for (Ad ad : collection) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", ad.getId());
            contentValues.put("inserted_at", Long.valueOf(System.nanoTime()));
            contentValues.put("sync_status", Integer.valueOf(1));
            arrayList.add(contentValues);
        }
        getContentResolver().bulkInsert(SavedAds.URI, (ContentValues[]) arrayList.toArray(new ContentValues[arrayList.size()]));
    }

    public void delete(String str) {
        getContentResolver().delete(SavedAds.URI, ID + str, null);
    }

    public void delete(Collection<String> collection) {
        getContentResolver().delete(SavedAds.URI, String.format("_id IN (%s);", new Object[]{SavedFavouritesSync.createCommaSeparatedString(collection)}), null);
    }

    public void deleteSavedAds(Collection<SavedAd> collection) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator it = collection.iterator();
        while (true) {
            stringBuilder.append(((SavedAd) it.next()).getId());
            if (it.hasNext()) {
                stringBuilder.append(",");
            } else {
                getContentResolver().delete(SavedAds.URI, String.format("_id IN (%s);", new Object[]{stringBuilder.toString()}), null);
                return;
            }
        }
    }

    public void update(List<String> list) {
        String createCommaSeparatedString = SavedFavouritesSync.createCommaSeparatedString(list);
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync_status", Integer.valueOf(1));
        getContentResolver().update(SavedAds.URI, contentValues, String.format("_id IN (%s);", new Object[]{createCommaSeparatedString}), null);
    }

    public void update(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", str);
        if (str2 != null) {
            contentValues.put("inserted_at", str2);
        }
        contentValues.put("sync_status", Integer.valueOf(1));
        getContentResolver().update(SavedAds.URI, contentValues, ID + str, null);
    }

    private void updateFavourite(String str, int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", str);
        contentValues.put("inserted_at", Long.valueOf(System.nanoTime()));
        contentValues.put("sync_status", Integer.valueOf(i));
        getContentResolver().update(SavedAds.URI, contentValues, ID + str, null);
    }

    public void persistFavourite(String str, int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", str);
        contentValues.put("inserted_at", Long.valueOf(System.nanoTime()));
        contentValues.put("sync_status", Integer.valueOf(i));
        getContentResolver().insert(SavedAds.URI, contentValues);
    }

    private ContentResolver getContentResolver() {
        return this.context.getContentResolver();
    }

    public void cleanTable() {
        Log.v("Start clean-up for saved ads table");
        getContentResolver().delete(SavedAds.URI, null, null);
    }
}

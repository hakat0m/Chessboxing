package com.gumtree.android.savedsearches;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.alerts.Alert;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.common.utils.UriTools;
import com.gumtree.android.model.SavedSearches;
import com.gumtree.android.model.SavedSearchesView;
import com.gumtree.android.srp.SearchUrl;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SavedSearchesDao {
    private static final String AMP = "&amp;";
    private static final String ATTR = "attr[";
    private static final String CLOSE_BRACKET = "]";
    private static final String COMA = ",";
    private static final String EQUAL = "=";
    private static final String EQUAL_QUESTION_MARK = "=?";
    private static final String ID = "_id=";
    private static final String SEMICOLON = ";";
    private Context context;

    public class Search {
        private String category;
        private long id;
        private boolean isEmailAlertEnabled;
        private String location;
        private String searchIntent;
        private int status;
        private String title;
        private String uid;
        private String url;

        public long getId() {
            return this.id;
        }

        public void setId(long j) {
            this.id = j;
        }

        public String getUid() {
            return this.uid;
        }

        public void setUid(String str) {
            this.uid = str;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public String getSearchIntent() {
            return this.searchIntent;
        }

        public void setSearchIntent(String str) {
            this.searchIntent = str;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String str) {
            this.url = str;
        }

        public String getCategory() {
            return this.category;
        }

        public void setCategory(String str) {
            this.category = str;
        }

        public String getLocation() {
            return this.location;
        }

        public void setLocation(String str) {
            this.location = str;
        }

        public void setIsEmailAlertEnabled(int i) {
            if (i == 0) {
                this.isEmailAlertEnabled = false;
            } else {
                this.isEmailAlertEnabled = true;
            }
        }

        public boolean isEmailAlertEnabled() {
            return this.isEmailAlertEnabled;
        }

        public void setStatus(int i) {
            this.status = i;
        }

        public boolean isToUpdate() {
            return 3 == this.status;
        }
    }

    public SavedSearchesDao(Context context) {
        this.context = context;
    }

    public static String convertToUrl(String str) {
        if (str == null) {
            return null;
        }
        try {
            Bundle extras = Intent.parseUri(str, 0).getExtras();
            if (extras == null) {
                return "?";
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (String str2 : extras.keySet()) {
                String str3;
                Object obj = extras.get(str2);
                if (obj instanceof String) {
                    str3 = (String) obj;
                } else if (obj instanceof Boolean) {
                    str3 = obj.toString();
                } else if (obj instanceof Long) {
                    str3 = BuildConfig.FLAVOR + obj;
                } else if (obj instanceof Double) {
                    str3 = BuildConfig.FLAVOR + obj;
                } else {
                    Log.w("Bundle contains a type that can't be converted : " + obj.getClass());
                    str3 = null;
                }
                if (str3 != null && str3.length() > 0) {
                    str3 = str3.replace("&", BuildConfig.FLAVOR);
                    if (SearchUrl.isPrimarySearchParam(str2)) {
                        stringBuilder.append(str2).append(EQUAL).append(str3).append(AMP);
                    } else {
                        stringBuilder.append(ATTR).append(str2).append(CLOSE_BRACKET).append(EQUAL).append(str3).append(AMP);
                    }
                }
            }
            return "http://capiholder/ads?" + stringBuilder.toString();
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public List<Search> toSync() {
        return getSearches("=0");
    }

    public List<Search> synced() {
        return getSearches("!=0");
    }

    public List<Search> toDelete() {
        return getSearches("=2");
    }

    private List<Search> getSearches(String str) {
        String str2 = "sync_status" + str;
        List<Search> arrayList = new ArrayList();
        Cursor query = getContentResolver().query(SavedSearches.URI, null, str2, null, null);
        while (query.moveToNext()) {
            Search search = new Search();
            search.setId(query.getLong(query.getColumnIndex("_id")));
            search.setUid(query.getString(query.getColumnIndex("uid")));
            search.setSearchIntent(query.getString(query.getColumnIndex("search_intent")));
            search.setLocation(query.getString(query.getColumnIndex("location")));
            search.setCategory(query.getString(query.getColumnIndex("category")));
            search.setTitle(query.getString(query.getColumnIndex(NewPostAdCategoryActivity.EXTRA_TITLE)));
            search.setIsEmailAlertEnabled(query.getInt(query.getColumnIndex("enable_email_alert")));
            search.setStatus(query.getInt(query.getColumnIndex("sync_status")));
            search.setUrl(convertToUrl(search.getSearchIntent()));
            arrayList.add(search);
        }
        return arrayList;
    }

    public void persist(Alert alert) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("search_intent", convertToIntentString(alert));
        contentValues.put(NewPostAdCategoryActivity.EXTRA_TITLE, alert.getTitle());
        contentValues.put("category", alert.getCategory());
        contentValues.put("location", alert.getLocation());
        contentValues.put("uid", alert.getId());
        contentValues.put("enable_email_alert", Integer.valueOf(alert.isEmailAlertEnabledAsInt()));
        contentValues.put("sync_status", Integer.valueOf(1));
        getContentResolver().insert(SavedSearches.URI, contentValues);
    }

    private String convertToIntentString(Alert alert) {
        String link = alert.getLink();
        if (link == null || link.split("ads?").length < 2) {
            return null;
        }
        Uri parse = Uri.parse(link.replace(ATTR, BuildConfig.FLAVOR).replace(CLOSE_BRACKET, BuildConfig.FLAVOR));
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : UriTools.getQueryParameterNames(parse)) {
            stringBuilder.append("S.").append(str).append(EQUAL).append(parse.getQueryParameter(str)).append(SEMICOLON);
        }
        String packageNameForIntent = GumtreeApplication.getPackageNameForIntent();
        stringBuilder.append("S.categoryName=").append(alert.getEncodedCategory()).append(SEMICOLON);
        stringBuilder.append("S.locationName=").append(alert.getEncodedLocation()).append(SEMICOLON);
        return "content://" + packageNameForIntent + "/ads#Intent;" + "action=android.intent.action.VIEW;" + "package=" + packageNameForIntent + SEMICOLON + "component=" + packageNameForIntent + "/com.gumtree.android.srp.SRPActivity;" + stringBuilder + "end";
    }

    public void delete(Search search) {
        getContentResolver().delete(SavedSearches.URI, ID + search.getId(), null);
    }

    public void update(String str, Alert alert) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NewPostAdCategoryActivity.EXTRA_TITLE, alert.getTitle());
        contentValues.put("category", alert.getCategory());
        contentValues.put("location", alert.getLocation());
        contentValues.put("uid", alert.getId());
        contentValues.put("inserted_at", BuildConfig.FLAVOR);
        contentValues.put("sync_status", Integer.valueOf(1));
        contentValues.put("enable_email_alert", Integer.valueOf(alert.isEmailAlertEnabledAsInt()));
        getContentResolver().update(SavedSearches.URI, contentValues, ID + str, null);
    }

    public long saveSearch(com.gumtree.android.common.search.Search search) {
        if (search == null) {
            return -1;
        }
        return ContentUris.parseId(getContentResolver().insert(SavedSearches.URI, getContentValues(search, getTitle(search), false)));
    }

    private ContentValues getContentValues(com.gumtree.android.common.search.Search search, String str, boolean z) {
        int i;
        String stringValue = search.getStringValue(StatefulActivity.EXTRA_LOCATION_NAME);
        if (stringValue.contains(COMA)) {
            stringValue = stringValue.split(COMA)[0];
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("inserted_at", String.valueOf(System.currentTimeMillis()));
        contentValues.put("search_intent", search.getSearchIntent().toUri(0));
        contentValues.put(NewPostAdCategoryActivity.EXTRA_TITLE, str);
        contentValues.put("category", search.getStringValue(StatefulActivity.EXTRA_CATEGORY_NAME));
        contentValues.put("location", stringValue);
        String str2 = "enable_email_alert";
        if (z) {
            i = 1;
        } else {
            i = 0;
        }
        contentValues.put(str2, Integer.valueOf(i));
        return contentValues;
    }

    private String getTitle(com.gumtree.android.common.search.Search search) {
        String stringValue = search.getStringValue(StatefulActivity.NAME_QUERY);
        if (TextUtils.isEmpty(stringValue)) {
            return null;
        }
        return stringValue;
    }

    private ContentResolver getContentResolver() {
        return this.context.getContentResolver();
    }

    public void changeEmailAlert(long j, boolean z) {
        changeEmailAlert(j, z, 3);
    }

    public void changeEmailAlert(long j, boolean z, int i) {
        if (j != -1) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("enable_email_alert", Boolean.valueOf(z));
            contentValues.put("sync_status", Integer.valueOf(i));
            getContentResolver().update(SavedSearches.URI, contentValues, "_id=?", new String[]{String.valueOf(j)});
        }
    }

    public void syncDelete(long j) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync_status", Integer.valueOf(2));
        getContentResolver().update(SavedSearches.URI, contentValues, "_id=?", new String[]{String.valueOf(j)});
    }

    public void syncRestore(long j) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync_status", Integer.valueOf(1));
        getContentResolver().update(SavedSearches.URI, contentValues, "_id=?", new String[]{String.valueOf(j)});
    }

    public Loader<Cursor> getCursorLoader() {
        return new CursorLoader(this.context, SavedSearchesView.URI, null, "sync_status !=2", null, "inserted_at DESC, _id DESC");
    }

    public void cleanTable() {
        Log.v("Start clean-up for saved searches table");
        getContentResolver().delete(SavedSearches.URI, null, null);
    }
}

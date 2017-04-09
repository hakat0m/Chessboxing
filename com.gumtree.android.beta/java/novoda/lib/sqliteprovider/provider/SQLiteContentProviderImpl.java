package novoda.lib.sqliteprovider.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import java.util.List;
import java.util.Map;
import novoda.lib.sqliteprovider.provider.action.InsertHelper;
import novoda.lib.sqliteprovider.sqlite.ExtendedSQLiteOpenHelper;
import novoda.lib.sqliteprovider.sqlite.ExtendedSQLiteQueryBuilder;
import novoda.lib.sqliteprovider.util.Log$Provider;
import novoda.lib.sqliteprovider.util.UriUtils;

public class SQLiteContentProviderImpl extends SQLiteContentProvider {
    private static final String DISTINCT = "distinct";
    private static final String EXPAND = "expand";
    private static final String GROUP_BY = "groupBy";
    private static final String HAVING = "having";
    protected static final String ID = "_id";
    private static final String LIMIT = "limit";
    private InsertHelper helper;
    private final ImplLogger logger = new ImplLogger();

    public boolean onCreate() {
        super.onCreate();
        this.helper = new InsertHelper((ExtendedSQLiteOpenHelper) getDatabaseHelper());
        return true;
    }

    protected SQLiteDatabase getWritableDatabase() {
        return getDatabaseHelper().getWritableDatabase();
    }

    protected SQLiteDatabase getReadableDatabase() {
        return getDatabaseHelper().getReadableDatabase();
    }

    protected SQLiteOpenHelper getDatabaseHelper(Context context) {
        try {
            return new ExtendedSQLiteOpenHelper(context, getCursorFactory());
        } catch (Throwable e) {
            Log$Provider.e(e);
            throw new IllegalStateException(e.getMessage());
        }
    }

    protected Uri insertInTransaction(Uri uri, ContentValues contentValues) {
        Uri insertSilently = insertSilently(uri, contentValues);
        notifyUriChange(uri);
        return insertSilently;
    }

    protected int bulkInsertInTransaction(Uri uri, ContentValues[] contentValuesArr) {
        int i = 0;
        for (ContentValues insertSilently : contentValuesArr) {
            if (insertSilently(uri, insertSilently) != null) {
                i++;
            }
            getWritableDatabase().yieldIfContendedSafely();
        }
        notifyUriChange(uri);
        return i;
    }

    private Uri insertSilently(Uri uri, ContentValues contentValues) {
        return ContentUris.withAppendedId(uri, this.helper.insert(uri, contentValues));
    }

    protected int updateInTransaction(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        int update = getWritableDatabase().update(UriUtils.getItemDirID(uri), contentValues != null ? new ContentValues(contentValues) : new ContentValues(), str, strArr);
        if (update > 0) {
            notifyUriChange(ContentUris.withAppendedId(uri, (long) update));
            return update;
        }
        throw new SQLException("Failed to update row into " + uri + " because it does not exists.");
    }

    protected int deleteInTransaction(Uri uri, String str, String[] strArr) {
        int delete = getWritableDatabase().delete(UriUtils.getItemDirID(uri), str, strArr);
        notifyUriChange(uri);
        return delete;
    }

    protected void notifyChange() {
    }

    public void notifyUriChange(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null, getNotificationSyncToNetwork());
    }

    public boolean getNotificationSyncToNetwork() {
        return false;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        this.logger.logStart(uri);
        ExtendedSQLiteQueryBuilder sQLiteQueryBuilder = getSQLiteQueryBuilder();
        List queryParameters = uri.getQueryParameters(EXPAND);
        String queryParameter = uri.getQueryParameter(GROUP_BY);
        String queryParameter2 = uri.getQueryParameter(HAVING);
        String queryParameter3 = uri.getQueryParameter(LIMIT);
        sQLiteQueryBuilder.setDistinct("true".equals(uri.getQueryParameter(DISTINCT)));
        StringBuilder stringBuilder = new StringBuilder(UriUtils.getItemDirID(uri));
        sQLiteQueryBuilder.setTables(stringBuilder.toString());
        Map map = null;
        if (queryParameters.size() > 0) {
            sQLiteQueryBuilder.addInnerJoin((String[]) queryParameters.toArray(new String[0]));
            map = ((ExtendedSQLiteOpenHelper) getDatabaseHelper()).getProjectionMap(stringBuilder.toString(), (String[]) queryParameters.toArray(new String[0]));
            sQLiteQueryBuilder.setProjectionMap(map);
        }
        CharSequence charSequence;
        if (UriUtils.isItem(uri)) {
            charSequence = "_id=" + uri.getLastPathSegment();
            this.logger.logAppendWhere(charSequence);
            sQLiteQueryBuilder.appendWhere(charSequence);
        } else if (UriUtils.hasParent(uri)) {
            StringBuilder stringBuilder2 = new StringBuilder();
            DatabaseUtils.appendEscapedSQLString(stringBuilder2, UriUtils.getParentId(uri));
            charSequence = UriUtils.getParentColumnName(uri) + ID + "=" + stringBuilder2.toString();
            this.logger.logAppendWhere(charSequence);
            sQLiteQueryBuilder.appendWhere(charSequence);
        }
        this.logger.logEnd(strArr, str, strArr2, str2, sQLiteQueryBuilder, queryParameter, queryParameter2, queryParameter3, map);
        Cursor query = sQLiteQueryBuilder.query(getReadableDatabase(), strArr, str, strArr2, queryParameter, queryParameter2, str2, queryParameter3);
        query.setNotificationUri(getContext().getContentResolver(), getNotificationUri(uri));
        return query;
    }

    protected ExtendedSQLiteQueryBuilder getSQLiteQueryBuilder() {
        return new ExtendedSQLiteQueryBuilder();
    }

    protected CursorFactory getCursorFactory() {
        return null;
    }

    protected Uri getNotificationUri(Uri uri) {
        return uri;
    }
}

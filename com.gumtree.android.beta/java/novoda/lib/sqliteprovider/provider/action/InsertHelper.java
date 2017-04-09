package novoda.lib.sqliteprovider.provider.action;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.message_box.MessagesDao;
import java.util.Arrays;
import java.util.List;
import novoda.lib.sqliteprovider.sqlite.ExtendedSQLiteOpenHelper;
import novoda.lib.sqliteprovider.util.Constraint;
import novoda.lib.sqliteprovider.util.Log$Provider;
import novoda.lib.sqliteprovider.util.UriUtils;

public class InsertHelper {
    private final ExtendedSQLiteOpenHelper dbHelper;

    public InsertHelper(ExtendedSQLiteOpenHelper extendedSQLiteOpenHelper) {
        this.dbHelper = extendedSQLiteOpenHelper;
    }

    public long insert(Uri uri, ContentValues contentValues) {
        long tryUpdateWithConstraint;
        long insert;
        ContentValues contentValues2 = contentValues != null ? new ContentValues(contentValues) : new ContentValues();
        String itemDirID = UriUtils.getItemDirID(uri);
        Constraint firstConstraint = this.dbHelper.getFirstConstraint(itemDirID, contentValues2);
        appendParentReference(uri, contentValues2);
        if (firstConstraint != null) {
            tryUpdateWithConstraint = tryUpdateWithConstraint(itemDirID, firstConstraint, contentValues2);
        } else {
            if (Log$Provider.warningLoggingEnabled()) {
                Log$Provider.w("No constrain against URI: " + uri);
            }
            tryUpdateWithConstraint = -1;
        }
        if (tryUpdateWithConstraint <= 0) {
            insert = this.dbHelper.getWritableDatabase().insert(itemDirID, null, contentValues2);
        } else {
            insert = tryUpdateWithConstraint;
        }
        if (insert != -1) {
            return insert;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Deprecated
    protected long tryUpdateWithConstrain(String str, String str2, ContentValues contentValues) {
        int update = this.dbHelper.getWritableDatabase().update(str, contentValues, str2 + MessagesDao.QUERY, new String[]{contentValues.getAsString(str2)});
        if (Log$Provider.verboseLoggingEnabled()) {
            Log$Provider.v("Constrain " + str2 + " yield " + update);
        }
        if (update <= 0) {
            return -1;
        }
        return getRowIdForUpdate(str, new Constraint(Arrays.asList(new String[]{str2})), contentValues);
    }

    protected long tryUpdateWithConstraint(String str, Constraint constraint, ContentValues contentValues) {
        int update = this.dbHelper.getWritableDatabase().update(str, contentValues, getWhereClause(constraint), getWhereArguments(constraint, contentValues));
        if (Log$Provider.verboseLoggingEnabled()) {
            Log$Provider.v("Constrain " + constraint + " yield " + update);
        }
        if (update > 0) {
            return getRowIdForUpdate(str, constraint, contentValues);
        }
        return -1;
    }

    private String getWhereClause(Constraint constraint) {
        List columns = constraint.getColumns();
        String str = BuildConfig.FLAVOR;
        for (int i = 0; i < columns.size(); i++) {
            String str2 = (String) columns.get(i);
            if (i > 0) {
                str = str + " AND ";
            }
            str = str + str2 + MessagesDao.QUERY;
        }
        return str;
    }

    private String[] getWhereArguments(Constraint constraint, ContentValues contentValues) {
        List columns = constraint.getColumns();
        int size = columns.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = contentValues.getAsString((String) columns.get(i));
        }
        return strArr;
    }

    private long getRowIdForUpdate(String str, Constraint constraint, ContentValues contentValues) {
        String str2 = str;
        Cursor query = this.dbHelper.getReadableDatabase().query(str2, new String[]{"rowid"}, getWhereClause(constraint), getWhereArguments(constraint, contentValues), null, null, null);
        if (!query.moveToFirst()) {
            return -1;
        }
        try {
            long j = query.getLong(0);
            return j;
        } finally {
            query.close();
        }
    }

    protected void appendParentReference(Uri uri, ContentValues contentValues) {
        if (UriUtils.hasParent(uri) && !contentValues.containsKey(UriUtils.getParentId(uri) + "_id")) {
            contentValues.put(UriUtils.getParentColumnName(uri) + "_id", UriUtils.getParentId(uri));
        }
    }
}

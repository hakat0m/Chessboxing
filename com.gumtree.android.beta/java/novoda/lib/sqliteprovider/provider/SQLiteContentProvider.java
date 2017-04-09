package novoda.lib.sqliteprovider.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteTransactionListener;
import android.net.Uri;
import java.util.ArrayList;

public abstract class SQLiteContentProvider extends ContentProvider implements SQLiteTransactionListener {
    private static final int SLEEP_AFTER_YIELD_DELAY = 4000;
    private final ThreadLocal<Boolean> mApplyingBatch = new ThreadLocal();
    private volatile boolean mNotifyChange;
    private SQLiteOpenHelper mOpenHelper;

    protected abstract int bulkInsertInTransaction(Uri uri, ContentValues[] contentValuesArr);

    protected abstract int deleteInTransaction(Uri uri, String str, String[] strArr);

    protected abstract CursorFactory getCursorFactory();

    protected abstract SQLiteOpenHelper getDatabaseHelper(Context context);

    protected abstract Uri insertInTransaction(Uri uri, ContentValues contentValues);

    protected abstract void notifyChange();

    protected abstract int updateInTransaction(Uri uri, ContentValues contentValues, String str, String[] strArr);

    public boolean onCreate() {
        this.mOpenHelper = getDatabaseHelper(getContext());
        return true;
    }

    protected SQLiteOpenHelper getDatabaseHelper() {
        return this.mOpenHelper;
    }

    private boolean applyingBatch() {
        return this.mApplyingBatch.get() != null && ((Boolean) this.mApplyingBatch.get()).booleanValue();
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri insertInTransaction;
        if (applyingBatch()) {
            insertInTransaction = insertInTransaction(uri, contentValues);
            if (insertInTransaction != null) {
                this.mNotifyChange = true;
            }
        } else {
            SQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
            writableDatabase.beginTransactionWithListener(this);
            try {
                insertInTransaction = insertInTransaction(uri, contentValues);
                if (insertInTransaction != null) {
                    this.mNotifyChange = true;
                }
                writableDatabase.setTransactionSuccessful();
                onEndTransaction();
            } finally {
                writableDatabase.endTransaction();
            }
        }
        return insertInTransaction;
    }

    public int bulkInsert(Uri uri, ContentValues[] contentValuesArr) {
        int length = contentValuesArr.length;
        SQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
        writableDatabase.beginTransactionWithListener(this);
        try {
            if (bulkInsertInTransaction(uri, contentValuesArr) != 0) {
                this.mNotifyChange = true;
            }
            writableDatabase.setTransactionSuccessful();
            onEndTransaction();
            return length;
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        int updateInTransaction;
        if (applyingBatch()) {
            updateInTransaction = updateInTransaction(uri, contentValues, str, strArr);
            if (updateInTransaction > 0) {
                this.mNotifyChange = true;
            }
        } else {
            SQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
            writableDatabase.beginTransactionWithListener(this);
            try {
                updateInTransaction = updateInTransaction(uri, contentValues, str, strArr);
                if (updateInTransaction > 0) {
                    this.mNotifyChange = true;
                }
                writableDatabase.setTransactionSuccessful();
                onEndTransaction();
            } finally {
                writableDatabase.endTransaction();
            }
        }
        return updateInTransaction;
    }

    public int delete(Uri uri, String str, String[] strArr) {
        int deleteInTransaction;
        if (applyingBatch()) {
            deleteInTransaction = deleteInTransaction(uri, str, strArr);
            if (deleteInTransaction > 0) {
                this.mNotifyChange = true;
            }
        } else {
            SQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
            writableDatabase.beginTransactionWithListener(this);
            try {
                deleteInTransaction = deleteInTransaction(uri, str, strArr);
                if (deleteInTransaction > 0) {
                    this.mNotifyChange = true;
                }
                writableDatabase.setTransactionSuccessful();
                onEndTransaction();
            } finally {
                writableDatabase.endTransaction();
            }
        }
        return deleteInTransaction;
    }

    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> arrayList) throws OperationApplicationException {
        SQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
        writableDatabase.beginTransactionWithListener(this);
        try {
            this.mApplyingBatch.set(Boolean.valueOf(true));
            int size = arrayList.size();
            ContentProviderResult[] contentProviderResultArr = new ContentProviderResult[size];
            for (int i = 0; i < size; i++) {
                ContentProviderOperation contentProviderOperation = (ContentProviderOperation) arrayList.get(i);
                if (i > 0 && contentProviderOperation.isYieldAllowed()) {
                    writableDatabase.yieldIfContendedSafely(4000);
                }
                contentProviderResultArr[i] = contentProviderOperation.apply(this, contentProviderResultArr, i);
            }
            writableDatabase.setTransactionSuccessful();
            return contentProviderResultArr;
        } finally {
            this.mApplyingBatch.set(Boolean.valueOf(false));
            writableDatabase.endTransaction();
            onEndTransaction();
        }
    }

    public void onBegin() {
        onBeginTransaction();
    }

    public void onCommit() {
        beforeTransactionCommit();
    }

    public void onRollback() {
    }

    protected void onBeginTransaction() {
    }

    protected void beforeTransactionCommit() {
    }

    protected void onEndTransaction() {
        if (this.mNotifyChange) {
            this.mNotifyChange = false;
            notifyChange();
        }
    }
}

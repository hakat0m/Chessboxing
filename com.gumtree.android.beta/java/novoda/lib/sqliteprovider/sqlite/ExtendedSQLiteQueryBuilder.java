package novoda.lib.sqliteprovider.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import java.util.Map;
import java.util.Set;

public class ExtendedSQLiteQueryBuilder {
    private final SQLiteQueryBuilder delegate;

    public ExtendedSQLiteQueryBuilder() {
        this.delegate = new SQLiteQueryBuilder();
    }

    public ExtendedSQLiteQueryBuilder(SQLiteQueryBuilder sQLiteQueryBuilder) {
        this.delegate = sQLiteQueryBuilder;
    }

    public void addInnerJoin(String... strArr) {
        Object tables = this.delegate.getTables();
        if (tables == null || TextUtils.isEmpty(tables)) {
            throw new IllegalStateException("You need to call setTable prior to call addInnerJoin");
        }
        StringBuilder stringBuilder = new StringBuilder(tables);
        for (String str : strArr) {
            stringBuilder.append(String.format(" LEFT JOIN %1$s ON %2$s.%3$s_id=%1$s._id", new Object[]{str, tables, singularize(str)}));
        }
        this.delegate.setTables(stringBuilder.toString());
    }

    private String singularize(String str) {
        return str.endsWith("s") ? str.substring(0, str.length() - 1) : str;
    }

    public String getTables() {
        return this.delegate.getTables();
    }

    public void appendWhere(CharSequence charSequence) {
        this.delegate.appendWhere(charSequence);
    }

    public void appendWhereEscapeString(String str) {
        this.delegate.appendWhereEscapeString(str);
    }

    public Cursor query(SQLiteDatabase sQLiteDatabase, String[] strArr, String str, String[] strArr2, String str2, String str3, String str4) {
        return this.delegate.query(sQLiteDatabase, strArr, str, strArr2, str2, str3, str4);
    }

    public Cursor query(SQLiteDatabase sQLiteDatabase, String[] strArr, String str, String[] strArr2, String str2, String str3, String str4, String str5) {
        return this.delegate.query(sQLiteDatabase, strArr, str, strArr2, str2, str3, str4, str5);
    }

    public String buildQuery(String[] strArr, String str, String[] strArr2, String str2, String str3, String str4, String str5) {
        return this.delegate.buildQuery(strArr, str, strArr2, str2, str3, str4, str5);
    }

    public String buildUnionSubQuery(String str, String[] strArr, Set<String> set, int i, String str2, String str3, String[] strArr2, String str4, String str5) {
        return this.delegate.buildUnionSubQuery(str, strArr, set, i, str2, str3, strArr2, str4, str5);
    }

    public String buildUnionQuery(String[] strArr, String str, String str2) {
        return this.delegate.buildUnionQuery(strArr, str, str2);
    }

    public void setDistinct(boolean z) {
        this.delegate.setDistinct(z);
    }

    public void setTables(String str) {
        this.delegate.setTables(str);
    }

    public void setProjectionMap(Map<String, String> map) {
        this.delegate.setProjectionMap(map);
    }

    public void setCursorFactory(CursorFactory cursorFactory) {
        this.delegate.setCursorFactory(cursorFactory);
    }

    public String toString() {
        return this.delegate.toString();
    }
}

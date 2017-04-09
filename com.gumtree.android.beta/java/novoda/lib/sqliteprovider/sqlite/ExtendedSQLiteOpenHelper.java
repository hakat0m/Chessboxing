package novoda.lib.sqliteprovider.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import novoda.lib.sqliteprovider.migration.Migrations;
import novoda.lib.sqliteprovider.sqlite.IDatabaseMetaInfo.SQLiteType;
import novoda.lib.sqliteprovider.util.Constraint;
import novoda.lib.sqliteprovider.util.DBUtils;
import novoda.lib.sqliteprovider.util.Log.Migration;

public class ExtendedSQLiteOpenHelper extends SQLiteOpenHelper implements IDatabaseMetaInfo {
    private static final String MIGRATIONS_PATH = "migrations";
    @Deprecated
    private final Map<String, List<String>> constrains;
    private final Map<String, List<Constraint>> constraints;
    private final Context context;

    public ExtendedSQLiteOpenHelper(Context context) throws IOException {
        this(context, null);
    }

    public ExtendedSQLiteOpenHelper(Context context, CursorFactory cursorFactory) throws IOException {
        this(context, context.getPackageName() + ".db", cursorFactory, Migrations.getVersion(context.getAssets(), MIGRATIONS_PATH));
    }

    public ExtendedSQLiteOpenHelper(Context context, String str, CursorFactory cursorFactory, int i) {
        super(context, str, cursorFactory, i);
        this.constrains = new HashMap();
        this.constraints = new HashMap();
        this.context = context;
    }

    public Map<String, SQLiteType> getColumns(String str) {
        return DBUtils.getFields(getReadableDatabase(), str);
    }

    public List<String> getTables() {
        return DBUtils.getTables(getReadableDatabase());
    }

    public List<String> getForeignTables(String str) {
        return DBUtils.getForeignTables(getReadableDatabase(), str);
    }

    public int getVersion() {
        return getReadableDatabase().getVersion();
    }

    public void setVersion(int i) {
        getWritableDatabase().setVersion(i);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            Migrations.migrate(sQLiteDatabase, this.context.getAssets(), MIGRATIONS_PATH);
        } catch (Throwable e) {
            Migration.e(e);
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        onCreate(sQLiteDatabase);
    }

    public Map<String, String> getProjectionMap(String str, String... strArr) {
        return DBUtils.getProjectionMap(getReadableDatabase(), str, strArr);
    }

    @Deprecated
    public List<String> getUniqueConstrains(String str) {
        if (!this.constrains.containsKey(str)) {
            this.constrains.put(str, DBUtils.getUniqueConstrains(getReadableDatabase(), str));
        }
        return (List) this.constrains.get(str);
    }

    public List<Constraint> getUniqueConstraints(String str) {
        if (!this.constraints.containsKey(str)) {
            this.constraints.put(str, DBUtils.getUniqueConstraints(getReadableDatabase(), str));
        }
        return (List) this.constraints.get(str);
    }

    @Deprecated
    public String getFirstConstrain(String str, ContentValues contentValues) {
        List<String> uniqueConstrains = getUniqueConstrains(str);
        if (uniqueConstrains == null) {
            return null;
        }
        for (String str2 : uniqueConstrains) {
            if (contentValues.containsKey(str2)) {
                return str2;
            }
        }
        return null;
    }

    public Constraint getFirstConstraint(String str, ContentValues contentValues) {
        List<Constraint> uniqueConstraints = getUniqueConstraints(str);
        if (uniqueConstraints == null) {
            return null;
        }
        for (Constraint constraint : uniqueConstraints) {
            Object obj = 1;
            for (String containsKey : constraint.getColumns()) {
                Object obj2;
                if (contentValues.containsKey(containsKey)) {
                    obj2 = obj;
                } else {
                    obj2 = null;
                }
                obj = obj2;
            }
            if (obj != null) {
                return constraint;
            }
        }
        return null;
    }
}

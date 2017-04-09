package novoda.lib.sqliteprovider.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import novoda.lib.sqliteprovider.sqlite.IDatabaseMetaInfo.SQLiteType;

public final class DBUtils {
    private static final String PRAGMA_TABLE = "PRAGMA table_info(\"%1$s\");";
    private static final String PRGAMA_INDEX_INFO = "PRAGMA index_info('%1$s');";
    private static final String PRGAMA_INDEX_LIST = "PRAGMA index_list('%1$s');";
    private static final String SELECT_TABLES_NAME = "SELECT name FROM sqlite_master WHERE type='table';";
    private static List<String> defaultTables = Arrays.asList(new String[]{"android_metadata"});

    private DBUtils() {
    }

    public static List<String> getForeignTables(SQLiteDatabase sQLiteDatabase, String str) {
        Cursor rawQuery = sQLiteDatabase.rawQuery(String.format(PRAGMA_TABLE, new Object[]{str}), null);
        List tables = getTables(sQLiteDatabase);
        List arrayList = new ArrayList(5);
        while (rawQuery.moveToNext()) {
            String string = rawQuery.getString(rawQuery.getColumnIndexOrThrow("name"));
            if (string.endsWith("_id")) {
                string = string.substring(0, string.lastIndexOf(95));
                if (tables.contains(string + "s")) {
                    arrayList.add(string + "s");
                } else if (tables.contains(string)) {
                    arrayList.add(string);
                }
            }
        }
        rawQuery.close();
        return Collections.unmodifiableList(arrayList);
    }

    public static List<String> getTables(SQLiteDatabase sQLiteDatabase) {
        Cursor rawQuery = sQLiteDatabase.rawQuery(SELECT_TABLES_NAME, null);
        List arrayList = new ArrayList(rawQuery.getCount());
        while (rawQuery.moveToNext()) {
            String string = rawQuery.getString(0);
            if (!defaultTables.contains(string)) {
                arrayList.add(string);
            }
        }
        rawQuery.close();
        return Collections.unmodifiableList(arrayList);
    }

    public static Map<String, String> getProjectionMap(SQLiteDatabase sQLiteDatabase, String str, String... strArr) {
        Map hashMap = new HashMap();
        hashMap.put("_id", str + "._id AS _id");
        for (Entry entry : getFields(sQLiteDatabase, str).entrySet()) {
            hashMap.put(str + "_" + ((String) entry.getKey()), str + "." + ((String) entry.getKey()) + " AS " + str + "_" + ((String) entry.getKey()));
        }
        for (String str2 : strArr) {
            for (Entry entry2 : getFields(sQLiteDatabase, str2).entrySet()) {
                hashMap.put(str2 + "_" + ((String) entry2.getKey()), str2 + "." + ((String) entry2.getKey()) + " AS " + str2 + "_" + ((String) entry2.getKey()));
            }
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public static Map<String, SQLiteType> getFields(SQLiteDatabase sQLiteDatabase, String str) {
        Cursor rawQuery = sQLiteDatabase.rawQuery(String.format(PRAGMA_TABLE, new Object[]{str}), null);
        Map hashMap = new HashMap(rawQuery.getCount());
        while (rawQuery.moveToNext()) {
            hashMap.put(rawQuery.getString(rawQuery.getColumnIndexOrThrow("name")), SQLiteType.valueOf(rawQuery.getString(rawQuery.getColumnIndexOrThrow("type")).toUpperCase()));
        }
        rawQuery.close();
        return Collections.unmodifiableMap(hashMap);
    }

    public static String getSQLiteVersion() {
        Cursor rawQuery = SQLiteDatabase.openOrCreateDatabase(":memory:", null).rawQuery("select sqlite_version() AS sqlite_version", null);
        StringBuilder stringBuilder = new StringBuilder();
        while (rawQuery.moveToNext()) {
            stringBuilder.append(rawQuery.getString(0));
        }
        rawQuery.close();
        return stringBuilder.toString();
    }

    @Deprecated
    public static List<String> getUniqueConstrains(SQLiteDatabase sQLiteDatabase, String str) {
        List<String> arrayList = new ArrayList();
        Cursor rawQuery = sQLiteDatabase.rawQuery(String.format(PRGAMA_INDEX_LIST, new Object[]{str}), null);
        while (rawQuery.moveToNext()) {
            if (rawQuery.getInt(2) == 1) {
                String string = rawQuery.getString(1);
                Cursor rawQuery2 = sQLiteDatabase.rawQuery(String.format(PRGAMA_INDEX_INFO, new Object[]{string}), null);
                if (rawQuery2.moveToFirst()) {
                    arrayList.add(rawQuery2.getString(2));
                }
                rawQuery2.close();
            }
        }
        rawQuery.close();
        return arrayList;
    }

    public static List<Constraint> getUniqueConstraints(SQLiteDatabase sQLiteDatabase, String str) {
        List<Constraint> arrayList = new ArrayList();
        Cursor rawQuery = sQLiteDatabase.rawQuery(String.format(PRGAMA_INDEX_LIST, new Object[]{str}), null);
        while (rawQuery.moveToNext()) {
            if (rawQuery.getInt(2) == 1) {
                String string = rawQuery.getString(1);
                Cursor rawQuery2 = sQLiteDatabase.rawQuery(String.format(PRGAMA_INDEX_INFO, new Object[]{string}), null);
                List arrayList2 = new ArrayList(rawQuery2.getCount());
                while (rawQuery2.moveToNext()) {
                    arrayList2.add(rawQuery2.getString(2));
                }
                rawQuery2.close();
                arrayList.add(new Constraint(arrayList2));
            }
        }
        rawQuery.close();
        return arrayList;
    }
}

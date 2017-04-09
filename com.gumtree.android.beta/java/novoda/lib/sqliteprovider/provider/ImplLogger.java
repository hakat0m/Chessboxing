package novoda.lib.sqliteprovider.provider;

import android.net.Uri;
import java.util.Arrays;
import java.util.Map;
import novoda.lib.sqliteprovider.sqlite.ExtendedSQLiteQueryBuilder;
import novoda.lib.sqliteprovider.util.Log$Provider;

public class ImplLogger {
    protected void logStart(Uri uri) {
        if (Log$Provider.verboseLoggingEnabled()) {
            Log$Provider.v("==================== start of query =======================");
            Log$Provider.v("Uri: " + uri.toString());
        }
    }

    protected void logAppendWhere(String str) {
        if (Log$Provider.verboseLoggingEnabled()) {
            Log$Provider.v("Appending to where clause: " + str);
        }
    }

    protected void logEnd(String[] strArr, String str, String[] strArr2, String str2, ExtendedSQLiteQueryBuilder extendedSQLiteQueryBuilder, String str3, String str4, String str5, Map<String, String> map) {
        if (Log$Provider.verboseLoggingEnabled()) {
            Log$Provider.v("table: " + extendedSQLiteQueryBuilder.getTables());
            if (strArr != null) {
                Log$Provider.v("projection:" + Arrays.toString(strArr));
            }
            if (str != null) {
                Log$Provider.v("selection: " + str + " with arguments " + Arrays.toString(strArr2));
            }
            Log$Provider.v("extra args: " + str3 + " ,having: " + str4 + " ,sort order: " + str2 + " ,limit: " + str5);
            if (map != null) {
                Log$Provider.v("projectionAutomated: " + map);
            }
            Log$Provider.v("==================== end of query =======================");
        }
    }
}

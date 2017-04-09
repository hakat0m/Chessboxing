package novoda.lib.sqliteprovider.util;

import android.net.Uri;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.dfp.DFPProcessor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UriUtils {
    private static Map<String, String> mappedIds = new HashMap();

    public static UriUtils from(Uri uri) {
        UriUtils uriUtils = new UriUtils();
        List pathSegments = uri.getPathSegments();
        String str = BuildConfig.FLAVOR;
        int i = 0;
        while (i < pathSegments.size()) {
            String str2 = (String) pathSegments.get(i);
            if (isNumeric(str2)) {
                int parseInt = Integer.parseInt(str2);
                for (int i2 = 0; i2 < parseInt; i2++) {
                    mappedIds.put(str, str2);
                }
                str2 = str;
            }
            i++;
            str = str2;
        }
        return uriUtils;
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNumberedEntryWithinCollection(Uri uri) {
        return isNumeric(uri.getLastPathSegment());
    }

    public static boolean isDir(Uri uri) {
        return isDir(BuildConfig.FLAVOR, uri);
    }

    public static boolean isDir(String str, Uri uri) {
        return !isItem(str, uri);
    }

    public static boolean isItem(Uri uri) {
        return isItem(BuildConfig.FLAVOR, uri);
    }

    public static boolean isItem(String str, Uri uri) {
        List pathSegments = uri.getPathSegments();
        if (str == null || BuildConfig.FLAVOR.equals(str)) {
            if (pathSegments.size() % 2 != 0) {
                return false;
            }
            return true;
        } else if (((pathSegments.size() - str.split(DFPProcessor.SEPARATOR).length) + 1) % 2 == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static String getItemDirID(Uri uri) {
        return getItemDirID(BuildConfig.FLAVOR, uri);
    }

    public static String getItemDirID(String str, Uri uri) {
        List pathSegments = uri.getPathSegments();
        if (isItem(str, uri)) {
            return (String) pathSegments.get(pathSegments.size() - 2);
        }
        return uri.getLastPathSegment();
    }

    public static boolean hasParent(Uri uri) {
        if (uri.getPathSegments().size() > 2) {
            return true;
        }
        return false;
    }

    public static String getParentColumnName(Uri uri) {
        if (!hasParent(uri)) {
            return BuildConfig.FLAVOR;
        }
        if (isNumberedEntryWithinCollection(uri)) {
            return (String) uri.getPathSegments().get((uri.getPathSegments().size() - 1) - 3);
        }
        return (String) uri.getPathSegments().get((uri.getPathSegments().size() - 1) - 2);
    }

    public static String getParentId(Uri uri) {
        if (!hasParent(uri)) {
            return BuildConfig.FLAVOR;
        }
        if (isNumberedEntryWithinCollection(uri)) {
            return (String) uri.getPathSegments().get((uri.getPathSegments().size() - 1) - 2);
        }
        return (String) uri.getPathSegments().get((uri.getPathSegments().size() - 1) - 1);
    }

    public Map<String, String> getMappedIds() {
        return mappedIds;
    }
}

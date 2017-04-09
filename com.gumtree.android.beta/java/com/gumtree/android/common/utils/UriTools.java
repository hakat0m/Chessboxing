package com.gumtree.android.common.utils;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.common.providers.AppProvider;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import novoda.lib.sqliteprovider.util.UriUtils;

public class UriTools {
    private static final String NOT_HIERARCHICAL = "This isn't a hierarchical URI.";
    private static final String URI_POINTS_TO_A_DIRECTORY = "Uri points to a directory ";

    public static Uri appendQueryParameter(String str, String str2, Uri uri) {
        return uri.buildUpon().appendQueryParameter(str, str2).build();
    }

    public static Uri appendQueryParameter(String str, int i, Uri uri) {
        return uri.buildUpon().appendQueryParameter(str, String.valueOf(i)).build();
    }

    public static Uri appendQueryParameter(String str, long j, Uri uri) {
        return uri.buildUpon().appendQueryParameter(str, String.valueOf(j)).build();
    }

    public static Uri appendQueryParameter(String str, boolean z, Uri uri) {
        return uri.buildUpon().appendQueryParameter(str, String.valueOf(z)).build();
    }

    public static Uri appendPath(String str, Uri uri) {
        return uri.buildUpon().appendPath(str).build();
    }

    public static Uri appendId(long j, Uri uri) {
        return uri.buildUpon().appendPath(String.valueOf(j)).build();
    }

    public static Uri appendId(int i, Uri uri) {
        return uri.buildUpon().appendPath(String.valueOf(i)).build();
    }

    public static String getQueryParameter(String str, Uri uri) {
        if (uri == null || TextUtils.isEmpty(uri.getQueryParameter(str))) {
            return BuildConfig.FLAVOR;
        }
        return uri.getQueryParameter(str);
    }

    public static long getId(Uri uri) {
        try {
            if (UriUtils.isItem(uri)) {
                return Long.parseLong(uri.getLastPathSegment());
            }
            throw new IllegalStateException(URI_POINTS_TO_A_DIRECTORY + uri);
        } catch (NumberFormatException e) {
            throw new IllegalStateException(URI_POINTS_TO_A_DIRECTORY + uri);
        }
    }

    public static boolean hasQueryParameter(String str, Uri uri) {
        if (TextUtils.isEmpty(uri.getQueryParameter(str))) {
            return false;
        }
        return true;
    }

    private static Set<String> getSetOfQueryParameterNames(Uri uri) {
        if (uri.isOpaque()) {
            throw new UnsupportedOperationException(NOT_HIERARCHICAL);
        }
        String encodedQuery = uri.getEncodedQuery();
        if (encodedQuery == null) {
            return Collections.emptySet();
        }
        Set linkedHashSet = new LinkedHashSet();
        int i = 0;
        do {
            int indexOf = encodedQuery.indexOf(38, i);
            if (indexOf == -1) {
                indexOf = encodedQuery.length();
            }
            int indexOf2 = encodedQuery.indexOf(61, i);
            if (indexOf2 > indexOf || indexOf2 == -1) {
                indexOf2 = indexOf;
            }
            linkedHashSet.add(Uri.decode(encodedQuery.substring(i, indexOf2)));
            i = indexOf + 1;
        } while (i < encodedQuery.length());
        return Collections.unmodifiableSet(linkedHashSet);
    }

    @SuppressLint({"NewApi"})
    public static String[] getQueryParameterNames(Uri uri) {
        Set setOfQueryParameterNames;
        if (Integer.parseInt(VERSION.SDK) < 14) {
            setOfQueryParameterNames = getSetOfQueryParameterNames(uri);
        } else {
            setOfQueryParameterNames = uri.getQueryParameterNames();
        }
        return (String[]) setOfQueryParameterNames.toArray(new String[setOfQueryParameterNames.size()]);
    }

    public static boolean isAppAuthority(Uri uri) {
        if (uri.getAuthority().equals(AppProvider.AUTHORITY)) {
            return true;
        }
        return false;
    }
}

/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ContentProvider
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.UriMatcher
 *  android.database.Cursor
 *  android.database.MatrixCursor
 *  android.net.Uri
 *  android.os.ParcelFileDescriptor
 *  java.io.File
 *  java.io.FileNotFoundException
 *  java.lang.Object
 *  java.lang.String
 */
package com.cygery.utilities;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import t4X4QwIV.HfM5AFD8kd7A;

public class g
extends ContentProvider {
    protected static String a;
    protected static final UriMatcher b;
    private static final String c;

    static {
        c = g.class.getName();
        b = new UriMatcher(-1);
    }

    public g(String string) {
        a = string;
    }

    public int delete(Uri uri, String string, String[] arrstring) {
        return 0;
    }

    public String getType(Uri uri) {
        switch (b.match(uri)) {
            default: {
                return null;
            }
            case 1: 
        }
        return "text/plain";
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public boolean onCreate() {
        b.addURI(a, "*", 1);
        return true;
    }

    public ParcelFileDescriptor openFile(Uri uri, String string) {
        switch (b.match(uri)) {
            default: {
                throw new FileNotFoundException("uri not supported: " + uri.toString());
            }
            case 1: 
        }
        if (this.getContext() == null) {
            return null;
        }
        return ParcelFileDescriptor.open((File)new File((Object)this.getContext().getFilesDir() + File.separator + uri.getLastPathSegment()), (int)268435456);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Cursor query(Uri uri, String[] arrstring, String string, String[] arrstring2, String string2) {
        Object[] arrobject;
        int n2;
        File file;
        MatrixCursor matrixCursor;
        switch (b.match(uri)) {
            default: {
                return null;
            }
            case 1: {
                if (this.getContext() == null || !(file = new File((Object)this.getContext().getFilesDir() + File.separator + uri.getLastPathSegment())).exists()) return null;
                matrixCursor = new MatrixCursor(arrstring);
                arrobject = new Object[arrstring.length];
                n2 = 0;
            }
        }
        do {
            if (n2 >= arrstring.length) {
                matrixCursor.addRow(arrobject);
                return matrixCursor;
            }
            if ("_display_name".equals((Object)arrstring[n2])) {
                arrobject[n2] = uri.getLastPathSegment();
            } else if ("_size".equals((Object)arrstring[n2])) {
                arrobject[n2] = HfM5AFD8kd7A.JHWPpkgmOx(file);
            }
            ++n2;
        } while (true);
    }

    public int update(Uri uri, ContentValues contentValues, String string, String[] arrstring) {
        return 0;
    }
}


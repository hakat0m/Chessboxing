package com.apptentive.android.sdk.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.Theme;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.adjust.sdk.Constants;
import com.apptentive.android.sdk.ApptentiveInternal;
import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.R;
import com.apptentive.android.sdk.model.StoredFile;
import com.ebay.kleinanzeigen.imagepicker.EbkImagePicker;
import com.gumtree.android.dfp.DFPProcessor;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.payment.di.PostAdPaymentModule;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import uk.co.senab.photoview.IPhotoView;

public class Util {
    public static final String PSEUDO_ISO8601_DATE_FORMAT = "yyyy-MM-dd HH:mm:ssZ";
    public static final String PSEUDO_ISO8601_DATE_FORMAT_MILLIS = "yyyy-MM-dd HH:mm:ss.SSSZ";

    public static String dateToIso8601String(long j) {
        return dateToString(new SimpleDateFormat(PSEUDO_ISO8601_DATE_FORMAT_MILLIS), new Date(j));
    }

    public static String dateToString(DateFormat dateFormat, Date date) {
        return dateFormat.format(date);
    }

    public static Date parseIso8601Date(String str) {
        String replace = str.trim().replace("Z", "+00:00").replace("T", " ");
        try {
            int lastIndexOf;
            if (replace.charAt(replace.length() - 3) == ':') {
                lastIndexOf = replace.lastIndexOf(":");
                replace = replace.substring(0, lastIndexOf) + replace.substring(lastIndexOf + 1);
            }
            int lastIndexOf2 = replace.lastIndexOf(46);
            lastIndexOf = replace.lastIndexOf(43) != -1 ? replace.lastIndexOf(43) : replace.lastIndexOf(45);
            if (lastIndexOf2 != -1) {
                String substring = replace.substring(0, lastIndexOf2 + 1);
                String substring2 = replace.substring(lastIndexOf2 + 1, lastIndexOf);
                replace = substring + String.format("%-3s", new Object[]{substring2}).replace(" ", DraftAd.NEW_AD_ID) + replace.substring(lastIndexOf);
            }
            try {
                if (replace.contains(".")) {
                    return new SimpleDateFormat(PSEUDO_ISO8601_DATE_FORMAT_MILLIS).parse(replace);
                }
                return new SimpleDateFormat(PSEUDO_ISO8601_DATE_FORMAT).parse(replace);
            } catch (Throwable e) {
                ApptentiveLog.e("Exception parsing date: " + replace, e, new Object[0]);
                return null;
            }
        } catch (Throwable e2) {
            ApptentiveLog.e("Error parsing date: " + str, e2, new Object[0]);
            return new Date();
        }
    }

    public static int getStatusBarHeight(Window window) {
        Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    private static List<PackageInfo> getPermissions(@NonNull Context context) {
        return context.getPackageManager().getInstalledPackages(4096);
    }

    public static boolean packageHasPermission(Context context, String str) {
        if (context == null) {
            return false;
        }
        return packageHasPermission(context, context.getApplicationContext().getPackageName(), str);
    }

    public static boolean packageHasPermission(Context context, String str, String str2) {
        if (context == null) {
            return false;
        }
        for (PackageInfo packageInfo : getPermissions(context)) {
            if (packageInfo.packageName.equals(str) && packageInfo.requestedPermissions != null) {
                for (String equals : packageInfo.requestedPermissions) {
                    if (equals.equals(str2)) {
                        return true;
                    }
                }
                continue;
            }
        }
        return false;
    }

    public static int pixelsToDips(@NonNull Context context, int i) {
        return Math.round(((float) i) / context.getResources().getDisplayMetrics().density);
    }

    public static float dipsToPixels(@NonNull Context context, float f) {
        return context.getResources().getDisplayMetrics().density * f;
    }

    public static float dipsToPixelsFloat(@NonNull Context context, int i) {
        return context.getResources().getDisplayMetrics().density * ((float) i);
    }

    public static void hideSoftKeyboard(Context context, View view) {
        if (context != null && view != null) {
            ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showSoftKeyboard(Activity activity, View view) {
        if (activity != null && activity.getCurrentFocus() != null) {
            ((InputMethodManager) activity.getSystemService("input_method")).showSoftInput(view, 0);
        }
    }

    public static boolean isNetworkConnectionPresent() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ApptentiveInternal.getInstance().getApplicationContext().getSystemService("connectivity");
        return (connectivityManager == null || connectivityManager.getActiveNetworkInfo() == null) ? false : true;
    }

    public static void ensureClosed(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static Point getScreenSize(Context context) {
        Point point = new Point();
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        point.set(defaultDisplay.getWidth(), defaultDisplay.getHeight());
        return point;
    }

    public static void printDebugInfo(Context context) {
        Point screenSize = getScreenSize(context);
        ApptentiveLog.d("Screen size: PX=%dx%d DP=%dx%d", Integer.valueOf(screenSize.x), Integer.valueOf(screenSize.y), Integer.valueOf(pixelsToDips(context, screenSize.x)), Integer.valueOf(pixelsToDips(context, screenSize.y)));
    }

    public static String trim(String str) {
        if (str != null) {
            return str.trim();
        }
        return null;
    }

    public static Integer parseCacheControlHeader(String str) {
        Throwable e;
        Integer num = null;
        if (str != null) {
            for (String trim : str.substring(str.indexOf("[") + 1, str.lastIndexOf("]")).split(",")) {
                String trim2 = trim2.trim();
                if (trim2.startsWith("max-age=")) {
                    String[] split = trim2.split("=");
                    if (split.length == 2) {
                        String str2;
                        try {
                            str2 = split[1];
                            try {
                                num = Integer.valueOf(Integer.parseInt(str2));
                                break;
                            } catch (NumberFormatException e2) {
                                e = e2;
                            }
                        } catch (NumberFormatException e3) {
                            e = e3;
                            str2 = num;
                            ApptentiveLog.e("Error parsing cache expiration as number: %s", e, str2);
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return num;
    }

    public static boolean isEmailValid(String str) {
        return str.matches("^[^\\s@]+@[^\\s@]+$");
    }

    public static boolean getPackageMetaDataBoolean(Context context, String str) {
        boolean z = false;
        try {
            z = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getBoolean(str, false);
        } catch (NameNotFoundException e) {
        }
        return z;
    }

    public static Object getPackageMetaData(Context context, String str) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.get(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getPackageMetaDataSingleQuotedString(Context context, String str) {
        Object packageMetaData = getPackageMetaData(context, str);
        if (packageMetaData == null) {
            return null;
        }
        String obj = packageMetaData.toString();
        if (obj.endsWith("'")) {
            obj = obj.substring(0, obj.length() - 1);
        }
        if (obj.startsWith("'")) {
            return obj.substring(1, obj.length());
        }
        return obj;
    }

    public static String stackTraceAsString(Throwable th) {
        Writer stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Throwable e) {
            ApptentiveLog.e("Error getting app version name.", e, new Object[0]);
            return null;
        }
    }

    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Throwable e) {
            ApptentiveLog.e("Error getting app version code.", e, new Object[0]);
            return -1;
        }
    }

    public static double currentTimeSeconds() {
        return ((double) System.currentTimeMillis()) / 1000.0d;
    }

    public static int getUtcOffset() {
        return TimeZone.getDefault().getOffset(System.currentTimeMillis()) / PostAdPaymentModule.REDIRECT_TO_MANAGE_ADS_TIMEOUT;
    }

    public static String getInstallerPackageName(Context context) {
        try {
            return context.getPackageManager().getInstallerPackageName(context.getPackageName());
        } catch (Exception e) {
            return null;
        }
    }

    public static String readStringFromInputStream(InputStream inputStream, String str) {
        Closeable inputStreamReader;
        Throwable th;
        Closeable closeable = null;
        StringBuilder stringBuilder = new StringBuilder();
        char[] cArr = new char[8196];
        try {
            inputStreamReader = new InputStreamReader(inputStream, str);
            while (true) {
                try {
                    int read = inputStreamReader.read(cArr, 0, 8196);
                    if (read < 0) {
                        break;
                    }
                    stringBuilder.append(cArr, 0, read);
                } catch (Exception e) {
                } catch (Throwable th2) {
                    Throwable th3 = th2;
                    closeable = inputStreamReader;
                    th = th3;
                }
            }
            ensureClosed(inputStreamReader);
        } catch (Exception e2) {
            inputStreamReader = null;
            ensureClosed(inputStreamReader);
            return stringBuilder.toString();
        } catch (Throwable th4) {
            th = th4;
            ensureClosed(closeable);
            throw th;
        }
        return stringBuilder.toString();
    }

    public static Integer getMajorOsVersion() {
        try {
            String[] split = VERSION.RELEASE.split("\\.");
            if (!(split == null || split.length == 0)) {
                return Integer.valueOf(Integer.parseInt(split[0]));
            }
        } catch (Throwable e) {
            ApptentiveLog.w("Error getting major OS version", e, new Object[0]);
        }
        return Integer.valueOf(-1);
    }

    public static Integer parseWebColorAsAndroidColor(String str) {
        Boolean valueOf = Boolean.valueOf(str.length() == 9);
        try {
            Integer valueOf2 = Integer.valueOf(Color.parseColor(str));
            if (!valueOf.booleanValue()) {
                return valueOf2;
            }
            return Integer.valueOf(((valueOf2.intValue() & 255) << 24) | (valueOf2.intValue() >>> 8));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static void calculateListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter != null) {
            int i;
            int i2 = 0;
            for (i = 0; i < adapter.getCount(); i++) {
                View view = adapter.getView(i, null, listView);
                view.measure(0, 0);
                i2 += view.getMeasuredHeight();
            }
            int dividerHeight = (listView.getDividerHeight() * (adapter.getCount() - 1)) + i2;
            i = listView.getLayoutParams().height - dividerHeight;
        }
    }

    public static void setBackground(View view, Drawable drawable) {
        if (VERSION.SDK_INT < 16) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    public static void setBackground(View view, int i) {
        setBackground(view, getCompatDrawable(view.getContext(), i));
    }

    public static Drawable getCompatDrawable(Context context, int i) {
        Drawable drawable = null;
        try {
            drawable = ContextCompat.getDrawable(context, i);
        } catch (Exception e) {
        }
        return drawable;
    }

    public static int getResourceIdFromAttribute(Theme theme, int i) {
        TypedValue typedValue = new TypedValue();
        if (theme.resolveAttribute(i, typedValue, true)) {
            return typedValue.resourceId;
        }
        return 0;
    }

    public static int getThemeColor(Context context, int i) {
        if (context == null) {
            return 0;
        }
        return getThemeColor(context.getTheme(), i);
    }

    public static int getThemeColor(Theme theme, int i) {
        TypedValue typedValue = new TypedValue();
        if (theme.resolveAttribute(i, typedValue, true)) {
            return typedValue.data;
        }
        return 0;
    }

    public static int getThemeColorFromAttrOrRes(Context context, int i, int i2) {
        int themeColor = getThemeColor(context, i);
        if (themeColor == 0) {
            return ContextCompat.getColor(context, i2);
        }
        return themeColor;
    }

    public static StateListDrawable getSelectableImageButtonBackground(int i) {
        Drawable colorDrawable = new ColorDrawable(i);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, colorDrawable);
        stateListDrawable.addState(new int[]{16843518}, colorDrawable);
        return stateListDrawable;
    }

    public static int brighter(int i, float f) {
        return Color.argb(Color.alpha(i), (int) ((((((float) Color.red(i)) * (IPhotoView.DEFAULT_MIN_SCALE - f)) / 255.0f) + f) * 255.0f), (int) ((((((float) Color.green(i)) * (IPhotoView.DEFAULT_MIN_SCALE - f)) / 255.0f) + f) * 255.0f), (int) ((((((float) Color.blue(i)) * (IPhotoView.DEFAULT_MIN_SCALE - f)) / 255.0f) + f) * 255.0f));
    }

    public static int dimmer(int i, float f) {
        return Color.argb((int) (((float) Color.alpha(i)) * f), Color.red(i), Color.green(i), Color.blue(i));
    }

    public static int alphaMixColors(int i, int i2) {
        double d = ((double) ((i >> 24) & 255)) / 255.0d;
        double d2 = ((double) ((i2 >> 24) & 255)) / 255.0d;
        double d3 = ((1.0d - d2) * d) + d2;
        d = (d * (1.0d - d2)) / d3;
        d2 = d / d3;
        return ((((int) ((d * ((double) ((float) (i & 255)))) + (d2 * ((double) ((float) (i2 & 255)))))) & 255) << 0) | ((((((int) (d3 * 255.0d)) & 255) << 24) | ((((int) ((((double) ((float) ((i >> 16) & 255))) * d) + (((double) ((float) ((i2 >> 16) & 255))) * d2))) & 255) << 16)) | ((((int) ((((double) ((float) ((i >> 8) & 255))) * d) + (((double) ((float) ((i2 >> 8) & 255))) * d2))) & 255) << 8));
    }

    public static boolean canLaunchIntent(Context context, Intent intent) {
        if (context == null || intent.resolveActivity(context.getPackageManager()) == null) {
            return false;
        }
        return true;
    }

    public static String classToString(Object obj) {
        if (obj == null) {
            return "null";
        }
        return String.format("%s(%s)", new Object[]{obj.getClass().getSimpleName(), obj});
    }

    public static String getMimeTypeFromUri(Context context, Uri uri) {
        return context != null ? context.getContentResolver().getType(uri) : null;
    }

    public static String getRealFilePathFromUri(Context context, Uri uri) {
        if (!hasPermission(context, "android.permission.READ_EXTERNAL_STORAGE")) {
            return null;
        }
        Cursor query = context.getContentResolver().query(uri, null, null, null, null);
        if (query == null || !query.moveToFirst()) {
            return null;
        }
        String string = query.getString(0);
        String substring = string.substring(string.lastIndexOf(":") + 1);
        query.close();
        query = context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, null, "_id = ? ", new String[]{substring}, null);
        if (query == null || !query.moveToFirst()) {
            return null;
        }
        String string2 = query.getString(query.getColumnIndex("_data"));
        query.close();
        return string2;
    }

    public static long getContentCreationTime(Context context, Uri uri) {
        if (!hasPermission(context, "android.permission.READ_EXTERNAL_STORAGE")) {
            return 0;
        }
        Cursor query = context.getContentResolver().query(uri, null, null, null, null);
        if (query != null && query.moveToFirst()) {
            String string = query.getString(0);
            String substring = string.substring(string.lastIndexOf(":") + 1);
            query.close();
            Cursor query2 = context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, null, "_id = ? ", new String[]{substring}, null);
            if (query2 != null && query2.moveToFirst()) {
                long j = query2.getLong(query2.getColumnIndex("date_added"));
                query2.close();
                return j;
            }
        }
        return 0;
    }

    private static String md5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(Constants.MD5);
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : digest) {
                stringBuilder.append(Integer.toHexString(b & 255));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateCacheFileFullPath(String str, File file) {
        return new File(file, md5(str)).getPath();
    }

    public static String generateCacheFileFullPath(Uri uri, File file, long j) {
        return new File(file, md5(uri.toString() + Long.toString(j))).getPath();
    }

    public static File getDiskCacheDir(Context context) {
        File file = null;
        if (("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) && hasPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            file = context.getExternalCacheDir();
        }
        if (file != null || context == null) {
            return file;
        }
        return context.getCacheDir();
    }

    public static String generateCacheFilePathFromNonceOrPrefix(Context context, String str, String str2) {
        if (str2 == null) {
            str2 = "apptentive-api-file-" + str;
        }
        return new File(getDiskCacheDir(context), str2).getPath();
    }

    public static boolean hasPermission(Context context, String str) {
        if (context != null && context.checkCallingOrSelfPermission(str) == 0) {
            return true;
        }
        return false;
    }

    public static boolean openFileAttachment(Context context, String str, String str2, String str3) {
        if (("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) && hasPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            File file = new File(str2);
            if (!file.exists()) {
                return false;
            }
            String name = file.getName();
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "apptentive-received");
            if (!file2.exists()) {
                file2.mkdir();
            }
            File file3 = new File(file2, name);
            String path = file3.getPath();
            if (!file3.exists()) {
                String[] list = file2.list();
                if (list != null) {
                    for (String file4 : list) {
                        new File(file2, file4).delete();
                    }
                }
            }
            if (copyFile(str2, path) == 0) {
                return false;
            }
            intent.setDataAndType(Uri.fromFile(file3), str3);
            try {
                context.startActivity(intent);
                return true;
            } catch (Throwable e) {
                ApptentiveLog.e("Activity not found to open attachment: ", e, new Object[0]);
                return false;
            }
        }
        Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(str));
        if (!canLaunchIntent(context, intent2)) {
            return false;
        }
        context.startActivity(intent2);
        return false;
    }

    public static int copyFile(String str, String str2) {
        Closeable fileInputStream;
        Throwable th;
        Closeable closeable = null;
        Closeable fileOutputStream;
        try {
            int i;
            Closeable closeable2;
            if (new File(str).exists()) {
                fileInputStream = new FileInputStream(str);
                try {
                    fileOutputStream = new FileOutputStream(str2);
                } catch (Exception e) {
                    ensureClosed(fileInputStream);
                    ensureClosed(closeable);
                    return 0;
                } catch (Throwable th2) {
                    fileOutputStream = null;
                    th = th2;
                    ensureClosed(fileInputStream);
                    ensureClosed(fileOutputStream);
                    throw th;
                }
                try {
                    byte[] bArr = new byte[1444];
                    i = 0;
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        i += read;
                        fileOutputStream.write(bArr, 0, read);
                    }
                    closeable2 = fileOutputStream;
                    fileOutputStream = fileInputStream;
                } catch (Exception e2) {
                    closeable = fileOutputStream;
                    ensureClosed(fileInputStream);
                    ensureClosed(closeable);
                    return 0;
                } catch (Throwable th3) {
                    th = th3;
                    ensureClosed(fileInputStream);
                    ensureClosed(fileOutputStream);
                    throw th;
                }
            }
            fileOutputStream = null;
            i = 0;
            closeable2 = null;
            ensureClosed(fileOutputStream);
            ensureClosed(closeable2);
            return i;
        } catch (Exception e3) {
            fileInputStream = null;
            ensureClosed(fileInputStream);
            ensureClosed(closeable);
            return 0;
        } catch (Throwable th22) {
            fileOutputStream = null;
            fileInputStream = null;
            th = th22;
            ensureClosed(fileInputStream);
            ensureClosed(fileOutputStream);
            throw th;
        }
    }

    public static boolean isMimeTypeImage(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.substring(0, str.indexOf(DFPProcessor.SEPARATOR)).equalsIgnoreCase("Image");
    }

    public static StoredFile createLocalStoredFile(String str, String str2, String str3) {
        Closeable fileInputStream;
        Throwable th;
        StoredFile storedFile = null;
        try {
            Context applicationContext = ApptentiveInternal.getInstance().getApplicationContext();
            if (!URLUtil.isContentUrl(str) || applicationContext == null) {
                fileInputStream = new FileInputStream(new File(str));
            } else {
                fileInputStream = applicationContext.getContentResolver().openInputStream(Uri.parse(str));
            }
            try {
                storedFile = createLocalStoredFile(fileInputStream, str, str2, str3);
                ensureClosed(fileInputStream);
            } catch (FileNotFoundException e) {
                ensureClosed(fileInputStream);
                return storedFile;
            } catch (Throwable th2) {
                th = th2;
                ensureClosed(fileInputStream);
                throw th;
            }
        } catch (FileNotFoundException e2) {
            fileInputStream = storedFile;
            ensureClosed(fileInputStream);
            return storedFile;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            fileInputStream = storedFile;
            th = th4;
            ensureClosed(fileInputStream);
            throw th;
        }
        return storedFile;
    }

    public static StoredFile createLocalStoredFile(InputStream inputStream, String str, String str2, String str3) {
        Closeable fileOutputStream;
        Closeable bufferedOutputStream;
        Closeable countingOutputStream;
        Throwable th;
        Throwable th2;
        if (inputStream == null) {
            return null;
        }
        try {
            File file = new File(str2);
            if (file.exists()) {
                file.delete();
            }
            fileOutputStream = new FileOutputStream(file);
            try {
                bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                try {
                    countingOutputStream = new CountingOutputStream(bufferedOutputStream);
                    try {
                        byte[] bArr = new byte[2048];
                        while (true) {
                            int read = inputStream.read(bArr, 0, 2048);
                            if (read != -1) {
                                countingOutputStream.write(bArr, 0, read);
                            } else {
                                ApptentiveLog.d("File saved, size = " + (countingOutputStream.getBytesWritten() / EbkImagePicker.DEFAULT_LONGEST_IMAGE_DIMENSION) + "k", new Object[0]);
                                ensureClosed(countingOutputStream);
                                ensureClosed(bufferedOutputStream);
                                ensureClosed(fileOutputStream);
                                StoredFile storedFile = new StoredFile();
                                storedFile.setSourceUriOrPath(str);
                                storedFile.setLocalFilePath(str2);
                                storedFile.setMimeType(str3);
                                return storedFile;
                            }
                        }
                    } catch (IOException e) {
                        try {
                            ApptentiveLog.e("Error creating local copy of file attachment.", new Object[0]);
                            ensureClosed(countingOutputStream);
                            ensureClosed(bufferedOutputStream);
                            ensureClosed(fileOutputStream);
                            return null;
                        } catch (Throwable th3) {
                            th = th3;
                            ensureClosed(countingOutputStream);
                            ensureClosed(bufferedOutputStream);
                            ensureClosed(fileOutputStream);
                            throw th;
                        }
                    }
                } catch (IOException e2) {
                    countingOutputStream = null;
                    ApptentiveLog.e("Error creating local copy of file attachment.", new Object[0]);
                    ensureClosed(countingOutputStream);
                    ensureClosed(bufferedOutputStream);
                    ensureClosed(fileOutputStream);
                    return null;
                } catch (Throwable th4) {
                    th2 = th4;
                    countingOutputStream = null;
                    th = th2;
                    ensureClosed(countingOutputStream);
                    ensureClosed(bufferedOutputStream);
                    ensureClosed(fileOutputStream);
                    throw th;
                }
            } catch (IOException e3) {
                bufferedOutputStream = null;
                countingOutputStream = null;
                ApptentiveLog.e("Error creating local copy of file attachment.", new Object[0]);
                ensureClosed(countingOutputStream);
                ensureClosed(bufferedOutputStream);
                ensureClosed(fileOutputStream);
                return null;
            } catch (Throwable th5) {
                countingOutputStream = null;
                th = th5;
                Object obj = null;
                ensureClosed(countingOutputStream);
                ensureClosed(bufferedOutputStream);
                ensureClosed(fileOutputStream);
                throw th;
            }
        } catch (IOException e4) {
            fileOutputStream = null;
            bufferedOutputStream = null;
            countingOutputStream = null;
            ApptentiveLog.e("Error creating local copy of file attachment.", new Object[0]);
            ensureClosed(countingOutputStream);
            ensureClosed(bufferedOutputStream);
            ensureClosed(fileOutputStream);
            return null;
        } catch (Throwable th6) {
            bufferedOutputStream = null;
            countingOutputStream = null;
            th2 = th6;
            fileOutputStream = null;
            th = th2;
            ensureClosed(countingOutputStream);
            ensureClosed(bufferedOutputStream);
            ensureClosed(fileOutputStream);
            throw th;
        }
    }

    public static Activity castContextToActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return context instanceof ContextWrapper ? castContextToActivity(((ContextWrapper) context).getBaseContext()) : null;
    }

    public static void replaceDefaultFont(Context context, String str) {
        Object obj = null;
        Typeface createFromAsset = Typeface.createFromAsset(context.getAssets(), str);
        TypedValue typedValue = new TypedValue();
        Theme newTheme = context.getResources().newTheme();
        ApptentiveInternal.getInstance().updateApptentiveInteractionTheme(newTheme, context);
        if (newTheme != null) {
            if (VERSION.SDK_INT >= 21) {
                if (newTheme.resolveAttribute(R.attr.apptentiveFontFamilyDefault, typedValue, true)) {
                    obj = new HashMap();
                    obj.put(typedValue.string.toString(), createFromAsset);
                }
                if (newTheme.resolveAttribute(R.attr.apptentiveFontFamilyMediumDefault, typedValue, true)) {
                    if (obj == null) {
                        obj = new HashMap();
                    }
                    obj.put(typedValue.string.toString(), createFromAsset);
                }
                if (obj != null) {
                    try {
                        Field declaredField = Typeface.class.getDeclaredField("sSystemFontMap");
                        declaredField.setAccessible(true);
                        declaredField.set(null, obj);
                    } catch (Throwable e) {
                        ApptentiveLog.e("Exception replacing system font", e, new Object[0]);
                    } catch (Throwable e2) {
                        ApptentiveLog.e("Exception replacing system font", e2, new Object[0]);
                    }
                }
            } else if (newTheme.resolveAttribute(R.attr.apptentiveTypefaceDefault, typedValue, true)) {
                String str2 = "DEFAULT";
                if (typedValue.data == context.getResources().getInteger(R.integer.apptentive_typeface_monospace)) {
                    str2 = "MONOSPACE";
                } else if (typedValue.data == context.getResources().getInteger(R.integer.apptentive_typeface_serif)) {
                    str2 = "SERIF";
                } else if (typedValue.data == context.getResources().getInteger(R.integer.apptentive_typeface_sans)) {
                    str2 = "SANS_SERIF";
                }
                try {
                    Field declaredField2 = Typeface.class.getDeclaredField(str2);
                    declaredField2.setAccessible(true);
                    declaredField2.set(null, createFromAsset);
                } catch (Throwable e22) {
                    ApptentiveLog.e("Exception replacing system font", e22, new Object[0]);
                } catch (Throwable e222) {
                    ApptentiveLog.e("Exception replacing system font", e222, new Object[0]);
                }
            }
        }
    }
}

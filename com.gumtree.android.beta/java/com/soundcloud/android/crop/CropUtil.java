package com.soundcloud.android.crop;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.gumtree.android.logging.Timber;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class CropUtil {
    private static final String SCHEME_CONTENT = "content";
    private static final String SCHEME_FILE = "file";

    CropUtil() {
    }

    public static void closeSilently(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
            }
        }
    }

    public static int getExifRotation(File file) {
        if (file == null) {
            return 0;
        }
        try {
            switch (new ExifInterface(file.getAbsolutePath()).getAttributeInt("Orientation", 0)) {
                case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                    return 180;
                case Timber.ERROR /*6*/:
                    return 90;
                case HighlightView.GROW_TOP_EDGE /*8*/:
                    return 270;
                default:
                    return 0;
            }
        } catch (Throwable e) {
            Log.e("Error getting Exif data", e);
            return 0;
        }
    }

    public static boolean copyExifRotation(File file, File file2) {
        if (file == null || file2 == null) {
            return false;
        }
        try {
            ExifInterface exifInterface = new ExifInterface(file.getAbsolutePath());
            ExifInterface exifInterface2 = new ExifInterface(file2.getAbsolutePath());
            exifInterface2.setAttribute("Orientation", exifInterface.getAttribute("Orientation"));
            exifInterface2.saveAttributes();
            return true;
        } catch (Throwable e) {
            Log.e("Error copying Exif data", e);
            return false;
        }
    }

    @Nullable
    public static File getFromMediaUri(Context context, ContentResolver contentResolver, Uri uri) {
        File file;
        Throwable th;
        Cursor cursor = null;
        if (uri == null) {
            return null;
        }
        if (SCHEME_FILE.equals(uri.getScheme())) {
            return new File(uri.getPath());
        }
        if (SCHEME_CONTENT.equals(uri.getScheme())) {
            Cursor query;
            try {
                query = contentResolver.query(uri, new String[]{"_data", "_display_name"}, null, null, null);
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            int columnIndex;
                            if (uri.toString().startsWith("content://com.google.android.gallery3d")) {
                                columnIndex = query.getColumnIndex("_display_name");
                            } else {
                                columnIndex = query.getColumnIndex("_data");
                            }
                            if (columnIndex != -1) {
                                Object string = query.getString(columnIndex);
                                if (!TextUtils.isEmpty(string)) {
                                    file = new File(string);
                                    if (query == null) {
                                        return file;
                                    }
                                    query.close();
                                    return file;
                                }
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        cursor = query;
                        try {
                            file = getFromMediaUriPfd(context, contentResolver, uri);
                            if (cursor != null) {
                                return file;
                            }
                            cursor.close();
                            return file;
                        } catch (Throwable th2) {
                            th = th2;
                            query = cursor;
                            if (query != null) {
                                query.close();
                            }
                            throw th;
                        }
                    } catch (SecurityException e2) {
                        if (query != null) {
                            query.close();
                        }
                        return null;
                    } catch (Throwable th3) {
                        th = th3;
                        if (query != null) {
                            query.close();
                        }
                        throw th;
                    }
                }
                if (query != null) {
                    query.close();
                }
            } catch (IllegalArgumentException e3) {
                file = getFromMediaUriPfd(context, contentResolver, uri);
                if (cursor != null) {
                    return file;
                }
                cursor.close();
                return file;
            } catch (SecurityException e4) {
                query = null;
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (Throwable th4) {
                th = th4;
                query = null;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        }
        return null;
    }

    private static String getTempFilename(Context context) throws IOException {
        return File.createTempFile("image", "tmp", context.getCacheDir()).getAbsolutePath();
    }

    @Nullable
    private static File getFromMediaUriPfd(Context context, ContentResolver contentResolver, Uri uri) {
        Closeable closeable;
        Throwable th;
        if (uri == null) {
            return null;
        }
        Closeable fileInputStream;
        Closeable fileOutputStream;
        try {
            fileInputStream = new FileInputStream(contentResolver.openFileDescriptor(uri, "r").getFileDescriptor());
            try {
                String tempFilename = getTempFilename(context);
                fileOutputStream = new FileOutputStream(tempFilename);
                try {
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read != -1) {
                            fileOutputStream.write(bArr, 0, read);
                        } else {
                            File file = new File(tempFilename);
                            closeSilently(fileInputStream);
                            closeSilently(fileOutputStream);
                            return file;
                        }
                    }
                } catch (IOException e) {
                    closeable = fileOutputStream;
                    fileOutputStream = fileInputStream;
                    closeSilently(fileOutputStream);
                    closeSilently(closeable);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    closeSilently(fileInputStream);
                    closeSilently(fileOutputStream);
                    throw th;
                }
            } catch (IOException e2) {
                closeable = null;
                fileOutputStream = fileInputStream;
                closeSilently(fileOutputStream);
                closeSilently(closeable);
                return null;
            } catch (Throwable th3) {
                fileOutputStream = null;
                th = th3;
                closeSilently(fileInputStream);
                closeSilently(fileOutputStream);
                throw th;
            }
        } catch (IOException e3) {
            closeable = null;
            fileOutputStream = null;
            closeSilently(fileOutputStream);
            closeSilently(closeable);
            return null;
        } catch (Throwable th32) {
            fileOutputStream = null;
            fileInputStream = null;
            th = th32;
            closeSilently(fileInputStream);
            closeSilently(fileOutputStream);
            throw th;
        }
    }

    public static void startBackgroundJob(MonitoredActivity monitoredActivity, String str, String str2, Runnable runnable, Handler handler) {
        new Thread(new BackgroundJob(monitoredActivity, runnable, ProgressDialog.show(monitoredActivity, str, str2, true, false), handler)).start();
    }
}

package com.gumtree.android.post_ad.gallery.utils;

import android.content.Context;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;
import java.io.File;
import java.util.Date;

public class PostAdImageStorage implements ImageStorage {
    private final Context context;

    public PostAdImageStorage(Context context) {
        this.context = context;
    }

    public File getNewTempFile() {
        File newFilename = getNewFilename(getPostAdTempDir());
        setPermissionAvoidCrashBeforeApi9(newFilename);
        return newFilename;
    }

    private void setPermissionAvoidCrashBeforeApi9(File file) {
        if (file != null) {
            try {
                File.class.getDeclaredMethod("setWritable", new Class[]{Boolean.TYPE, Boolean.TYPE}).invoke(file, new Object[]{Boolean.valueOf(true), Boolean.valueOf(false)});
                File.class.getDeclaredMethod("setReadable", new Class[]{Boolean.TYPE, Boolean.TYPE}).invoke(file, new Object[]{Boolean.valueOf(true), Boolean.valueOf(false)});
            } catch (Exception e) {
                Log.e("Problem setting permission on file");
            }
        }
    }

    private File getPostAdTempDir() {
        File postAdPath = getPostAdPath();
        if (verifyExistence(postAdPath)) {
            return postAdPath;
        }
        CrashlyticsHelper.getInstance().catchCustomLogging("Failed creating image capture temporary directory");
        return null;
    }

    private File getPostAdPath() {
        return this.context.getExternalCacheDir();
    }

    public boolean verifyExistence(File file) {
        if (file.exists() || file.mkdirs()) {
            return true;
        }
        CrashlyticsHelper.getInstance().catchCustomLogging("Could not create post ad dir");
        return false;
    }

    private File getNewFilename(File file) {
        if (file == null) {
            return null;
        }
        File file2 = new File(file, new Date().getTime() + ".jpg");
        try {
            if (file2.createNewFile()) {
                return file2;
            }
            return null;
        } catch (Throwable e) {
            CrashlyticsHelper.getInstance().catchGumtreeException(e);
            return null;
        }
    }

    public void clear() {
        File[] listFiles = getPostAdPath().listFiles();
        if (listFiles != null) {
            for (File delete : listFiles) {
                delete.delete();
            }
        }
    }
}

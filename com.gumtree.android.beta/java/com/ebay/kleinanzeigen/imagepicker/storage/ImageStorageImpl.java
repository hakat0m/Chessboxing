package com.ebay.kleinanzeigen.imagepicker.storage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.kleinanzeigen.imagepicker.EbkImagePicker;
import com.ebay.kleinanzeigen.imagepicker.platform.LOG;
import com.gumtree.android.dfp.DFPProcessor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

public class ImageStorageImpl implements ImageStorage {
    private final String FILE_NAME_PREFIX = "IMG_";
    private String directoryName;

    public ImageStorageImpl(String str) {
        this.directoryName = str;
    }

    public String storeImage(Bitmap bitmap, String str, boolean z, boolean z2) {
        return saveImage(bitmap, str, z, z2);
    }

    public String storeImage(Bitmap bitmap, boolean z) {
        return saveImage(bitmap, getNewUniqueFileName(), false, z);
    }

    private String saveImage(Bitmap bitmap, String str, boolean z, boolean z2) {
        FileOutputStream fileOutputStream;
        String absolutePath;
        Throwable e;
        File newTempModifiedFile = z ? getNewTempModifiedFile(str) : getNewTempFile(str);
        if (newTempModifiedFile == null) {
            return BuildConfig.FLAVOR;
        }
        try {
            fileOutputStream = new FileOutputStream(newTempModifiedFile);
            try {
                bitmap.compress(CompressFormat.JPEG, z2 ? 70 : 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                absolutePath = newTempModifiedFile.getAbsolutePath();
                if (fileOutputStream == null) {
                    return absolutePath;
                }
                try {
                    fileOutputStream.close();
                    return absolutePath;
                } catch (Throwable e2) {
                    LOG.error(e2);
                    return BuildConfig.FLAVOR;
                }
            } catch (FileNotFoundException e3) {
                e2 = e3;
                try {
                    LOG.error(e2);
                    absolutePath = BuildConfig.FLAVOR;
                    if (fileOutputStream != null) {
                        return absolutePath;
                    }
                    try {
                        fileOutputStream.close();
                        return absolutePath;
                    } catch (Throwable e22) {
                        LOG.error(e22);
                        return BuildConfig.FLAVOR;
                    }
                } catch (Throwable th) {
                    e22 = th;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable e222) {
                            LOG.error(e222);
                            return BuildConfig.FLAVOR;
                        }
                    }
                    throw e222;
                }
            } catch (IOException e4) {
                e222 = e4;
                LOG.error(e222);
                absolutePath = BuildConfig.FLAVOR;
                if (fileOutputStream != null) {
                    return absolutePath;
                }
                try {
                    fileOutputStream.close();
                    return absolutePath;
                } catch (Throwable e2222) {
                    LOG.error(e2222);
                    return BuildConfig.FLAVOR;
                }
            }
        } catch (FileNotFoundException e5) {
            e2222 = e5;
            fileOutputStream = null;
            LOG.error(e2222);
            absolutePath = BuildConfig.FLAVOR;
            if (fileOutputStream != null) {
                return absolutePath;
            }
            fileOutputStream.close();
            return absolutePath;
        } catch (IOException e6) {
            e2222 = e6;
            fileOutputStream = null;
            LOG.error(e2222);
            absolutePath = BuildConfig.FLAVOR;
            if (fileOutputStream != null) {
                return absolutePath;
            }
            fileOutputStream.close();
            return absolutePath;
        } catch (Throwable th2) {
            e2222 = th2;
            fileOutputStream = null;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw e2222;
        }
    }

    public String makeCopyForModification(String str) {
        String str2;
        String str3 = null;
        File file = new File(str);
        if (file == null || !file.getParent().endsWith(this.directoryName)) {
            str2 = "." + getNewUniqueFileName();
        } else {
            str2 = "." + new File(str).getName();
        }
        try {
            File newTempModifiedFile = getNewTempModifiedFile(str2);
            if (!newTempModifiedFile.exists()) {
                newTempModifiedFile.mkdirs();
            }
            InputStream fileInputStream = new FileInputStream(str);
            OutputStream fileOutputStream = new FileOutputStream(getModifiedImagesDir().getAbsolutePath() + DFPProcessor.SEPARATOR + str2);
            byte[] bArr = new byte[EbkImagePicker.DEFAULT_LONGEST_IMAGE_DIMENSION];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            str3 = getModifiedImagesDir().getAbsolutePath() + DFPProcessor.SEPARATOR + str2;
        } catch (Throwable e) {
            LOG.error(e);
        } catch (Throwable e2) {
            LOG.error(e2);
        }
        return str3;
    }

    public void deleteAllModifiedFiles() {
        deleteRecursively(getModifiedImagesDir());
    }

    public void deleteImage(String str) {
        if (!TextUtils.isEmpty(str)) {
            File file = new File(str);
            if (file != null && file.exists()) {
                file.delete();
            }
        }
    }

    private void deleteRecursively(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                String[] list = file.list();
                if (list != null) {
                    for (String file2 : list) {
                        File file3 = new File(file, file2);
                        if (file3.isDirectory()) {
                            deleteRecursively(file3);
                        } else {
                            file3.delete();
                        }
                    }
                }
            }
            file.delete();
        }
    }

    private String getNewUniqueFileName() {
        return "IMG_" + new Date().getTime() + "_" + getRandomFilenameSuffix() + ".jpg";
    }

    private String getRandomFilenameSuffix() {
        return String.valueOf(new Random().nextInt(90000) + 10000);
    }

    private File getNewTempFile(String str) {
        File newFilename = getNewFilename(getImagesDir(), str);
        setPermissions(newFilename);
        return newFilename;
    }

    private File getNewTempModifiedFile(String str) {
        File newFilename = getNewFilename(getModifiedImagesDir(), str);
        setPermissions(newFilename);
        return newFilename;
    }

    private File getImagesDir() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + DFPProcessor.SEPARATOR + this.directoryName);
        if (verifyExistence(file)) {
            return file;
        }
        LOG.error("Failed creating images directory");
        return null;
    }

    private File getModifiedImagesDir() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + DFPProcessor.SEPARATOR + this.directoryName + "/.modified");
        if (verifyExistence(file)) {
            try {
                new File(file, ".nomedia").createNewFile();
                return file;
            } catch (IOException e) {
                LOG.error("Could not create .nomedia file");
                return file;
            }
        }
        LOG.error("Failed creating modified images directory");
        return null;
    }

    @Nullable
    private File getNewFilename(File file, String str) {
        File file2 = null;
        if (file == null) {
            return file2;
        }
        File file3 = new File(file, str);
        try {
            if (file3.createNewFile()) {
                return file3;
            }
            return file3;
        } catch (Throwable e) {
            LOG.error(e);
            return file2;
        }
    }

    private void setPermissions(File file) {
        if (file != null) {
            file.setWritable(true, false);
            file.setReadable(true, false);
        }
    }

    public boolean verifyExistence(File file) {
        if (file.exists() || file.mkdirs()) {
            return true;
        }
        LOG.error("Could not create images dir");
        return false;
    }
}

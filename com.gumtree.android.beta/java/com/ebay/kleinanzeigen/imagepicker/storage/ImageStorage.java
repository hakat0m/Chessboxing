package com.ebay.kleinanzeigen.imagepicker.storage;

import android.graphics.Bitmap;

public interface ImageStorage {
    void deleteAllModifiedFiles();

    void deleteImage(String str);

    String makeCopyForModification(String str);

    String storeImage(Bitmap bitmap, String str, boolean z, boolean z2);

    String storeImage(Bitmap bitmap, boolean z);
}

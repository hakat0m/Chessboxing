package com.ebay.kleinanzeigen.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.ebay.kleinanzeigen.imagepicker.image_editing.ImageGalleryActivity;
import com.ebay.kleinanzeigen.imagepicker.image_library.Image;
import com.ebay.kleinanzeigen.imagepicker.platform.LOG;
import com.ebay.kleinanzeigen.imagepicker.storage.ImageStorageImpl;
import java.util.ArrayList;
import java.util.List;

public class EbkImagePicker {
    public static final int DEFAULT_LONGEST_IMAGE_DIMENSION = 1024;
    public static final int REQUEST_IMAGE_PICKER = 1000;
    private Intent imagePickerIntent = new Intent();
    private Class<?> targetClass = EbkImagePickerActivity.class;

    public EbkImagePicker maxNumber(int i) {
        this.imagePickerIntent.putExtra("MAX_SELECTION", i);
        return this;
    }

    public EbkImagePicker galleryEnabled(boolean z) {
        this.imagePickerIntent.putExtra("GALLERY_ENABLED", z);
        return this;
    }

    public EbkImagePicker selectedImages(@Nullable List<Image> list) {
        if (list != null) {
            this.imagePickerIntent.putExtra("EXTRA_IMAGE_LIST", new ArrayList(list));
        }
        return this;
    }

    public EbkImagePicker storageDirectoryName(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            this.imagePickerIntent.putExtra("STORAGE_DIR", "eBay Kleinanzeigen");
        } else {
            this.imagePickerIntent.putExtra("STORAGE_DIR", str);
        }
        return this;
    }

    public EbkImagePicker imageLongestDimension(int i) {
        this.imagePickerIntent.putExtra("LONGEST_DIMENSION", i);
        return this;
    }

    public EbkImagePicker forPick() {
        this.targetClass = EbkImagePickerActivity.class;
        return this;
    }

    public EbkImagePicker forEditImage(int i) {
        this.imagePickerIntent.putExtra("STARTING_POSITION", i);
        this.targetClass = ImageGalleryActivity.class;
        return this;
    }

    public void start(Activity activity) {
        if (this.targetClass.equals(ImageGalleryActivity.class) && !this.imagePickerIntent.hasExtra("EXTRA_IMAGE_LIST")) {
            this.targetClass = EbkImagePickerActivity.class;
            LOG.error("Provide a list of images in order to directly open edit mode");
        }
        activity.startActivityForResult(getIntent(activity), REQUEST_IMAGE_PICKER);
    }

    public void start(Context context, Fragment fragment) {
        if (this.targetClass.equals(ImageGalleryActivity.class) && !this.imagePickerIntent.hasExtra("EXTRA_IMAGE_LIST")) {
            this.targetClass = EbkImagePickerActivity.class;
            LOG.error("Provide a list of images in order to directly open edit mode");
        }
        fragment.startActivityForResult(getIntent(context), REQUEST_IMAGE_PICKER);
    }

    private Intent getIntent(Context context) {
        if (!this.imagePickerIntent.hasExtra("EXTRA_IMAGE_LIST")) {
            new ImageStorageImpl(this.imagePickerIntent.getStringExtra("STORAGE_DIR")).deleteAllModifiedFiles();
        }
        this.imagePickerIntent.setClass(context, this.targetClass);
        return this.imagePickerIntent;
    }
}

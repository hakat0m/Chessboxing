package com.ebay.kleinanzeigen.imagepicker.image_library;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.widget.ImageView;
import com.apptentive.android.sdk.BuildConfig;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.io.Serializable;

public class Image implements Parcelable, Serializable {
    public static final Creator<Image> CREATOR = new 1();
    public static final String NEW_IMAGE_ID = "NEW_IMAGE";
    private ImageData data;
    private String filePath;
    private boolean galleryImage;
    private String imageId;
    private boolean imageModifiedByUser;
    private String modifiedFilePath;
    private boolean selected;

    public Image() {
        this(BuildConfig.FLAVOR, false);
    }

    public Image(String str) {
        this(str, BuildConfig.FLAVOR, false);
    }

    public Image(String str, boolean z) {
        this(str, z, null);
    }

    public Image(String str, String str2, boolean z) {
        this(str, str2, z, null);
    }

    public Image(String str, boolean z, String str2) {
        this(NEW_IMAGE_ID, str, z, str2);
    }

    public Image(String str, String str2, boolean z, String str3) {
        this.data = new ImageData();
        this.imageId = str;
        this.filePath = str2;
        this.selected = z;
        this.modifiedFilePath = str3;
    }

    public String getImageId() {
        return this.imageId;
    }

    public String getFinalPath() {
        if (TextUtils.isEmpty(getModifiedFilePath())) {
            return getOriginalFilePath();
        }
        return getModifiedFilePath();
    }

    public void setOriginalFilePath(String str) {
        this.filePath = str;
    }

    public String getOriginalFilePath() {
        return this.filePath;
    }

    public void setModifiedFilePath(String str) {
        this.modifiedFilePath = str;
    }

    public String getModifiedFilePath() {
        return this.modifiedFilePath;
    }

    public void setGalleryImage(boolean z) {
        this.galleryImage = z;
    }

    public boolean isGalleryImage() {
        return this.galleryImage;
    }

    public void setSelected(boolean z) {
        this.selected = z;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public boolean isImageModifiedByUser() {
        return this.imageModifiedByUser;
    }

    public void setImageModifiedByUser(boolean z) {
        this.imageModifiedByUser = z;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Image)) {
            return false;
        }
        if (getOriginalFilePath() == null && ((Image) obj).getOriginalFilePath() == null) {
            return true;
        }
        if (getOriginalFilePath() == null && ((Image) obj).getOriginalFilePath() != null) {
            return false;
        }
        if (getOriginalFilePath() == null || ((Image) obj).getOriginalFilePath() != null) {
            return getOriginalFilePath().equals(((Image) obj).getOriginalFilePath());
        }
        return false;
    }

    public void showIn(ImageView imageView, ImageLoader imageLoader) {
        imageLoader.displayImage("file://" + getFinalPath(), imageView);
    }

    public ImageData getData() {
        return this.data;
    }

    public void setData(ImageData imageData) {
        this.data = imageData;
    }

    public void resetData() {
        this.data = new ImageData();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2;
        int i3 = 1;
        parcel.writeString(this.imageId);
        parcel.writeString(this.filePath);
        parcel.writeString(this.modifiedFilePath);
        if (this.selected) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        parcel.writeInt(i2);
        if (this.galleryImage) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        parcel.writeInt(i2);
        if (!this.imageModifiedByUser) {
            i3 = 0;
        }
        parcel.writeInt(i3);
        parcel.writeParcelable(this.data, i);
    }
}

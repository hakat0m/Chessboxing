package com.ebay.kleinanzeigen.imagepicker.image_library;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.gumtree.android.dfp.DFPProcessor;
import java.io.Serializable;

public class Folder implements Parcelable, Serializable, Comparable {
    public static final Creator<Folder> CREATOR = new 1();
    private String folderName;
    private String folderStoragePath;
    private String folderThumbnailPath;
    private int imageCount;

    public Folder(String str, String str2, String str3, int i) {
        this.folderName = str;
        this.folderStoragePath = str2;
        this.folderThumbnailPath = str3;
        this.imageCount = i;
    }

    public Folder(Parcel parcel) {
        this.folderName = parcel.readString();
        this.folderThumbnailPath = parcel.readString();
    }

    public void increaseImageCount() {
        this.imageCount++;
    }

    public boolean isRoot() {
        return this.folderStoragePath.equals(DFPProcessor.SEPARATOR);
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String str) {
        this.folderName = str;
    }

    public String getFolderThumbnailPath() {
        return this.folderThumbnailPath;
    }

    public void setFolderThumbnailPath(String str) {
        this.folderThumbnailPath = str;
    }

    public String getFolderStoragePath() {
        return this.folderStoragePath;
    }

    public void setFolderStoragePath(String str) {
        this.folderStoragePath = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.folderName);
        parcel.writeString(this.folderThumbnailPath);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Folder folder = (Folder) obj;
        if (this.folderStoragePath != null) {
            if (this.folderStoragePath.equals(folder.folderStoragePath)) {
                return true;
            }
        } else if (folder.folderStoragePath == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.folderStoragePath != null ? this.folderStoragePath.hashCode() : 0;
    }

    public int compareTo(Object obj) {
        return getFolderName().toLowerCase().compareTo(((Folder) obj).getFolderName().toLowerCase());
    }

    public int getImageCount() {
        return this.imageCount;
    }

    public void setImageCount(int i) {
        this.imageCount = i;
    }
}

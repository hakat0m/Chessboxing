package com.ebay.kleinanzeigen.imagepicker.image_library;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class ImageData implements Parcelable, Serializable {
    public static final Creator<ImageData> CREATOR = new 1();
    private int lastDegreestoRotate = -1;
    private float normalHeight;
    private float rotatedHeight;
    private boolean saving = false;

    public float getRotatedHeight() {
        return this.rotatedHeight;
    }

    public void setRotatedHeight(float f) {
        this.rotatedHeight = f;
    }

    public float getNormalHeight() {
        return this.normalHeight;
    }

    public void setNormalHeight(float f) {
        this.normalHeight = f;
    }

    public int getLastDegreestoRotate() {
        return this.lastDegreestoRotate;
    }

    public void setLastDegreestoRotate(int i) {
        this.lastDegreestoRotate = i;
    }

    public boolean isSaving() {
        return this.saving;
    }

    public void setSaving(boolean z) {
        this.saving = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.normalHeight);
        parcel.writeFloat(this.rotatedHeight);
        parcel.writeInt(this.lastDegreestoRotate);
        parcel.writeInt(this.saving ? 1 : 0);
    }
}

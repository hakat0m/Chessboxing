package com.gumtree.android.features;

import java.io.Serializable;

public class FeatureDurationOption implements Serializable {
    private static final int MULTIPLIER = 31;
    private String currency;
    private int durationDays;
    private boolean inPackage;
    private boolean isChecked;
    private String parentName;
    private String price;

    public void setCheckStatus(boolean z) {
        this.isChecked = z;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public int getDurationDays() {
        return this.durationDays;
    }

    public void setDurationDays(int i) {
        this.durationDays = i;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String str) {
        this.currency = str;
    }

    public boolean isInPackage() {
        return this.inPackage;
    }

    public void setInPackage(boolean z) {
        this.inPackage = z;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String str) {
        this.parentName = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FeatureDurationOption featureDurationOption = (FeatureDurationOption) obj;
        if (this.durationDays != featureDurationOption.durationDays) {
            return false;
        }
        if (this.inPackage != featureDurationOption.inPackage) {
            return false;
        }
        if (this.isChecked != featureDurationOption.isChecked) {
            return false;
        }
        if (!this.currency.equals(featureDurationOption.currency)) {
            return false;
        }
        if (!this.parentName.equals(featureDurationOption.parentName)) {
            return false;
        }
        if (this.price.equals(featureDurationOption.price)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i;
        int i2 = 1;
        int hashCode = ((this.price.hashCode() * MULTIPLIER) + this.durationDays) * MULTIPLIER;
        if (this.isChecked) {
            i = 1;
        } else {
            i = 0;
        }
        i = (((i + hashCode) * MULTIPLIER) + this.currency.hashCode()) * MULTIPLIER;
        if (!this.inPackage) {
            i2 = 0;
        }
        return ((i + i2) * MULTIPLIER) + this.parentName.hashCode();
    }
}

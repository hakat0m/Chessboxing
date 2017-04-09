package com.gumtree.android.postad.confirmation.models;

import java.io.Serializable;

public class SuccessPostResult implements Serializable {
    private static final long serialVersionUID = 4655518845495705778L;
    private String imageUrl;
    private String price;
    private String subTitle;
    private String title;

    protected boolean canEqual(Object obj) {
        return obj instanceof SuccessPostResult;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SuccessPostResult)) {
            return false;
        }
        SuccessPostResult successPostResult = (SuccessPostResult) obj;
        if (!successPostResult.canEqual(this)) {
            return false;
        }
        String title = getTitle();
        String title2 = successPostResult.getTitle();
        if (title != null ? !title.equals(title2) : title2 != null) {
            return false;
        }
        title = getSubTitle();
        title2 = successPostResult.getSubTitle();
        if (title != null ? !title.equals(title2) : title2 != null) {
            return false;
        }
        title = getPrice();
        title2 = successPostResult.getPrice();
        if (title != null ? !title.equals(title2) : title2 != null) {
            return false;
        }
        title = getImageUrl();
        title2 = successPostResult.getImageUrl();
        if (title == null) {
            if (title2 == null) {
                return true;
            }
        } else if (title.equals(title2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String title = getTitle();
        int hashCode = (title == null ? 43 : title.hashCode()) + 59;
        String subTitle = getSubTitle();
        hashCode = (subTitle == null ? 43 : subTitle.hashCode()) + (hashCode * 59);
        subTitle = getPrice();
        hashCode = (subTitle == null ? 43 : subTitle.hashCode()) + (hashCode * 59);
        subTitle = getImageUrl();
        hashCode *= 59;
        if (subTitle != null) {
            i = subTitle.hashCode();
        }
        return hashCode + i;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public void setSubTitle(String str) {
        this.subTitle = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String toString() {
        return "SuccessPostResult(title=" + getTitle() + ", subTitle=" + getSubTitle() + ", price=" + getPrice() + ", imageUrl=" + getImageUrl() + ")";
    }

    SuccessPostResult(String str, String str2, String str3, String str4) {
        this.title = str;
        this.subTitle = str2;
        this.price = str3;
        this.imageUrl = str4;
    }

    public static SuccessPostResultBuilder builder() {
        return new SuccessPostResultBuilder();
    }

    public String getTitle() {
        return this.title;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public String getPrice() {
        return this.price;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }
}

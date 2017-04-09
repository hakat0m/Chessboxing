package com.gumtree.android.manageads;

import java.beans.ConstructorProperties;

public class Ad {
    private int adsCount;
    private int colorId;
    private String footerActionButtonText;
    private String formattedDate;
    private String formattedLocation;
    private String formattedPrice;
    private String id;
    private boolean isHeader;
    private boolean isNewPost;
    private String listViewsNum;
    private String pictureUrl;
    private String repliesNum;
    private String status;
    private Status statusIdentifier;
    private String title;
    private String viewsNum;

    public AdBuilder toBuilder() {
        return new AdBuilder().statusIdentifier(this.statusIdentifier).id(this.id).status(this.status).footerActionButtonText(this.footerActionButtonText).formattedDate(this.formattedDate).title(this.title).formattedLocation(this.formattedLocation).formattedPrice(this.formattedPrice).viewsNum(this.viewsNum).listViewsNum(this.listViewsNum).repliesNum(this.repliesNum).pictureUrl(this.pictureUrl).colorId(this.colorId).adsCount(this.adsCount).isHeader(this.isHeader).isNewPost(this.isNewPost);
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof Ad;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Ad)) {
            return false;
        }
        Ad ad = (Ad) obj;
        if (!ad.canEqual(this)) {
            return false;
        }
        Status statusIdentifier = getStatusIdentifier();
        Status statusIdentifier2 = ad.getStatusIdentifier();
        if (statusIdentifier != null ? !statusIdentifier.equals(statusIdentifier2) : statusIdentifier2 != null) {
            return false;
        }
        String id = getId();
        String id2 = ad.getId();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getStatus();
        id2 = ad.getStatus();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getFooterActionButtonText();
        id2 = ad.getFooterActionButtonText();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getFormattedDate();
        id2 = ad.getFormattedDate();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getTitle();
        id2 = ad.getTitle();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getFormattedLocation();
        id2 = ad.getFormattedLocation();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getFormattedPrice();
        id2 = ad.getFormattedPrice();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getViewsNum();
        id2 = ad.getViewsNum();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getListViewsNum();
        id2 = ad.getListViewsNum();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getRepliesNum();
        id2 = ad.getRepliesNum();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getPictureUrl();
        id2 = ad.getPictureUrl();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        if (getColorId() != ad.getColorId()) {
            return false;
        }
        if (getAdsCount() != ad.getAdsCount()) {
            return false;
        }
        if (isHeader() != ad.isHeader()) {
            return false;
        }
        return isNewPost() == ad.isNewPost();
    }

    public int hashCode() {
        int i = 79;
        int i2 = 43;
        Status statusIdentifier = getStatusIdentifier();
        int hashCode = (statusIdentifier == null ? 43 : statusIdentifier.hashCode()) + 59;
        String id = getId();
        hashCode = (id == null ? 43 : id.hashCode()) + (hashCode * 59);
        id = getStatus();
        hashCode = (id == null ? 43 : id.hashCode()) + (hashCode * 59);
        id = getFooterActionButtonText();
        hashCode = (id == null ? 43 : id.hashCode()) + (hashCode * 59);
        id = getFormattedDate();
        hashCode = (id == null ? 43 : id.hashCode()) + (hashCode * 59);
        id = getTitle();
        hashCode = (id == null ? 43 : id.hashCode()) + (hashCode * 59);
        id = getFormattedLocation();
        hashCode = (id == null ? 43 : id.hashCode()) + (hashCode * 59);
        id = getFormattedPrice();
        hashCode = (id == null ? 43 : id.hashCode()) + (hashCode * 59);
        id = getViewsNum();
        hashCode = (id == null ? 43 : id.hashCode()) + (hashCode * 59);
        id = getListViewsNum();
        hashCode = (id == null ? 43 : id.hashCode()) + (hashCode * 59);
        id = getRepliesNum();
        hashCode = (id == null ? 43 : id.hashCode()) + (hashCode * 59);
        id = getPictureUrl();
        hashCode *= 59;
        if (id != null) {
            i2 = id.hashCode();
        }
        hashCode = ((isHeader() ? 79 : 97) + ((((((hashCode + i2) * 59) + getColorId()) * 59) + getAdsCount()) * 59)) * 59;
        if (!isNewPost()) {
            i = 97;
        }
        return hashCode + i;
    }

    public void setAdsCount(int i) {
        this.adsCount = i;
    }

    public void setColorId(int i) {
        this.colorId = i;
    }

    public void setFooterActionButtonText(String str) {
        this.footerActionButtonText = str;
    }

    public void setFormattedDate(String str) {
        this.formattedDate = str;
    }

    public void setFormattedLocation(String str) {
        this.formattedLocation = str;
    }

    public void setFormattedPrice(String str) {
        this.formattedPrice = str;
    }

    public void setHeader(boolean z) {
        this.isHeader = z;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setListViewsNum(String str) {
        this.listViewsNum = str;
    }

    public void setNewPost(boolean z) {
        this.isNewPost = z;
    }

    public void setPictureUrl(String str) {
        this.pictureUrl = str;
    }

    public void setRepliesNum(String str) {
        this.repliesNum = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setStatusIdentifier(Status status) {
        this.statusIdentifier = status;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setViewsNum(String str) {
        this.viewsNum = str;
    }

    public String toString() {
        return "Ad(statusIdentifier=" + getStatusIdentifier() + ", id=" + getId() + ", status=" + getStatus() + ", footerActionButtonText=" + getFooterActionButtonText() + ", formattedDate=" + getFormattedDate() + ", title=" + getTitle() + ", formattedLocation=" + getFormattedLocation() + ", formattedPrice=" + getFormattedPrice() + ", viewsNum=" + getViewsNum() + ", listViewsNum=" + getListViewsNum() + ", repliesNum=" + getRepliesNum() + ", pictureUrl=" + getPictureUrl() + ", colorId=" + getColorId() + ", adsCount=" + getAdsCount() + ", isHeader=" + isHeader() + ", isNewPost=" + isNewPost() + ")";
    }

    public static AdBuilder builder() {
        return new AdBuilder();
    }

    @ConstructorProperties({"statusIdentifier", "id", "status", "footerActionButtonText", "formattedDate", "title", "formattedLocation", "formattedPrice", "viewsNum", "listViewsNum", "repliesNum", "pictureUrl", "colorId", "adsCount", "isHeader", "isNewPost"})
    public Ad(Status status, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, int i, int i2, boolean z, boolean z2) {
        this.statusIdentifier = status;
        this.id = str;
        this.status = str2;
        this.footerActionButtonText = str3;
        this.formattedDate = str4;
        this.title = str5;
        this.formattedLocation = str6;
        this.formattedPrice = str7;
        this.viewsNum = str8;
        this.listViewsNum = str9;
        this.repliesNum = str10;
        this.pictureUrl = str11;
        this.colorId = i;
        this.adsCount = i2;
        this.isHeader = z;
        this.isNewPost = z2;
    }

    public Status getStatusIdentifier() {
        return this.statusIdentifier;
    }

    public String getId() {
        return this.id;
    }

    public String getStatus() {
        return this.status;
    }

    public String getFooterActionButtonText() {
        return this.footerActionButtonText;
    }

    public String getFormattedDate() {
        return this.formattedDate;
    }

    public String getTitle() {
        return this.title;
    }

    public String getFormattedLocation() {
        return this.formattedLocation;
    }

    public String getFormattedPrice() {
        return this.formattedPrice;
    }

    public String getViewsNum() {
        return this.viewsNum;
    }

    public String getListViewsNum() {
        return this.listViewsNum;
    }

    public String getRepliesNum() {
        return this.repliesNum;
    }

    public String getPictureUrl() {
        return this.pictureUrl;
    }

    public int getColorId() {
        return this.colorId;
    }

    public int getAdsCount() {
        return this.adsCount;
    }

    public boolean isHeader() {
        return this.isHeader;
    }

    public boolean isNewPost() {
        return this.isNewPost;
    }
}

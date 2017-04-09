package com.gumtree.android.ads;

public class CoreAttribute {
    private String adId;
    private String description;
    private String key;
    private String label;
    private String value;

    public CoreAttribute(String str, String str2) {
        this.adId = str;
        this.key = str2;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getAdId() {
        return this.adId;
    }

    public void setAdId(String str) {
        this.adId = str;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public boolean isDynamicAttribute() {
        return false;
    }
}

package com.gumtree.android.api.treebay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ThumbnailImageUrl {
    private String imageUrl;

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }
}

package com.gumtree.android.api.treebay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemSummary {
    private List<Image> additionalImages;
    private String description;
    private Image image;
    private String itemAffiliateWebUrl;
    private String itemId;
    private ItemLocation itemLocation;
    private Price price;
    private List<ThumbnailImageUrl> thumbnailImages;
    private String title;

    public void setAdditionalImages(List<Image> list) {
        this.additionalImages = list;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setItemAffiliateWebUrl(String str) {
        this.itemAffiliateWebUrl = str;
    }

    public void setItemId(String str) {
        this.itemId = str;
    }

    public void setItemLocation(ItemLocation itemLocation) {
        this.itemLocation = itemLocation;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setThumbnailImages(List<ThumbnailImageUrl> list) {
        this.thumbnailImages = list;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getItemId() {
        return this.itemId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Image getImage() {
        return this.image;
    }

    public List<Image> getAdditionalImages() {
        return this.additionalImages;
    }

    public Price getPrice() {
        return this.price;
    }

    public List<ThumbnailImageUrl> getThumbnailImages() {
        return this.thumbnailImages;
    }

    public ItemLocation getItemLocation() {
        return this.itemLocation;
    }

    public String getItemAffiliateWebUrl() {
        return this.itemAffiliateWebUrl;
    }
}

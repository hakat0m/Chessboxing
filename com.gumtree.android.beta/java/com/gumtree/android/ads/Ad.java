package com.gumtree.android.ads;

import com.gumtree.android.category.CategoryItem;
import java.util.ArrayList;
import java.util.List;

public class Ad {
    private String accountId;
    private AdSlot adSlot;
    private List<VIPAttribute> attributes = new ArrayList();
    private CategoryItem category;
    private String contactName;
    private String description;
    private String distance;
    private int featured;
    private int freespee;
    private String id;
    private String latitude;
    private String locationsId;
    private String locationsName;
    private String longitude;
    private String modificationDate;
    private String neighborhood;
    private String phone;
    List<Picture> pictures = new ArrayList();
    private String postingSince;
    private String price;
    private String priceCurrency;
    private String priceFrequency;
    private String replyLink;
    private String replyTemplate;
    private String replyUrl;
    private String startDate;
    private String status;
    private String title;
    private int urgent;
    private String userId;
    private String visibleOnMap;
    private String websiteLink;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public List<Picture> getPictures() {
        return this.pictures;
    }

    public void setPictures(List<Picture> list) {
        this.pictures = list;
    }

    public void addPictures(List<Picture> list) {
        this.pictures.addAll(list);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public String getNeighborhood() {
        return this.neighborhood;
    }

    public void setNeighborhood(String str) {
        this.neighborhood = str;
    }

    public CategoryItem getCategory() {
        return this.category;
    }

    public void setCategory(CategoryItem categoryItem) {
        this.category = categoryItem;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String str) {
        this.startDate = str;
    }

    public String getModificationDate() {
        return this.modificationDate;
    }

    public void setModificationDate(String str) {
        this.modificationDate = str;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public String getPriceCurrency() {
        return this.priceCurrency;
    }

    public void setPriceCurrency(String str) {
        this.priceCurrency = str;
    }

    public String getPriceFrequency() {
        return this.priceFrequency;
    }

    public void setPriceFrequency(String str) {
        this.priceFrequency = str;
    }

    public String getLocationsId() {
        return this.locationsId;
    }

    public void setLocationsId(String str) {
        this.locationsId = str;
    }

    public String getLocationsName() {
        return this.locationsName;
    }

    public void setLocationsName(String str) {
        this.locationsName = str;
    }

    public String getReplyLink() {
        return this.replyLink;
    }

    public void setReplyLink(String str) {
        this.replyLink = str;
    }

    public String getWebsiteLink() {
        return this.websiteLink;
    }

    public void setWebsiteLink(String str) {
        this.websiteLink = str;
    }

    public String getReplyUrl() {
        return this.replyUrl;
    }

    public void setReplyUrl(String str) {
        this.replyUrl = str;
    }

    public String getVisibleOnMap() {
        return this.visibleOnMap;
    }

    public void setVisibleOnMap(String str) {
        this.visibleOnMap = str;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String str) {
        this.latitude = str;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String str) {
        this.longitude = str;
    }

    public int getFeatured() {
        return this.featured;
    }

    public void setFeatured(int i) {
        this.featured = i;
    }

    public int getUrgent() {
        return this.urgent;
    }

    public void setUrgent(int i) {
        this.urgent = i;
    }

    public int getFreespee() {
        return this.freespee;
    }

    public void setFreespee(int i) {
        this.freespee = i;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(String str) {
        this.contactName = str;
    }

    public String getPostingSince() {
        return this.postingSince;
    }

    public void setPostingSince(String str) {
        this.postingSince = str;
    }

    public String getReplyTemplate() {
        return this.replyTemplate;
    }

    public void setReplyTemplate(String str) {
        this.replyTemplate = str;
    }

    public List<VIPAttribute> getAttributes() {
        return this.attributes;
    }

    public void addAttributes(List<VIPAttribute> list) {
        this.attributes.addAll(list);
    }

    public String getDistance() {
        return this.distance;
    }

    public void setDistance(String str) {
        this.distance = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setAdSlot(AdSlot adSlot) {
        this.adSlot = adSlot;
    }

    public AdSlot getAdSlot() {
        return this.adSlot;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String str) {
        this.accountId = str;
    }
}

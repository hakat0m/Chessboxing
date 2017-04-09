package com.gumtree.android.vip.model;

import android.database.Cursor;
import com.gumtree.android.ads.AdSlot;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.vip.VIPContactFragment;
import com.gumtree.android.vip.api.Serialiser;

public class Advert {
    private String accountId;
    private AdSlot adSlot;
    private String categoryId;
    private String categoryName;
    private String contactName;
    private String contactPostingSince;
    private String description;
    private int freespee;
    private String fullLocation;
    private int id;
    private String latitude;
    private String longitude;
    private String phone;
    private String price;
    private String priceFrequency;
    private String reply;
    private String replyTemplate;
    private String replyUrl;
    private String startDate;
    private String title;
    private int urgent;
    private String userId;
    private String visibleOnMap;
    private String webUrl;

    public Advert(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex("uid"));
        this.categoryId = cursor.getString(cursor.getColumnIndex(VIPContactFragment.CATEGORY_ID));
        this.categoryName = cursor.getString(cursor.getColumnIndex("category_name"));
        this.title = cursor.getString(cursor.getColumnIndex(NewPostAdCategoryActivity.EXTRA_TITLE));
        this.webUrl = cursor.getString(cursor.getColumnIndex("website_link"));
        this.visibleOnMap = cursor.getString(cursor.getColumnIndex(StatefulActivity.EXTRA_VISIBLE_MAP));
        this.latitude = cursor.getString(cursor.getColumnIndex(StatefulActivity.NAME_LATITUDE));
        this.longitude = cursor.getString(cursor.getColumnIndex(StatefulActivity.NAME_LONGITUDE));
        this.fullLocation = cursor.getString(cursor.getColumnIndex("full_location_name"));
        this.price = cursor.getString(cursor.getColumnIndex("price_amount"));
        this.priceFrequency = cursor.getString(cursor.getColumnIndex("price_frequency"));
        this.description = cursor.getString(cursor.getColumnIndex("description"));
        this.urgent = cursor.getInt(cursor.getColumnIndex("urgent"));
        this.freespee = cursor.getInt(cursor.getColumnIndex("freespee"));
        this.phone = cursor.getString(cursor.getColumnIndex("phone"));
        this.reply = cursor.getString(cursor.getColumnIndex("reply"));
        this.replyUrl = cursor.getString(cursor.getColumnIndex("reply_url"));
        this.contactName = cursor.getString(cursor.getColumnIndex("contact_name"));
        this.contactPostingSince = cursor.getString(cursor.getColumnIndex("contact_postingSince"));
        this.startDate = cursor.getString(cursor.getColumnIndex("start_date_time"));
        this.replyTemplate = cursor.getString(cursor.getColumnIndex("reply_template"));
        this.userId = cursor.getString(cursor.getColumnIndex("user_id"));
        this.adSlot = (AdSlot) new Serialiser().deserialise(cursor.getBlob(cursor.getColumnIndex("ad_slot")));
        this.accountId = cursor.getString(cursor.getColumnIndex("account_id"));
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String str) {
        this.categoryId = str;
    }

    public String getWebUrl() {
        return this.webUrl;
    }

    public void setWebUrl(String str) {
        this.webUrl = str;
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

    public String getFullLocation() {
        return this.fullLocation;
    }

    public void setFullLocation(String str) {
        this.fullLocation = str;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public String getPriceFrequency() {
        return this.priceFrequency;
    }

    public void setPriceFrequency(String str) {
        this.priceFrequency = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public int getUrgent() {
        return this.urgent;
    }

    public void setUrgent(int i) {
        this.urgent = i;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public String getReply() {
        return this.reply;
    }

    public void setReply(String str) {
        this.reply = str;
    }

    public String getReplyUrl() {
        return this.replyUrl;
    }

    public void setReplyUrl(String str) {
        this.replyUrl = str;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(String str) {
        this.contactName = str;
    }

    public String getContactPostingSince() {
        return this.contactPostingSince;
    }

    public void setContactPostingSince(String str) {
        this.contactPostingSince = str;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String str) {
        this.startDate = str;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String str) {
        this.categoryName = str;
    }

    public String getReplyTemplate() {
        return this.replyTemplate;
    }

    public void setReplyTemplate(String str) {
        this.replyTemplate = str;
    }

    public String getUserId() {
        return this.userId;
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

    public int getFreespee() {
        return this.freespee;
    }

    public void setFreespee(int i) {
        this.freespee = i;
    }
}

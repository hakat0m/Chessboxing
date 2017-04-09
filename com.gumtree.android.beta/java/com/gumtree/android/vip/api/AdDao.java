package com.gumtree.android.vip.api;

import android.content.ContentValues;
import android.net.Uri;
import com.gumtree.android.ads.Ad;
import com.gumtree.android.ads.Picture;
import com.gumtree.android.ads.VIPAttribute;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.utils.DateTimeDataProcessor;
import com.gumtree.android.common.utils.FormatterHelper;
import com.gumtree.android.dfp.DFPProcessor;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.model.Ads;
import com.gumtree.android.model.AttributesForVip;
import com.gumtree.android.model.Pictures;
import com.gumtree.android.vip.VIPContactFragment;

public class AdDao {
    public void update(Ad ad, Batch batch) {
        ContentValues contentValues = new ContentValues();
        DateTimeDataProcessor dateTimeDataProcessor = new DateTimeDataProcessor();
        contentValues.put(NewPostAdCategoryActivity.EXTRA_TITLE, ad.getTitle());
        contentValues.put("ad_status", ad.getStatus());
        contentValues.put("description", ad.getDescription());
        contentValues.put("phone", ad.getPhone());
        contentValues.put("neighborhood", ad.getNeighborhood());
        contentValues.put(VIPContactFragment.CATEGORY_ID, ad.getCategory().getId());
        contentValues.put("category_name", ad.getCategory().getName());
        contentValues.put("start_date_time", dateTimeDataProcessor.getXDaysAgo(ad.getStartDate()));
        contentValues.put("modification_date_time", ad.getModificationDate());
        contentValues.put("price_amount", FormatterHelper.getFormattedPrice(ad.getPrice()));
        contentValues.put("user_id", ad.getUserId());
        contentValues.put("price_currency", ad.getPriceCurrency());
        contentValues.put("price_frequency", ad.getPriceFrequency());
        contentValues.put("location_id", ad.getLocationsId());
        contentValues.put("full_location_name", ad.getLocationsName());
        contentValues.put("reply", ad.getReplyLink());
        contentValues.put("website_link", ad.getWebsiteLink());
        contentValues.put("reply_url", ad.getReplyUrl());
        contentValues.put(StatefulActivity.EXTRA_VISIBLE_MAP, ad.getVisibleOnMap());
        contentValues.put(StatefulActivity.NAME_LATITUDE, ad.getLatitude());
        contentValues.put("uid", ad.getId());
        contentValues.put(StatefulActivity.NAME_LONGITUDE, ad.getLongitude());
        contentValues.put("featured", Integer.valueOf(ad.getFeatured()));
        contentValues.put("urgent", Integer.valueOf(ad.getUrgent()));
        contentValues.put("freespee", Integer.valueOf(ad.getFreespee()));
        contentValues.put("contact_name", ad.getContactName());
        contentValues.put("contact_postingSince", ad.getPostingSince());
        contentValues.put("reply_template", ad.getReplyTemplate());
        contentValues.put("ad_slot", new Serialiser().serialise(ad.getAdSlot()));
        contentValues.put("account_id", ad.getAccountId());
        batch.update(Ads.URI).withSelection("uid=?", new String[]{ad.getId()}).withValues(contentValues);
        if (ad.getPictures().size() > 0) {
            for (Picture forPictures : ad.getPictures()) {
                forPictures(ad.getId(), forPictures, batch);
            }
        }
        if (ad.getAttributes().size() > 0) {
            for (VIPAttribute vIPAttribute : ad.getAttributes()) {
                forAttributes(ad.getId(), ad.getCategory().getId().longValue(), vIPAttribute, batch);
            }
        }
    }

    public void insert(String str, Ad ad, Batch batch) {
        ContentValues contentValues = new ContentValues();
        DateTimeDataProcessor dateTimeDataProcessor = new DateTimeDataProcessor();
        contentValues.put("uid", str);
        contentValues.put(NewPostAdCategoryActivity.EXTRA_TITLE, ad.getTitle());
        contentValues.put("ad_status", ad.getStatus());
        contentValues.put("description", ad.getDescription());
        contentValues.put("phone", ad.getPhone());
        contentValues.put("neighborhood", ad.getNeighborhood());
        contentValues.put(VIPContactFragment.CATEGORY_ID, ad.getCategory().getId());
        contentValues.put("category_name", ad.getCategory().getName());
        contentValues.put("start_date_time", dateTimeDataProcessor.getXDaysAgo(ad.getStartDate()));
        contentValues.put("modification_date_time", ad.getModificationDate());
        contentValues.put("price_amount", FormatterHelper.getFormattedPrice(ad.getPrice()));
        contentValues.put("user_id", ad.getUserId());
        contentValues.put("price_currency", ad.getPriceCurrency());
        contentValues.put("price_frequency", ad.getPriceFrequency());
        contentValues.put("location_id", ad.getLocationsId());
        contentValues.put("full_location_name", ad.getLocationsName());
        contentValues.put("reply", ad.getReplyLink());
        contentValues.put("website_link", ad.getWebsiteLink());
        contentValues.put("reply_url", ad.getReplyUrl());
        contentValues.put(StatefulActivity.EXTRA_VISIBLE_MAP, ad.getVisibleOnMap());
        contentValues.put(StatefulActivity.NAME_LATITUDE, ad.getLatitude());
        contentValues.put(StatefulActivity.NAME_LONGITUDE, ad.getLongitude());
        contentValues.put("featured", Integer.valueOf(ad.getFeatured()));
        contentValues.put("urgent", Integer.valueOf(ad.getUrgent()));
        contentValues.put("freespee", Integer.valueOf(ad.getFreespee()));
        contentValues.put("contact_name", ad.getContactName());
        contentValues.put("contact_postingSince", ad.getPostingSince());
        contentValues.put("reply_template", ad.getReplyTemplate());
        contentValues.put("ad_slot", new Serialiser().serialise(ad.getAdSlot()));
        contentValues.put("account_id", ad.getAccountId());
        batch.insert(Ads.URI).withValues(contentValues);
        if (ad.getPictures().size() > 0) {
            for (Picture forPictures : ad.getPictures()) {
                forPictures(str, forPictures, batch);
            }
        }
        if (ad.getAttributes().size() > 0) {
            for (VIPAttribute vIPAttribute : ad.getAttributes()) {
                forAttributes(str, ad.getCategory().getId().longValue(), vIPAttribute, batch);
            }
        }
    }

    private void forAttributes(String str, long j, VIPAttribute vIPAttribute, Batch batch) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ad_id", str);
        contentValues.put(VIPContactFragment.CATEGORY_ID, Long.valueOf(j));
        contentValues.put("uid", vIPAttribute.getName() + DFPProcessor.SEPARATOR + str);
        contentValues.put("value", vIPAttribute.getValue());
        contentValues.put("read", vIPAttribute.getRead());
        contentValues.put("localized_label", vIPAttribute.getLocalizedLabel());
        contentValues.put("value_localized", vIPAttribute.getValueLocalizedLabel());
        contentValues.put("name", vIPAttribute.getName());
        batch.insert(AttributesForVip.URI).withValues(contentValues);
    }

    private void forPictures(String str, Picture picture, Batch batch) {
        Uri parse = Uri.parse(picture.getHref());
        ContentValues contentValues = new ContentValues();
        contentValues.put("ad_id", str);
        contentValues.put("href", picture.getHref());
        contentValues.put("rel", picture.getRel());
        contentValues.put("_uid", parse.getScheme() + "://" + parse.getAuthority() + ";" + parse.getPath() + ";" + str);
        batch.insert(Pictures.URI).withValues(contentValues);
    }
}

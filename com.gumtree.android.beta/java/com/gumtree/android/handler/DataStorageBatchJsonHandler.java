package com.gumtree.android.handler;

import android.content.ContentValues;
import android.net.Uri;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.ads.Ad;
import com.gumtree.android.ads.CoreAttribute;
import com.gumtree.android.ads.DynamicAttribute;
import com.gumtree.android.ads.ManageAd;
import com.gumtree.android.ads.Picture;
import com.gumtree.android.ads.PostAd;
import com.gumtree.android.category.CategoryItem;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.utils.DateTimeDataProcessor;
import com.gumtree.android.common.utils.FormatterHelper;
import com.gumtree.android.conversations.Conversation;
import com.gumtree.android.conversations.UserMessage;
import com.gumtree.android.features.Feature;
import com.gumtree.android.features.FeatureDurationOption;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.metadata.SearchMetadata;
import com.gumtree.android.metadata.SupportedValueOptions;
import com.gumtree.android.model.Ads;
import com.gumtree.android.model.Categories;
import com.gumtree.android.model.Conversations;
import com.gumtree.android.model.Features;
import com.gumtree.android.model.ManagedAds;
import com.gumtree.android.model.Messages;
import com.gumtree.android.model.Metadata;
import com.gumtree.android.model.MetadataSupportedValuesOptions;
import com.gumtree.android.model.Pictures;
import com.gumtree.android.model.PostAds;
import com.gumtree.android.model.PostAdsImages;
import com.gumtree.android.model.ServiceCallRegistry;
import com.gumtree.android.vip.VIPContactFragment;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataStorageBatchJsonHandler implements JsonHandler {
    private static final String EQUAL_QUESTION_MARK = "=?";
    private static final int FIVE = 5;
    private static final int FOUR = 4;
    private static final String ONE = "1";
    private static final String SEPARATOR = "/";
    private static final String T = "T";
    private static final String ZERO = "0";
    private final Batch batch;

    public DataStorageBatchJsonHandler(Batch batch) {
        this.batch = batch;
    }

    public Batch getBatch() {
        return this.batch;
    }

    public void onAdFromList(Ad ad, String str) {
        DateTimeDataProcessor dateTimeDataProcessor = new DateTimeDataProcessor();
        ContentValues contentValues = new ContentValues();
        setCommonAdValues(ad, contentValues);
        contentValues.put("location_id", ad.getLocationsId());
        contentValues.put("full_location_name", ad.getLocationsName());
        contentValues.put("reply", ad.getReplyLink());
        contentValues.put("reply_url", ad.getReplyUrl());
        contentValues.put("website_link", ad.getWebsiteLink());
        contentValues.put("inserted_at", str);
        contentValues.put("sort_value", Long.valueOf(dateTimeDataProcessor.getMillisOfDay()));
        if (ad.getUrgent() == 1) {
            contentValues.put("urgent", Integer.valueOf(ad.getUrgent()));
        }
        if (ad.getFeatured() == 1) {
            contentValues.put("featured", Integer.valueOf(ad.getFeatured()));
        }
        if (ad.getFreespee() == 1) {
            contentValues.put("freespee", Integer.valueOf(ad.getFreespee()));
        }
        contentValues.put("uid", ad.getId());
        contentValues.put(StatefulActivity.NAME_DISTANCE, ad.getDistance());
        this.batch.insert(Ads.URI).withValues(contentValues);
    }

    private void setCommonAdValues(Ad ad, ContentValues contentValues) {
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
        contentValues.put("price_frequency", ad.getPriceFrequency());
        contentValues.put("user_id", ad.getUserId());
        contentValues.put("price_currency", ad.getPriceCurrency());
    }

    public void onFeature(Feature feature) {
        for (FeatureDurationOption featureDurationOption : feature.getDurationOptions()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("cat_loc_key", feature.getCategoryLocation());
            contentValues.put("long_description", feature.getLongDescription());
            contentValues.put("description", feature.getDescription());
            contentValues.put("duration", Integer.valueOf(featureDurationOption.getDurationDays()));
            contentValues.put("price", BuildConfig.FLAVOR + featureDurationOption.getPrice());
            contentValues.put("include_package", Integer.valueOf(featureDurationOption.isInPackage() ? 1 : 0));
            contentValues.put("name", feature.getName());
            this.batch.insert(Features.URI).withValues(contentValues);
        }
    }

    public void onPicture(String str, Picture picture) {
        Uri parse = Uri.parse(picture.getHref());
        ContentValues contentValues = new ContentValues();
        contentValues.put("ad_id", str);
        contentValues.put("href", picture.getHref());
        contentValues.put("rel", picture.getRel());
        contentValues.put("_uid", parse.getScheme() + "://" + parse.getAuthority() + ";" + parse.getPath() + ";" + str);
        this.batch.insert(Pictures.URI).withValues(contentValues);
    }

    public void onSearchMetadata(SearchMetadata searchMetadata, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(VIPContactFragment.CATEGORY_ID, str);
        contentValues.put("uid", str + ";" + searchMetadata.getName());
        addBasicAttributeDataToValues(searchMetadata, contentValues);
        this.batch.insert(com.gumtree.android.model.SearchMetadata.URI).withValues(contentValues);
    }

    public void onMetadata(SearchMetadata searchMetadata, String str) {
        ContentValues contentValues = new ContentValues();
        String type = searchMetadata.getType();
        if (type == null || BuildConfig.FLAVOR.equals(type)) {
            type = "NUM";
        }
        contentValues.put("uid", str + ";" + searchMetadata.getName());
        contentValues.put("name", searchMetadata.getName());
        contentValues.put("localized_label", searchMetadata.getLocalizedName());
        contentValues.put("read", searchMetadata.getRead());
        contentValues.put("write", searchMetadata.getWrite());
        if (searchMetadata.getDescription() != null && searchMetadata.getDescription().length() > 0) {
            contentValues.put("description", searchMetadata.getDescription());
        }
        contentValues.put("type", type);
        contentValues.put(VIPContactFragment.CATEGORY_ID, str);
        contentValues.put("is_attribute", searchMetadata.isDynamic() ? ZERO : ONE);
        contentValues.put("preselected_value", searchMetadata.getPreSelectedValue());
        if (searchMetadata.getSupportedValues() != null && searchMetadata.getSupportedValues().length() > 0) {
            contentValues.put("supported_values", searchMetadata.getSupportedValues());
            contentValues.put("supported_values_localized", searchMetadata.getSupportedLabels());
        }
        contentValues.put("price_sensitive", searchMetadata.isPriceSensitive() ? ONE : ZERO);
        contentValues.put("disabled", searchMetadata.isDisabled() ? ONE : ZERO);
        this.batch.insert(Metadata.URI).withValues(contentValues);
    }

    public void onMetadataSupportedValuesOptions(SupportedValueOptions supportedValueOptions, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("metadata_name", str);
        contentValues.put("supported_value", supportedValueOptions.getSupportedValue());
        contentValues.put("link_title", supportedValueOptions.getLinkTitle());
        contentValues.put("link_body_text", supportedValueOptions.getLinkBodyText());
        contentValues.put("link_body_buttons_labels", supportedValueOptions.getLinkBodyButtonsLabels());
        contentValues.put("link_body_buttons_actions", supportedValueOptions.getLinkBodyButtonsActions());
        contentValues.put("popup_body_text", supportedValueOptions.getPopupBodyText());
        contentValues.put("popup_body_buttons_labels", supportedValueOptions.getPopupBodyButtonsLabels());
        contentValues.put("popup_body_buttons_actions", supportedValueOptions.getPopupBodyButtonsActions());
        this.batch.insert(MetadataSupportedValuesOptions.URI).withValues(contentValues);
    }

    private void addBasicAttributeDataToValues(SearchMetadata searchMetadata, ContentValues contentValues) {
        contentValues.put("name", searchMetadata.getName());
        contentValues.put("localized_label", searchMetadata.getLocalizedName());
        contentValues.put("read", searchMetadata.getRead());
        contentValues.put("search_param", searchMetadata.getSearchParam());
        contentValues.put("search_response_included", searchMetadata.getSearchResponseIncluded());
        contentValues.put("search_style", searchMetadata.getSearchStyle());
        contentValues.put("type", searchMetadata.getType());
        contentValues.put("write", searchMetadata.getWrite());
        contentValues.put("is_attribute", searchMetadata.isDynamic() ? ZERO : ONE);
        if (searchMetadata.getSupportedValues() != null && searchMetadata.getSupportedValues().length() > 0) {
            contentValues.put("supported_values", searchMetadata.getSupportedValues());
            contentValues.put("supported_values_localized", searchMetadata.getSupportedLabels());
        }
    }

    public void onCategory(CategoryItem categoryItem, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", categoryItem.getId());
        if (categoryItem.getParent() != null) {
            contentValues.put("parent_id", categoryItem.getParent().getId());
            contentValues.put("parent_name", categoryItem.getParent().getName());
            contentValues.put("parent_value", categoryItem.getParent().getIdName());
        }
        contentValues.put("has_cv_posting", Integer.valueOf(categoryItem.getHasCVPosting()));
        contentValues.put("path", categoryItem.getPathIds().toString());
        contentValues.put("path_names", categoryItem.getPathLocalizedName().toString());
        contentValues.put("path_values", categoryItem.getPathIdNames().toString());
        contentValues.put("children_count", Integer.valueOf(categoryItem.getCategories().size()));
        if (str == null || str.length() == 0) {
            contentValues.put("inserted_at", Long.valueOf(System.currentTimeMillis()));
        } else {
            contentValues.put("inserted_at", str);
        }
        contentValues.put("value_raw", categoryItem.getIdName());
        contentValues.put("value", categoryItem.getName());
        contentValues.put("sort_value", Long.valueOf(System.currentTimeMillis()));
        contentValues.put("thumb", categoryItem.getPicture());
        this.batch.insert(Categories.URI).withValues(contentValues);
    }

    public void onManageAd(PostAd postAd) {
        for (CoreAttribute coreAttribute : postAd.getCoreAttributeses()) {
            ContentValues contentValues = new ContentValues();
            if (coreAttribute.getLabel() != null) {
                contentValues.put("label", coreAttribute.getLabel());
            }
            contentValues.put("ad_id", coreAttribute.getAdId());
            contentValues.put("key", coreAttribute.getKey());
            contentValues.put("value", coreAttribute.getValue());
            contentValues.put("description", coreAttribute.getDescription());
            this.batch.insert(PostAds.URI).withValues(contentValues);
        }
        for (DynamicAttribute dynamicAttribute : postAd.getDynamicAttributes()) {
            contentValues = new ContentValues(FIVE);
            contentValues.put("ad_id", dynamicAttribute.getAdId());
            contentValues.put("label", dynamicAttribute.getLabel());
            contentValues.put("value", checkForDateValue(dynamicAttribute.getValue()));
            contentValues.put("is_attribute", Integer.valueOf(1));
            contentValues.put("key", dynamicAttribute.getKey());
            contentValues.put("description", dynamicAttribute.getDescription());
            this.batch.insert(PostAds.URI).withValues(contentValues);
        }
        for (String str : postAd.getFeaturesActive()) {
            contentValues = new ContentValues();
            contentValues.put("ad_id", postAd.getId());
            contentValues.put("key", str);
            this.batch.insert(PostAds.URI).withValues(contentValues);
        }
        for (Picture picture : postAd.getPictures()) {
            contentValues = new ContentValues(FOUR);
            contentValues.put("ad_id", picture.getAdId());
            contentValues.put("href", picture.getHref());
            contentValues.put("upload_state", picture.getStatus());
            contentValues.put("rel", picture.getRel());
            contentValues.put("gallery_index", picture.getIndex());
            this.batch.insert(PostAdsImages.URI).withValues(contentValues);
        }
    }

    public void onFinish(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("url", str);
        contentValues.put("inserted_at", str2);
        this.batch.insert(ServiceCallRegistry.URI).withValues(contentValues);
    }

    public void onFinish(String str, long j, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("url", str);
        contentValues.put("inserted_at", Long.valueOf(j));
        contentValues.put("abundance", str2);
        this.batch.insert(ServiceCallRegistry.URI).withValues(contentValues);
    }

    public void onFinish(String str, long j, String str2, String str3, String str4, String str5, String str6, String str7) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("url", str);
        contentValues.put("inserted_at", Long.valueOf(j));
        contentValues.put("abundance", str3);
        contentValues.put("additional_abundance", str4);
        contentValues.put("top_ad_abundance", str5);
        contentValues.put("content_url", str2);
        contentValues.put("treebay_search_url", str6);
        contentValues.put("treebay_item_template_url", str7);
        this.batch.insert(ServiceCallRegistry.URI).withValues(contentValues);
    }

    public void onPictureUploaded(String str, String str2, String str3) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("href", str2);
        contentValues.put("rel", str3);
        this.batch.update(PostAdsImages.URI).withSelection("storage_path=?", new String[]{str}).withValues(contentValues);
    }

    public void onPictureState(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("upload_state", str2);
        this.batch.update(PostAdsImages.URI).withSelection("storage_path=?", new String[]{str}).withValues(contentValues);
    }

    private String checkForDateValue(String str) {
        if (str.contains(T)) {
            return formatDate(str);
        }
        return str;
    }

    private String formatDate(String str) {
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(str.split(T)[0]);
            Calendar instance = Calendar.getInstance();
            instance.setTime(parse);
            int i = instance.get(1);
            int i2 = instance.get(2);
            str = instance.get(FIVE) + SEPARATOR + (i2 + 1) + SEPARATOR + i;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public void onManagedAdFromList(ManageAd manageAd, String str) {
        DateTimeDataProcessor dateTimeDataProcessor = new DateTimeDataProcessor();
        ContentValues contentValues = new ContentValues();
        setCommonAdValues(manageAd.getAdvert(), contentValues);
        contentValues.put("location_id", manageAd.getAdvert().getLocationsId());
        contentValues.put("full_location_name", manageAd.getAdvert().getLocationsName());
        contentValues.put("reply", manageAd.getAdvert().getReplyLink());
        contentValues.put("website_link", manageAd.getAdvert().getWebsiteLink());
        contentValues.put("_id", manageAd.getAdvert().getId());
        contentValues.put("inserted_at", str);
        contentValues.put("ad_status", manageAd.getStatus());
        contentValues.put("sort_value", Long.valueOf(dateTimeDataProcessor.getMillisOfDay()));
        if (manageAd.getAdvert().getUrgent() == 1) {
            contentValues.put("urgent", Integer.valueOf(manageAd.getAdvert().getUrgent()));
        }
        if (manageAd.getAdvert().getFeatured() == 1) {
            contentValues.put("featured", Integer.valueOf(manageAd.getAdvert().getFeatured()));
        }
        contentValues.put("paid_feature", Integer.valueOf(manageAd.getPaidFeature()));
        contentValues.put("srp_views", manageAd.getSrpViews());
        contentValues.put("vip_views", manageAd.getVipViews());
        contentValues.put("num_replies", manageAd.getRepliesViews());
        this.batch.insert(ManagedAds.URI).withValues(contentValues);
        this.batch.delete(Pictures.URI).withSelection("ad_id=?", new String[]{manageAd.getAdvert().getId()});
    }

    public void onConversation(Conversation conversation) {
        int i;
        int i2 = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put("uid", conversation.getUid());
        contentValues.put("ad_id", conversation.getAdId());
        contentValues.put("ad_owner_id", conversation.getAdOwnerId());
        contentValues.put("ad_owner_email", conversation.getAdOwnerEmail());
        contentValues.put("ad_owner_name", conversation.getAdOwnerName());
        contentValues.put("ad_replier_id", conversation.getAdReplierId());
        contentValues.put("ad_replier_email", conversation.getAdReplierEmail());
        contentValues.put("ad_replier_name", conversation.getAdReplierName());
        contentValues.put("ad_subject", conversation.getAdSubject());
        contentValues.put("ad_first_img_url", conversation.getAdFirstImageUrl());
        contentValues.put("num_unread_msg", Integer.valueOf(conversation.getNumUnreadMsg()));
        contentValues.put("deleted_buyer", Integer.valueOf(conversation.isDeletedBuyer() ? 1 : 0));
        if (conversation.isDeletedSeller()) {
            i = 1;
        } else {
            i = 0;
        }
        contentValues.put("deleted_seller", Integer.valueOf(i));
        if (conversation.isFlaggedBuyer()) {
            i = 1;
        } else {
            i = 0;
        }
        contentValues.put("flagged_buyer", Integer.valueOf(i));
        if (conversation.isFlaggedSeller()) {
            i2 = 1;
        }
        contentValues.put("flagged_seller", Integer.valueOf(i2));
        contentValues.put("sync_status", Integer.valueOf(1));
        this.batch.insert(Conversations.URI).withValues(contentValues);
    }

    public void onUserMessageFromConversation(UserMessage userMessage) {
    }

    public void onUserMessagesFromList(UserMessage userMessage) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", userMessage.getId());
        contentValues.put("conversation_uid", userMessage.getConversationId());
        contentValues.put("sender_name", userMessage.getSenderName());
        contentValues.put("direction", userMessage.getDirection());
        contentValues.put("msg_content", userMessage.getMsgContent());
        contentValues.put("answered", Integer.valueOf(userMessage.isAnswered() ? 1 : 0));
        contentValues.put("post_time_stamp", userMessage.getPostTimeStamp());
        contentValues.put("sync_status", Integer.valueOf(1));
        this.batch.insert(Messages.URI).withValues(contentValues);
    }
}

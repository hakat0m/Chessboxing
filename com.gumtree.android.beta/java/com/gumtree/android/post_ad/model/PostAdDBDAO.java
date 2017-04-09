package com.gumtree.android.post_ad.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.features.Feature;
import com.gumtree.android.features.Feature.FeatureType;
import com.gumtree.android.features.FeatureDurationOption;
import com.gumtree.android.metadata.SupportedValueOptions;
import com.gumtree.android.model.Features;
import com.gumtree.android.model.Metadata;
import com.gumtree.android.model.MetadataSupportedValuesOptions;
import com.gumtree.android.model.Pictures;
import com.gumtree.android.model.PostAds;
import com.gumtree.android.model.PostAdsImages;
import com.gumtree.android.post_ad.feature.ActiveFeatureQueryHelper;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import com.gumtree.android.vip.VIPContactFragment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PostAdDBDAO {
    private static final String EQUAL_QUESTION_MARK = "=?";

    public void saveContent(Context context, PostAdData postAdData) {
        ContentResolver contentResolver = context.getContentResolver();
        String adId = postAdData.getAdId();
        clearPostAdTable(contentResolver, adId);
        for (PostAdAttributeItem insertOrUpdate : PostAdMemDAO.from(postAdData).getListOfAllAttributes()) {
            insertOrUpdate(contentResolver, adId, insertOrUpdate);
        }
        if (postAdData.getFeaturesResult() != null) {
            clearPostAdFeaturesTable(contentResolver, postAdData);
            for (Feature feature : postAdData.getFeaturesResult().getFeatures()) {
                for (FeatureDurationOption featureDurationOption : feature.getDurationOptions()) {
                    updateFeature(contentResolver, feature, featureDurationOption, isDurationOptionChecked(feature, featureDurationOption, postAdData.getSelectedFeatures()));
                }
            }
            if (postAdData.getFeaturesResult().getInsertion() != null && postAdData.getFeaturesResult().getFeatures().indexOf(new Feature(FeatureType.INSERTION.getValue())) < 0) {
                updateFeature(contentResolver, postAdData.getFeaturesResult().getInsertion(), (FeatureDurationOption) postAdData.getFeaturesResult().getInsertion().getDurationOptions().get(0), true);
            }
        }
    }

    private boolean isDurationOptionChecked(Feature feature, FeatureDurationOption featureDurationOption, ArrayList<Feature> arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Feature feature2 = (Feature) it.next();
            if (feature2.equals(feature)) {
                for (FeatureDurationOption featureDurationOption2 : feature2.getDurationOptions()) {
                    if (featureDurationOption2.equals(featureDurationOption)) {
                        return featureDurationOption2.isChecked();
                    }
                }
                continue;
            }
        }
        return false;
    }

    private void updateFeature(ContentResolver contentResolver, Feature feature, FeatureDurationOption featureDurationOption, boolean z) {
        int i;
        int i2 = 1;
        ContentValues contentValues = new ContentValues();
        contentValues.put("cat_loc_key", feature.getCategoryLocation());
        contentValues.put("long_description", feature.getLongDescription());
        contentValues.put("description", feature.getDescription());
        contentValues.put("duration", Integer.valueOf(featureDurationOption.getDurationDays()));
        contentValues.put("price", BuildConfig.FLAVOR + featureDurationOption.getPrice());
        String str = "include_package";
        if (featureDurationOption.isInPackage()) {
            i = 1;
        } else {
            i = 0;
        }
        contentValues.put(str, Integer.valueOf(i));
        contentValues.put("name", feature.getName());
        String str2 = "selected";
        if (!z) {
            i2 = 0;
        }
        contentValues.put(str2, Integer.valueOf(i2));
        contentResolver.insert(Features.URI, contentValues);
    }

    private void insertOrUpdate(ContentResolver contentResolver, String str, PostAdAttributeItem postAdAttributeItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("key", postAdAttributeItem.getKey());
        contentValues.put("value", postAdAttributeItem.getValue());
        contentValues.put("ad_id", str);
        contentValues.put("label", postAdAttributeItem.getLabel());
        contentValues.put("description", postAdAttributeItem.getDescription());
        if (postAdAttributeItem.isAttribute()) {
            contentValues.put("is_attribute", Integer.valueOf(1));
        }
        contentValues.put("price_sensitive", Integer.valueOf(postAdAttributeItem.isPriceSensitive() ? 1 : 0));
        if (getId(contentResolver, str, postAdAttributeItem.getKey()) == null) {
            contentResolver.insert(PostAds.URI, contentValues);
        } else {
            contentResolver.update(PostAds.URI, contentValues, "_id=?", new String[]{getId(contentResolver, str, postAdAttributeItem.getKey())});
        }
    }

    private String getId(ContentResolver contentResolver, String str, String str2) {
        String str3 = null;
        ContentResolver contentResolver2 = contentResolver;
        Cursor query = contentResolver2.query(PostAds.URI, new String[]{"_id"}, "key=? and ad_id=?", new String[]{str2, str}, null);
        if (query.moveToFirst()) {
            str3 = query.getString(query.getColumnIndex("_id"));
        }
        query.close();
        return str3;
    }

    public void cleanPictures(ContentResolver contentResolver, String str) {
        contentResolver.delete(Pictures.URI, "ad_id=?", new String[]{str});
    }

    public void resetContent(Context context, PostAdData postAdData) {
        ContentResolver contentResolver = context.getContentResolver();
        String adId = postAdData.getAdId();
        clearPostAdTable(contentResolver, adId);
        clearPostAdFeaturesTable(contentResolver, postAdData);
        clearPostAdImagesTable(contentResolver, adId);
        clearPostAdMetadataTable(contentResolver, adId);
    }

    private void clearPostAdTable(ContentResolver contentResolver, String str) {
        contentResolver.delete(PostAds.URI, "ad_id=?", new String[]{str});
    }

    private void clearPostAdFeaturesTable(ContentResolver contentResolver, PostAdData postAdData) {
        contentResolver.delete(Features.URI, null, null);
    }

    private void clearPostAdImagesTable(ContentResolver contentResolver, String str) {
        contentResolver.delete(PostAdsImages.URI, "ad_id=?", new String[]{str});
    }

    private void clearPostAdMetadataTable(ContentResolver contentResolver, String str) {
        contentResolver.delete(Metadata.URI, null, null);
    }

    public PostAdData getContent(Context context, String str) {
        ContentResolver contentResolver = context.getContentResolver();
        PostAdData newInstance = PostAdData.newInstance(str);
        for (PostAdAttributeItem addPostAttribute : getAttributes(contentResolver, str)) {
            newInstance.addPostAttribute(addPostAttribute);
        }
        setFeatures(context, newInstance, loadFeatures(contentResolver, newInstance));
        getDynamicAttribute(contentResolver, newInstance);
        getPicture(contentResolver, newInstance);
        return newInstance;
    }

    private void getPicture(ContentResolver contentResolver, PostAdData postAdData) {
        Cursor query;
        Throwable th;
        try {
            ContentResolver contentResolver2 = contentResolver;
            query = contentResolver2.query(PostAdsImages.URI, null, "ad_id=? AND gallery_index=?", new String[]{postAdData.getAdId(), DraftAd.NEW_AD_ID}, null);
            try {
                if (query.moveToNext()) {
                    postAdData.setDefaultPicture(query.getString(query.getColumnIndex("href")));
                }
                if (query != null) {
                    query.close();
                }
            } catch (Throwable th2) {
                th = th2;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    private List<PostAdAttributeItem> getAttributes(ContentResolver contentResolver, String str) {
        Throwable th;
        Cursor cursor;
        List<PostAdAttributeItem> arrayList = new ArrayList();
        try {
            Cursor query = contentResolver.query(PostAds.URI, null, "ad_id=?", new String[]{str}, null);
            while (query.moveToNext()) {
                try {
                    arrayList.add(new PostAdAttributeItem(query.getString(query.getColumnIndex("key")), query.getString(query.getColumnIndex("value")), query.getString(query.getColumnIndex("label")), query.getString(query.getColumnIndex("description")), query.getInt(query.getColumnIndex("is_attribute")), query.getInt(query.getColumnIndex("price_sensitive")) == 1));
                } catch (Throwable th2) {
                    th = th2;
                    cursor = query;
                }
            }
            if (query != null) {
                query.close();
            }
            return arrayList;
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private Collection<Feature> loadFeatures(ContentResolver contentResolver, PostAdData postAdData) {
        Cursor query;
        Throwable th;
        Collection linkedList = new LinkedList();
        try {
            query = contentResolver.query(Features.URI, null, "cat_loc_key=?", new String[]{postAdData.getCategoryId() + ";" + postAdData.getLocationId()}, null);
            while (query.moveToNext()) {
                try {
                    boolean z;
                    String string = query.getString(query.getColumnIndex("name"));
                    Collection arrayList = new ArrayList();
                    FeatureDurationOption featureDurationOption = new FeatureDurationOption();
                    featureDurationOption.setPrice(query.getString(query.getColumnIndex("price")));
                    featureDurationOption.setDurationDays(query.getInt(query.getColumnIndex("duration")));
                    featureDurationOption.setInPackage(query.getInt(query.getColumnIndex("include_package")) != 0);
                    if (query.getInt(query.getColumnIndex("selected")) == 0) {
                        z = false;
                    } else {
                        z = true;
                    }
                    featureDurationOption.setCheckStatus(z);
                    featureDurationOption.setCurrency("\u00a3");
                    featureDurationOption.setParentName(string);
                    arrayList.add(featureDurationOption);
                    Feature feature = new Feature(string);
                    feature.setCategoryLocation(query.getString(query.getColumnIndex("cat_loc_key")));
                    feature.setId(query.getString(query.getColumnIndex("_id")));
                    feature.setDescription(query.getString(query.getColumnIndex("description")));
                    feature.setLongDescription(query.getString(query.getColumnIndex("long_description")));
                    if (linkedList.contains(feature)) {
                        feature.getDurationOptions().addAll(((Feature) linkedList.get(linkedList.indexOf(feature))).getDurationOptions());
                        feature.setSelected(((Feature) linkedList.get(linkedList.indexOf(feature))).isSelected());
                    }
                    feature.getDurationOptions().addAll(arrayList);
                    if (featureDurationOption.isChecked()) {
                        feature.setSelected(featureDurationOption.isChecked());
                    }
                    if (linkedList.indexOf(feature) >= 0) {
                        linkedList.set(linkedList.indexOf(feature), feature);
                    } else {
                        linkedList.add(feature);
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            if (query != null) {
                query.close();
            }
            return linkedList;
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    private void setFeatures(Context context, PostAdData postAdData, Collection<Feature> collection) {
        FeaturesResult featuresResult = new FeaturesResult();
        filterForPurchasedFeature(context, postAdData, collection);
        featuresResult.setFeatures(filterFeatures(collection, postAdData));
        featuresResult.setInsertion(filterForInsertion(collection));
        if (featuresResult.getInsertion() != null) {
            FeatureDurationOption featureDurationOption = (FeatureDurationOption) featuresResult.getInsertion().getDurationOptions().get(0);
            boolean z = (featureDurationOption == null || Double.parseDouble(featureDurationOption.getPrice()) <= 0.0d || featureDurationOption.isInPackage()) ? false : true;
            postAdData.setIsPaidAd(z);
        } else {
            postAdData.setIsPaidAd(false);
        }
        postAdData.setFeaturesResult(featuresResult);
    }

    private void filterForPurchasedFeature(Context context, PostAdData postAdData, Collection<Feature> collection) {
        ActiveFeatureQueryHelper activeFeatureQueryHelper = new ActiveFeatureQueryHelper();
        Cursor query = context.getContentResolver().query(activeFeatureQueryHelper.getUri(), activeFeatureQueryHelper.getProjections(), activeFeatureQueryHelper.getWhereQuery(), activeFeatureQueryHelper.getWhereParams(postAdData.getAdId()), activeFeatureQueryHelper.getSortOrder());
        if (query.moveToFirst()) {
            int i = query.getInt(query.getColumnIndex("featured"));
            int i2 = query.getInt(query.getColumnIndex("urgent"));
            int i3 = query.getInt(query.getColumnIndex("paid_feature"));
            for (Feature feature : collection) {
                boolean z;
                if (feature.getName().equalsIgnoreCase(FeatureType.TOP_AD.getValue())) {
                    feature.setPurchased(i != 0);
                }
                if (feature.getName().equalsIgnoreCase(FeatureType.URGENT.getValue())) {
                    if (i2 != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    feature.setPurchased(z);
                }
                if (feature.getName().equalsIgnoreCase(FeatureType.INSERTION.getValue())) {
                    if (i3 != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    feature.setPurchased(z);
                }
            }
        }
        query.close();
    }

    private List<Feature> filterFeatures(Collection<Feature> collection, PostAdData postAdData) {
        List<Feature> linkedList = new LinkedList();
        if (postAdData.isPost()) {
            for (Feature feature : collection) {
                if (!(FeatureType.BUMP_UP.getValue().equals(feature.getName()) || FeatureType.INSERTION.getValue().equals(feature.getName()))) {
                    linkedList.add(feature);
                }
            }
        } else {
            for (Feature feature2 : collection) {
                if (!FeatureType.INSERTION.getValue().equals(feature2.getName())) {
                    linkedList.add(feature2);
                }
            }
        }
        return linkedList;
    }

    private Feature filterForInsertion(Collection<Feature> collection) {
        for (Feature feature : collection) {
            if (FeatureType.INSERTION.getValue().equals(feature.getName())) {
                return feature;
            }
        }
        return null;
    }

    private void getDynamicAttribute(ContentResolver contentResolver, PostAdData postAdData) {
        String str;
        Exception e;
        Throwable th;
        Cursor cursor;
        PostAdAttributeItem attribute = postAdData.getAttribute(VIPContactFragment.CATEGORY_ID);
        String str2 = BuildConfig.FLAVOR;
        if (attribute == null || TextUtils.isEmpty(attribute.getValue())) {
            str = PaymentConverter.DEFAULT_PAYMENT_METHOD_ID;
        } else {
            str = attribute.getValue();
        }
        try {
            Cursor query;
            Cursor query2 = contentResolver.query(Metadata.URI, null, "category_id=?", new String[]{str}, null);
            while (query2.moveToNext()) {
                str2 = query2.getString(query2.getColumnIndex("name"));
                str = query2.getString(query2.getColumnIndex("write"));
                postAdData.getWriteStatusMap().put(str2, str);
                if (!("unsupported".equalsIgnoreCase(str) || str2.equalsIgnoreCase("price"))) {
                    int i = str2.equalsIgnoreCase("price-frequency") ? 0 : 1;
                    String string = query2.getString(query2.getColumnIndex("type"));
                    String string2 = query2.getString(query2.getColumnIndex("localized_label"));
                    String string3 = query2.getString(query2.getColumnIndex("supported_values"));
                    String string4 = query2.getString(query2.getColumnIndex("supported_values_localized"));
                    String string5 = query2.getString(query2.getColumnIndex("description"));
                    int i2 = query2.getInt(query2.getColumnIndex("price_sensitive"));
                    int i3 = query2.getInt(query2.getColumnIndex("disabled"));
                    String string6 = query2.getString(query2.getColumnIndex("preselected_value"));
                    attribute = postAdData.getAttribute(str2);
                    if (attribute == null) {
                        String value = (postAdData == null || postAdData.getAttribute(str2) == null) ? BuildConfig.FLAVOR : postAdData.getAttribute(str2).getValue();
                        String label = (postAdData == null || postAdData.getAttribute(str2) == null) ? BuildConfig.FLAVOR : postAdData.getAttribute(str2).getLabel();
                        postAdData.addPostAttribute(new PostAdAttributeItem(str2, value, label, string5, i, i2 == 1));
                        attribute = postAdData.getAttribute(str2);
                    }
                    attribute.setDescription(string5);
                    ArrayList arrayList = new ArrayList();
                    try {
                        query = contentResolver.query(MetadataSupportedValuesOptions.URI, null, "metadata_name=?", new String[]{str2}, null);
                        while (query.moveToNext()) {
                            try {
                                SupportedValueOptions supportedValueOptions = new SupportedValueOptions(str2);
                                supportedValueOptions.setSupportedValue(query.getString(query.getColumnIndex("supported_value")));
                                supportedValueOptions.setLinkTitle(query.getString(query.getColumnIndex("link_title")));
                                supportedValueOptions.setLinkBodyText(query.getString(query.getColumnIndex("link_body_text")));
                                supportedValueOptions.setLinkBodyButtonsLabels(query.getString(query.getColumnIndex("link_body_buttons_labels")));
                                supportedValueOptions.setLinkBodyButtonsActions(query.getString(query.getColumnIndex("link_body_buttons_actions")));
                                supportedValueOptions.setPopupBodyText(query.getString(query.getColumnIndex("popup_body_text")));
                                supportedValueOptions.setPopupBodyButtonsLabels(query.getString(query.getColumnIndex("popup_body_buttons_labels")));
                                supportedValueOptions.setPopupBodyButtonsActions(query.getString(query.getColumnIndex("popup_body_buttons_actions")));
                                arrayList.add(supportedValueOptions);
                            } catch (Exception e2) {
                                e = e2;
                            }
                        }
                        if (query != null) {
                            query.close();
                        }
                    } catch (Exception e3) {
                        e = e3;
                        query = null;
                        try {
                            Log.e("Error when trying to save supported values", e.getMessage());
                            if (query != null) {
                                query.close();
                            }
                            attribute.setAttributesValue(string, string2, string3, string4, i3 == 1, string6, arrayList);
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        cursor = query2;
                    }
                    if (i3 == 1) {
                    }
                    attribute.setAttributesValue(string, string2, string3, string4, i3 == 1, string6, arrayList);
                }
            }
            if (query2 != null) {
                query2.close();
                return;
            }
            return;
            if (query != null) {
                query.close();
            }
            throw th;
        } catch (Throwable th4) {
            th = th4;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }
}

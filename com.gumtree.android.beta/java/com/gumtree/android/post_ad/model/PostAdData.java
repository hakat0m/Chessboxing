package com.gumtree.android.post_ad.model;

import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.features.Feature;
import com.gumtree.android.post_ad.validation.Validator;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.vip.VIPContactFragment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class PostAdData implements Serializable {
    private static final long serialVersionUID = 1;
    private String adId;
    private final HashMap<String, PostAdAttributeItem> attributeMap = new LinkedHashMap();
    private String defaultPicture;
    private FeaturesResult featuresResult;
    private boolean isPaidAd = false;
    private boolean isPost;
    private ArrayList<Feature> selectedFeatures = new ArrayList();
    private final HashMap<String, String> writeStatusMap = new HashMap();

    private PostAdData(String str) {
        this.adId = str;
    }

    public static PostAdData newInstance(String str) {
        PostAdData postAdData = new PostAdData(str);
        if (DraftAd.NEW_AD_ID.equals(str)) {
            postAdData.isPost = true;
        } else {
            postAdData.isPost = false;
        }
        return postAdData;
    }

    public boolean isPost() {
        return this.isPost;
    }

    public boolean isPaidAd() {
        return this.isPaidAd && this.isPost;
    }

    public void setIsPaidAd(boolean z) {
        this.isPaidAd = z;
    }

    public FeaturesResult getFeaturesResult() {
        return this.featuresResult;
    }

    public void setFeaturesResult(FeaturesResult featuresResult) {
        this.featuresResult = featuresResult;
    }

    public ArrayList<Feature> getSelectedFeatures() {
        return this.selectedFeatures;
    }

    public void setSelectedFeatures(ArrayList<Feature> arrayList) {
        this.selectedFeatures.clear();
        this.selectedFeatures = arrayList;
    }

    public boolean setAdId(String str) {
        if (!this.adId.equals(DraftAd.NEW_AD_ID)) {
            return false;
        }
        this.adId = str;
        return true;
    }

    public String getAdId() {
        return this.adId;
    }

    public void addPostAttribute(PostAdAttributeItem postAdAttributeItem) {
        if (this.attributeMap.containsKey(postAdAttributeItem.getKey())) {
            this.attributeMap.remove(postAdAttributeItem.getKey());
        }
        this.attributeMap.put(postAdAttributeItem.getKey(), postAdAttributeItem);
    }

    public PostAdAttributeItem getAttribute(String str) {
        if (this.attributeMap.containsKey(str)) {
            return (PostAdAttributeItem) this.attributeMap.get(str);
        }
        return null;
    }

    public HashMap<String, String> getPriceSensitiveAttributes() {
        HashMap<String, String> hashMap = new HashMap();
        for (Entry value : this.attributeMap.entrySet()) {
            PostAdAttributeItem postAdAttributeItem = (PostAdAttributeItem) value.getValue();
            if (postAdAttributeItem.isPriceSensitive()) {
                hashMap.put(postAdAttributeItem.getKey(), postAdAttributeItem.getValue());
            }
        }
        return hashMap;
    }

    public String getWriteStatusOfAttribute(String str) {
        return (String) this.writeStatusMap.get(str);
    }

    public HashMap<String, PostAdAttributeItem> getAttributeMap() {
        return this.attributeMap;
    }

    public HashMap<String, String> getWriteStatusMap() {
        return this.writeStatusMap;
    }

    public String isValueValid(String str) {
        try {
            return new Validator().isValueValid(str, this.attributeMap, this.writeStatusMap);
        } catch (Exception e) {
            return BuildConfig.FLAVOR;
        }
    }

    public boolean isAdValid() {
        return new Validator().isAdValid(this.attributeMap, this.writeStatusMap);
    }

    public String getLocationId() {
        if (this.attributeMap.containsKey("location")) {
            return ((PostAdAttributeItem) this.attributeMap.get("location")).getValue();
        }
        return BuildConfig.FLAVOR;
    }

    public String getCategoryId() {
        if (this.attributeMap.containsKey(VIPContactFragment.CATEGORY_ID)) {
            return ((PostAdAttributeItem) this.attributeMap.get(VIPContactFragment.CATEGORY_ID)).getValue();
        }
        return BuildConfig.FLAVOR;
    }

    public String getTitle() {
        if (this.attributeMap.containsKey(NewPostAdCategoryActivity.EXTRA_TITLE)) {
            return ((PostAdAttributeItem) this.attributeMap.get(NewPostAdCategoryActivity.EXTRA_TITLE)).getValue();
        }
        return BuildConfig.FLAVOR;
    }

    public PostAdData clone() {
        PostAdData postAdData = new PostAdData(this.adId);
        postAdData.isPost = this.isPost;
        for (String attribute : this.attributeMap.keySet()) {
            postAdData.addPostAttribute(getAttribute(attribute));
        }
        return postAdData;
    }

    public void removeAttribute(String str) {
        if (this.attributeMap.containsKey(str)) {
            this.attributeMap.remove(str);
        }
    }

    public void removeAttributes() {
        Iterator it = this.attributeMap.entrySet().iterator();
        while (it.hasNext()) {
            if (((PostAdAttributeItem) ((Entry) it.next()).getValue()).isAttribute()) {
                it.remove();
            }
        }
    }

    public String getDefaultPicture() {
        return this.defaultPicture;
    }

    public void setDefaultPicture(String str) {
        this.defaultPicture = str;
    }
}

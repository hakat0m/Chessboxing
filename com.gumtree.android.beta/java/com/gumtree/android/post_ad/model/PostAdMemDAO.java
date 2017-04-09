package com.gumtree.android.post_ad.model;

import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.vip.VIPContactFragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostAdMemDAO {
    private PostAdData mPostAdData;

    private PostAdMemDAO(PostAdData postAdData) {
        this.mPostAdData = postAdData;
    }

    public static PostAdMemDAO from(PostAdData postAdData) {
        if (postAdData == null) {
            postAdData = PostAdData.newInstance(DraftAd.NEW_AD_ID);
        }
        return new PostAdMemDAO(postAdData);
    }

    public PostAdData getPostAdData() {
        return this.mPostAdData;
    }

    public void resetAttributeData() {
        this.mPostAdData.removeAttributes();
    }

    public String getWriteStatusOfAttribute(String str) {
        if (this.mPostAdData == null || TextUtils.isEmpty(this.mPostAdData.getWriteStatusOfAttribute(str)) || TextUtils.isEmpty(getValueFromPostAdData(VIPContactFragment.CATEGORY_ID))) {
            return "optional";
        }
        return this.mPostAdData.getWriteStatusOfAttribute(str);
    }

    public String getValueFromPostAdData(String str) {
        PostAdData postAdData = this.mPostAdData;
        if (postAdData == null || postAdData.getAttribute(str) == null) {
            return BuildConfig.FLAVOR;
        }
        return postAdData.getAttribute(str).getValue();
    }

    public String getLabelPostAdData(String str) {
        PostAdData postAdData = this.mPostAdData;
        if (postAdData == null || postAdData.getAttribute(str) == null) {
            return BuildConfig.FLAVOR;
        }
        return postAdData.getAttribute(str).getLabel();
    }

    public String verifyAttribute(String str) {
        return this.mPostAdData.isValueValid(str);
    }

    public boolean isAdValid() {
        return this.mPostAdData.isAdValid();
    }

    public List<PostAdAttributeItem> getListOfAttributes() {
        HashMap attributeMap = this.mPostAdData.getAttributeMap();
        List arrayList = new ArrayList();
        for (String str : attributeMap.keySet()) {
            if (((PostAdAttributeItem) attributeMap.get(str)).isAttribute()) {
                arrayList.add(attributeMap.get(str));
            }
        }
        return arrayList;
    }

    public PostAdAttributeItem getPostAdAttributeByKey(String str) {
        return this.mPostAdData.getAttribute(str);
    }

    public PostAdAttributeItem[] getListOfAllAttributes() {
        HashMap attributeMap = this.mPostAdData.getAttributeMap();
        PostAdAttributeItem[] postAdAttributeItemArr = new PostAdAttributeItem[attributeMap.size()];
        int i = 0;
        for (String str : attributeMap.keySet()) {
            postAdAttributeItemArr[i] = (PostAdAttributeItem) attributeMap.get(str);
            i++;
        }
        return postAdAttributeItemArr;
    }

    public boolean isManageAd() {
        return !this.mPostAdData.isPost();
    }

    public String getAdId() {
        return this.mPostAdData.getAdId();
    }

    public void addAttributeToData(String str, String str2, String str3, String str4, Object obj, boolean z) {
        int i;
        PostAdAttributeItem postAdAttributeItem;
        CloneNotSupportedException e;
        if (obj == null || !(obj instanceof Integer)) {
            i = 0;
        } else {
            i = ((Integer) obj).intValue();
        }
        PostAdAttributeItem attribute = this.mPostAdData.getAttribute(str);
        if (attribute == null) {
            postAdAttributeItem = new PostAdAttributeItem(str, str2, str3, str4, i, z);
        } else {
            try {
                PostAdAttributeItem clone = attribute.clone();
                postAdAttributeItem = new PostAdAttributeItem(str, str2, str3, clone.getDescription(), attribute.isAttribute() ? 1 : 0, z);
                try {
                    postAdAttributeItem.setAttributesValue(clone.getType(), clone.getLocalizedLabel(), clone.getSupportedValues(), clone.getSupportedLocalizedValues(), clone.isDisabled(), clone.getPreSelectedValue(), clone.getSupportedValueOptionsList());
                } catch (CloneNotSupportedException e2) {
                    e = e2;
                    e.printStackTrace();
                    this.mPostAdData.addPostAttribute(postAdAttributeItem);
                }
            } catch (CloneNotSupportedException e3) {
                e = e3;
                postAdAttributeItem = attribute;
                e.printStackTrace();
                this.mPostAdData.addPostAttribute(postAdAttributeItem);
            }
        }
        this.mPostAdData.addPostAttribute(postAdAttributeItem);
    }
}

package com.gumtree.android.post_ad.model;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.gumtree.android.ads.parser.PostAdXmlBuilder.PostAdData;
import com.gumtree.android.model.PostAdsImages;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostAdDataAdapter implements PostAdData {
    private String accountId;
    private Context context;
    private PostAdData postAdData;
    private String username;

    public PostAdDataAdapter(Context context, PostAdData postAdData, String str, String str2) {
        this.postAdData = postAdData;
        this.username = str;
        this.accountId = str2;
        this.context = context;
    }

    public Map<String, String> retrieveAttributes() {
        Map<String, String> hashMap = new HashMap();
        for (PostAdAttributeItem postAdAttributeItem : getListOfAllAttributes()) {
            if (!postAdAttributeItem.isAttribute()) {
                hashMap.put(postAdAttributeItem.getKey(), postAdAttributeItem.getValue());
            }
        }
        hashMap.put("email", this.username);
        hashMap.put("account-id", this.accountId);
        return hashMap;
    }

    public Map<String, String> retrieveDynamicAttributes() {
        Map<String, String> hashMap = new HashMap();
        for (PostAdAttributeItem postAdAttributeItem : getListOfAllAttributes()) {
            if (postAdAttributeItem.isAttribute()) {
                hashMap.put(postAdAttributeItem.getKey(), postAdAttributeItem.getValue());
            }
        }
        return hashMap;
    }

    public PostAdAttributeItem[] getListOfAllAttributes() {
        HashMap attributeMap = this.postAdData.getAttributeMap();
        PostAdAttributeItem[] postAdAttributeItemArr = new PostAdAttributeItem[attributeMap.size()];
        int i = 0;
        for (String str : attributeMap.keySet()) {
            postAdAttributeItemArr[i] = (PostAdAttributeItem) attributeMap.get(str);
            i++;
        }
        return postAdAttributeItemArr;
    }

    public List<String> retrievePictures() {
        String adId = this.postAdData.getAdId();
        Cursor query = this.context.getContentResolver().query(PostAdsImages.URI, new String[]{"href"}, "ad_id=?", new String[]{adId}, null);
        List<String> arrayList = new ArrayList();
        while (query.moveToNext()) {
            CharSequence string = query.getString(0);
            if (!TextUtils.isEmpty(string)) {
                arrayList.add(string);
            }
        }
        return arrayList;
    }
}

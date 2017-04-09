package com.gumtree.android.post_ad.feature;

import android.net.Uri;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.common.providers.QueryHelper;
import com.gumtree.android.model.ManagedAdsView;
import com.gumtree.android.postad.DraftAd;

public class ActiveFeatureQueryHelper implements QueryHelper {
    public Uri getUri() {
        return ManagedAdsView.URI;
    }

    public String[] getProjections() {
        return new String[]{"_id", "inserted_at", "full_location_name", NewPostAdCategoryActivity.EXTRA_TITLE, "modification_date_time", "price_amount", "price_currency", "image_url", "ad_status", "featured", "urgent", "paid_feature"};
    }

    public String getWhereQuery() {
        return "is_deleted =? AND _id=? ";
    }

    public String[] getWhereParams(String... strArr) {
        return new String[]{DraftAd.NEW_AD_ID, strArr[0]};
    }

    public String getSortOrder() {
        return null;
    }
}

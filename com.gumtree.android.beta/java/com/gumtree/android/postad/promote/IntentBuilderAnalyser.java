package com.gumtree.android.postad.promote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.gumtree.Log;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.DraftAdAttribute;
import com.gumtree.android.postad.DraftFeature.Type;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.ListUtils;

class IntentBuilderAnalyser {
    IntentBuilderAnalyser() {
    }

    public static Intent createIntent(Context context, DraftAd draftAd) {
        ArrayList activeFeaturesToOrdinal = activeFeaturesToOrdinal(ListUtils.emptyIfNull(draftAd.getActiveFeatures()));
        Intent intent = new Intent(context, PromotionActivity.class);
        if (!DraftAd.NEW_AD_ID.equals(draftAd.getId())) {
            intent.putExtra(Keys.AD, draftAd.getId());
        }
        String str = null;
        List<DraftAdAttribute> attributesValues = draftAd.getAttributesValues();
        if (attributesValues != null) {
            for (DraftAdAttribute draftAdAttribute : attributesValues) {
                String value;
                if (Keys.SELLER_TYPE.equals(draftAdAttribute.getName())) {
                    value = draftAdAttribute.getValue();
                } else {
                    value = str;
                }
                str = value;
            }
        }
        return intent.putExtra(Keys.CATEGORY, draftAd.getCategory().getId()).putExtra(Keys.LOCATION, draftAd.getLocation().getId()).putIntegerArrayListExtra(Keys.ACTIVE_FEATURES, activeFeaturesToOrdinal).putExtra(Keys.SELECTIONS, bundleList(draftAd.getSelectedFeatures())).putExtra(Keys.IMAGE_COUNT, ListUtils.emptyIfNull(draftAd.getPostAdImages()).size()).putExtra(Keys.SELLER_TYPE, str);
    }

    public String extractAdId(Intent intent) {
        return intent.getStringExtra(Keys.AD);
    }

    public String extractCategoryId(Intent intent) {
        return intent.getStringExtra(Keys.CATEGORY);
    }

    public long extractLocationId(Intent intent) {
        return intent.getLongExtra(Keys.LOCATION, 0);
    }

    @NonNull
    public List<Type> extractActiveFeatures(Intent intent) {
        return ordinalToActiveFeatures(intent.getIntegerArrayListExtra(Keys.ACTIVE_FEATURES));
    }

    @NonNull
    public List<PromotionFeature> extractSelectedFeatures(Intent intent) {
        return unBundleList(intent.getBundleExtra(Keys.SELECTIONS));
    }

    public int extractImageCount(Intent intent) {
        return intent.getIntExtra(Keys.IMAGE_COUNT, 0);
    }

    public String extractSellerType(Intent intent) {
        return intent.getStringExtra(Keys.SELLER_TYPE);
    }

    @NonNull
    private static ArrayList<Integer> activeFeaturesToOrdinal(@NonNull List<Type> list) {
        ArrayList<Integer> arrayList = new ArrayList(list.size());
        for (Type ordinal : list) {
            arrayList.add(Integer.valueOf(ordinal.ordinal()));
        }
        return arrayList;
    }

    @NonNull
    private static List<Type> ordinalToActiveFeatures(@NonNull List<Integer> list) {
        List<Type> arrayList = new ArrayList(list.size());
        for (Integer intValue : list) {
            arrayList.add(Type.values()[intValue.intValue()]);
        }
        return arrayList;
    }

    @NonNull
    public static List<PromotionFeature> analyse(@NonNull Intent intent) {
        return unBundleList(intent.getExtras());
    }

    @NonNull
    private static List<PromotionFeature> unBundleList(Bundle bundle) {
        int i = bundle.getInt(Keys.COUNT, 0);
        List arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            Bundle bundle2 = bundle.getBundle(Keys.getSelectionKey(i2));
            if (bundle2 == null) {
                Log.d(IntentBuilderAnalyser.class.getSimpleName(), "Inconsistent argument: bundle " + i2 + " containing a promotionFeature is missing");
            } else {
                arrayList.add((PromotionFeature) bundle2.getSerializable(Keys.PROMOTION_FEATURE));
            }
        }
        return arrayList;
    }

    public Intent createResult(@NonNull List<PromotionFeature> list) {
        return new Intent().putExtras(bundleList(list));
    }

    @NonNull
    private static Bundle bundleList(@NonNull List<PromotionFeature> list) {
        Bundle bundle = new Bundle();
        for (int i = 0; i < list.size(); i++) {
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable(Keys.PROMOTION_FEATURE, (Serializable) list.get(i));
            bundle.putBundle(Keys.getSelectionKey(i), bundle2);
        }
        bundle.putInt(Keys.COUNT, list.size());
        return bundle;
    }
}

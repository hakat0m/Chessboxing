package com.gumtree.android.postad.payment.models;

import com.gumtree.android.postad.promote.PromotionFeature;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.List;

public class OrderData implements Serializable {
    private String adId;
    private List<PromotionFeature> promotionFeatures;

    @ConstructorProperties({"adId", "promotionFeatures"})
    public OrderData(String str, List<PromotionFeature> list) {
        this.adId = str;
        this.promotionFeatures = list;
    }

    public static OrderDataBuilder builder() {
        return new OrderDataBuilder();
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof OrderData;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OrderData)) {
            return false;
        }
        OrderData orderData = (OrderData) obj;
        if (!orderData.canEqual(this)) {
            return false;
        }
        String adId = getAdId();
        String adId2 = orderData.getAdId();
        if (adId != null ? !adId.equals(adId2) : adId2 != null) {
            return false;
        }
        List promotionFeatures = getPromotionFeatures();
        List promotionFeatures2 = orderData.getPromotionFeatures();
        if (promotionFeatures == null) {
            if (promotionFeatures2 == null) {
                return true;
            }
        } else if (promotionFeatures.equals(promotionFeatures2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String adId = getAdId();
        int hashCode = (adId == null ? 43 : adId.hashCode()) + 59;
        List promotionFeatures = getPromotionFeatures();
        hashCode *= 59;
        if (promotionFeatures != null) {
            i = promotionFeatures.hashCode();
        }
        return hashCode + i;
    }

    public void setAdId(String str) {
        this.adId = str;
    }

    public void setPromotionFeatures(List<PromotionFeature> list) {
        this.promotionFeatures = list;
    }

    public String toString() {
        return "OrderData(adId=" + getAdId() + ", promotionFeatures=" + getPromotionFeatures() + ")";
    }

    public String getAdId() {
        return this.adId;
    }

    public List<PromotionFeature> getPromotionFeatures() {
        return this.promotionFeatures;
    }
}

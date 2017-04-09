package com.gumtree.android.postad.promote;

import android.support.annotation.Nullable;
import com.gumtree.android.postad.DraftFeature;
import com.gumtree.android.postad.DraftOption;
import java.beans.ConstructorProperties;
import java.io.Serializable;

public final class PromotionFeature implements Serializable {
    private final boolean active;
    private final String disableReason;
    private final boolean enabled;
    private final DraftFeature feature;
    @Nullable
    private final DraftOption highlighted;
    private final boolean selected;

    public PromotionFeatureBuilder toBuilder() {
        return new PromotionFeatureBuilder().feature(this.feature).enabled(this.enabled).disableReason(this.disableReason).active(this.active).selected(this.selected).highlighted(this.highlighted);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PromotionFeature)) {
            return false;
        }
        PromotionFeature promotionFeature = (PromotionFeature) obj;
        DraftFeature feature = getFeature();
        DraftFeature feature2 = promotionFeature.getFeature();
        if (feature != null ? !feature.equals(feature2) : feature2 != null) {
            return false;
        }
        if (isEnabled() != promotionFeature.isEnabled()) {
            return false;
        }
        String disableReason = getDisableReason();
        String disableReason2 = promotionFeature.getDisableReason();
        if (disableReason != null ? !disableReason.equals(disableReason2) : disableReason2 != null) {
            return false;
        }
        if (isActive() != promotionFeature.isActive()) {
            return false;
        }
        if (isSelected() != promotionFeature.isSelected()) {
            return false;
        }
        DraftOption highlighted = getHighlighted();
        DraftOption highlighted2 = promotionFeature.getHighlighted();
        if (highlighted == null) {
            if (highlighted2 == null) {
                return true;
            }
        } else if (highlighted.equals(highlighted2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 79;
        int i2 = 43;
        DraftFeature feature = getFeature();
        int hashCode = (isEnabled() ? 79 : 97) + (((feature == null ? 43 : feature.hashCode()) + 59) * 59);
        String disableReason = getDisableReason();
        hashCode = ((isActive() ? 79 : 97) + (((disableReason == null ? 43 : disableReason.hashCode()) + (hashCode * 59)) * 59)) * 59;
        if (!isSelected()) {
            i = 97;
        }
        hashCode += i;
        DraftOption highlighted = getHighlighted();
        hashCode *= 59;
        if (highlighted != null) {
            i2 = highlighted.hashCode();
        }
        return hashCode + i2;
    }

    public String toString() {
        return "PromotionFeature(feature=" + getFeature() + ", enabled=" + isEnabled() + ", disableReason=" + getDisableReason() + ", active=" + isActive() + ", selected=" + isSelected() + ", highlighted=" + getHighlighted() + ")";
    }

    public static PromotionFeatureBuilder builder() {
        return new PromotionFeatureBuilder();
    }

    @ConstructorProperties({"feature", "enabled", "disableReason", "active", "selected", "highlighted"})
    public PromotionFeature(DraftFeature draftFeature, boolean z, String str, boolean z2, boolean z3, @Nullable DraftOption draftOption) {
        this.feature = draftFeature;
        this.enabled = z;
        this.disableReason = str;
        this.active = z2;
        this.selected = z3;
        this.highlighted = draftOption;
    }

    public DraftFeature getFeature() {
        return this.feature;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getDisableReason() {
        return this.disableReason;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isSelected() {
        return this.selected;
    }

    @Nullable
    public DraftOption getHighlighted() {
        return this.highlighted;
    }
}

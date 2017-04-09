package com.gumtree.android.metadata.model;

import android.support.annotation.NonNull;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.List;

public class DraftAdMetadata implements Serializable {
    private List<DraftAdMetadataAttribute> attributes;
    private String categoryId;
    private boolean isPriceFrequencySupported;
    private boolean isPriceSupported;
    private DraftAdMetadataAttribute priceFrequencyAttribute;

    public static DraftAdMetadataBuilder builder() {
        return new DraftAdMetadataBuilder();
    }

    @ConstructorProperties({"categoryId", "isPriceSupported", "isPriceFrequencySupported", "priceFrequencyAttribute", "attributes"})
    public DraftAdMetadata(String str, boolean z, boolean z2, DraftAdMetadataAttribute draftAdMetadataAttribute, List<DraftAdMetadataAttribute> list) {
        this.categoryId = str;
        this.isPriceSupported = z;
        this.isPriceFrequencySupported = z2;
        this.priceFrequencyAttribute = draftAdMetadataAttribute;
        this.attributes = list;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String str) {
        this.categoryId = str;
    }

    public boolean isPriceSupported() {
        return this.isPriceSupported;
    }

    public void setPriceSupported(boolean z) {
        this.isPriceSupported = z;
    }

    public boolean isPriceFrequencySupported() {
        return this.isPriceFrequencySupported;
    }

    public void setPriceFrequencySupported(boolean z) {
        this.isPriceFrequencySupported = z;
    }

    public DraftAdMetadataAttribute getPriceFrequencyAttribute() {
        return this.priceFrequencyAttribute;
    }

    public void setPriceFrequencyAttribute(DraftAdMetadataAttribute draftAdMetadataAttribute) {
        this.priceFrequencyAttribute = draftAdMetadataAttribute;
    }

    @NonNull
    public List<DraftAdMetadataAttribute> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(List<DraftAdMetadataAttribute> list) {
        this.attributes = list;
    }

    public String toString() {
        return "DraftAdMetadata{categoryId='" + this.categoryId + '\'' + ", isPriceSupported=" + this.isPriceSupported + ", isPriceFrequencySupported=" + this.isPriceFrequencySupported + ", priceFrequencyAttribute=" + this.priceFrequencyAttribute + ", attributes=" + this.attributes + '}';
    }
}

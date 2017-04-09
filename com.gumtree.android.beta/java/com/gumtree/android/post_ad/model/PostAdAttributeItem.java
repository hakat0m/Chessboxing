package com.gumtree.android.post_ad.model;

import com.gumtree.android.common.utils.AppUtils;
import com.gumtree.android.metadata.SupportedValueOptions;
import com.gumtree.android.vip.VIPContactFragment;
import java.io.Serializable;
import java.util.List;

public class PostAdAttributeItem implements Serializable {
    private static final long serialVersionUID = 1;
    private String description;
    private final int isAttribute;
    private boolean isDisabled;
    private boolean isPriceSensitive;
    private final String key;
    private String label;
    private String localizedLabel;
    private String preSelectedValue;
    private String supportedLocalizedValues;
    private List<SupportedValueOptions> supportedValueOptionsList;
    private String supportedValues;
    private String type;
    private String value;

    public PostAdAttributeItem(String str, String str2, String str3, String str4, int i, boolean z, boolean z2, List<SupportedValueOptions> list) {
        this.key = str;
        this.value = str2;
        this.label = str3;
        this.isAttribute = i;
        this.description = str4;
        this.isPriceSensitive = z;
        this.isDisabled = z2;
        this.supportedValueOptionsList = list;
    }

    public PostAdAttributeItem(String str, String str2, String str3, String str4, int i, boolean z) {
        this(str, str2, str3, str4, i, z, false, null);
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public boolean isAttribute() {
        return this.isAttribute == 1;
    }

    public boolean isFeature() {
        return this.isAttribute == 2;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getLocalizedLabel() {
        return this.localizedLabel;
    }

    public void setLocalizedLabel(String str) {
        this.localizedLabel = str;
    }

    public String getSupportedValues() {
        return this.supportedValues;
    }

    public void setSupportedValues(String str) {
        this.supportedValues = str;
    }

    public String getSupportedLocalizedValues() {
        return this.supportedLocalizedValues;
    }

    public void setSupportedLocalizedValues(String str) {
        this.supportedLocalizedValues = str;
    }

    public void setAttributesValue(String str, String str2, String str3, String str4, boolean z, String str5, List<SupportedValueOptions> list) {
        this.type = str;
        this.localizedLabel = str2;
        this.supportedValues = str3;
        this.supportedLocalizedValues = str4;
        this.isDisabled = z;
        this.preSelectedValue = str5;
        this.supportedValueOptionsList = list;
    }

    public PostAdAttributeItem clone() throws CloneNotSupportedException {
        PostAdAttributeItem postAdAttributeItem = new PostAdAttributeItem(this.key, this.value, this.label, this.description, this.isAttribute, this.isPriceSensitive);
        postAdAttributeItem.setAttributesValue(this.type, this.localizedLabel, this.supportedValues, this.supportedLocalizedValues, this.isDisabled, this.preSelectedValue, this.supportedValueOptionsList);
        return postAdAttributeItem;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String toString() {
        if (this.key.equals("price")) {
            return AppUtils.getFormattedPrice(getValue());
        }
        if (this.key.equals(VIPContactFragment.CATEGORY_ID) || this.key.equals("location")) {
            return getLabel();
        }
        return getValue();
    }

    public boolean isPriceSensitive() {
        return this.isPriceSensitive;
    }

    public void setPriceSensitive(boolean z) {
        this.isPriceSensitive = z;
    }

    public List<SupportedValueOptions> getSupportedValueOptionsList() {
        return this.supportedValueOptionsList;
    }

    public void setSupportedValueOptionsList(List<SupportedValueOptions> list) {
        this.supportedValueOptionsList = list;
    }

    public boolean isDisabled() {
        return this.isDisabled;
    }

    public void setDisabled(boolean z) {
        this.isDisabled = z;
    }

    public String getPreSelectedValue() {
        return this.preSelectedValue;
    }

    public void setPreSelectedValue(String str) {
        this.preSelectedValue = str;
    }
}

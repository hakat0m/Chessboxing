package com.gumtree.android.metadata.model;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DraftAdMetadataAttribute implements Serializable {
    private LinkedHashMap<String, String> attributeValues = new LinkedHashMap();
    private String defaultValue;
    private String dialogAttributeValue;
    private String dialogBody;
    private String dialogTitle;
    private boolean isLocked;
    private boolean isPriceSensitive;
    private boolean isRequired;
    private String localisedLabel;
    private String name;
    private String popupAttributeValue;
    private String popupText;
    private MetadataType type;

    protected boolean canEqual(Object obj) {
        return obj instanceof DraftAdMetadataAttribute;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DraftAdMetadataAttribute)) {
            return false;
        }
        DraftAdMetadataAttribute draftAdMetadataAttribute = (DraftAdMetadataAttribute) obj;
        if (!draftAdMetadataAttribute.canEqual(this)) {
            return false;
        }
        String localisedLabel = getLocalisedLabel();
        String localisedLabel2 = draftAdMetadataAttribute.getLocalisedLabel();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        localisedLabel = getName();
        localisedLabel2 = draftAdMetadataAttribute.getName();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        MetadataType type = getType();
        MetadataType type2 = draftAdMetadataAttribute.getType();
        if (type != null ? !type.equals(type2) : type2 != null) {
            return false;
        }
        if (isRequired() != draftAdMetadataAttribute.isRequired()) {
            return false;
        }
        HashMap attributeValues = getAttributeValues();
        HashMap attributeValues2 = draftAdMetadataAttribute.getAttributeValues();
        if (attributeValues != null ? !attributeValues.equals(attributeValues2) : attributeValues2 != null) {
            return false;
        }
        if (isPriceSensitive() != draftAdMetadataAttribute.isPriceSensitive()) {
            return false;
        }
        localisedLabel = getDefaultValue();
        localisedLabel2 = draftAdMetadataAttribute.getDefaultValue();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        if (isLocked() != draftAdMetadataAttribute.isLocked()) {
            return false;
        }
        localisedLabel = getDialogAttributeValue();
        localisedLabel2 = draftAdMetadataAttribute.getDialogAttributeValue();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        localisedLabel = getDialogTitle();
        localisedLabel2 = draftAdMetadataAttribute.getDialogTitle();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        localisedLabel = getDialogBody();
        localisedLabel2 = draftAdMetadataAttribute.getDialogBody();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        localisedLabel = getPopupAttributeValue();
        localisedLabel2 = draftAdMetadataAttribute.getPopupAttributeValue();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        localisedLabel = getPopupText();
        localisedLabel2 = draftAdMetadataAttribute.getPopupText();
        if (localisedLabel == null) {
            if (localisedLabel2 == null) {
                return true;
            }
        } else if (localisedLabel.equals(localisedLabel2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 79;
        int i2 = 43;
        String localisedLabel = getLocalisedLabel();
        int hashCode = (localisedLabel == null ? 43 : localisedLabel.hashCode()) + 59;
        String name = getName();
        hashCode = (name == null ? 43 : name.hashCode()) + (hashCode * 59);
        MetadataType type = getType();
        hashCode = (isRequired() ? 79 : 97) + (((type == null ? 43 : type.hashCode()) + (hashCode * 59)) * 59);
        HashMap attributeValues = getAttributeValues();
        hashCode = (isPriceSensitive() ? 79 : 97) + (((attributeValues == null ? 43 : attributeValues.hashCode()) + (hashCode * 59)) * 59);
        name = getDefaultValue();
        hashCode = ((name == null ? 43 : name.hashCode()) + (hashCode * 59)) * 59;
        if (!isLocked()) {
            i = 97;
        }
        hashCode += i;
        String dialogAttributeValue = getDialogAttributeValue();
        hashCode = (dialogAttributeValue == null ? 43 : dialogAttributeValue.hashCode()) + (hashCode * 59);
        dialogAttributeValue = getDialogTitle();
        hashCode = (dialogAttributeValue == null ? 43 : dialogAttributeValue.hashCode()) + (hashCode * 59);
        dialogAttributeValue = getDialogBody();
        hashCode = (dialogAttributeValue == null ? 43 : dialogAttributeValue.hashCode()) + (hashCode * 59);
        dialogAttributeValue = getPopupAttributeValue();
        hashCode = (dialogAttributeValue == null ? 43 : dialogAttributeValue.hashCode()) + (hashCode * 59);
        dialogAttributeValue = getPopupText();
        hashCode *= 59;
        if (dialogAttributeValue != null) {
            i2 = dialogAttributeValue.hashCode();
        }
        return hashCode + i2;
    }

    public void setAttributeValues(LinkedHashMap<String, String> linkedHashMap) {
        this.attributeValues = linkedHashMap;
    }

    public void setDefaultValue(String str) {
        this.defaultValue = str;
    }

    public void setDialogAttributeValue(String str) {
        this.dialogAttributeValue = str;
    }

    public void setDialogBody(String str) {
        this.dialogBody = str;
    }

    public void setDialogTitle(String str) {
        this.dialogTitle = str;
    }

    public void setLocalisedLabel(String str) {
        this.localisedLabel = str;
    }

    public void setLocked(boolean z) {
        this.isLocked = z;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setPopupAttributeValue(String str) {
        this.popupAttributeValue = str;
    }

    public void setPopupText(String str) {
        this.popupText = str;
    }

    public void setPriceSensitive(boolean z) {
        this.isPriceSensitive = z;
    }

    public void setRequired(boolean z) {
        this.isRequired = z;
    }

    public void setType(MetadataType metadataType) {
        this.type = metadataType;
    }

    public String toString() {
        return "DraftAdMetadataAttribute(localisedLabel=" + getLocalisedLabel() + ", name=" + getName() + ", type=" + getType() + ", isRequired=" + isRequired() + ", attributeValues=" + getAttributeValues() + ", isPriceSensitive=" + isPriceSensitive() + ", defaultValue=" + getDefaultValue() + ", isLocked=" + isLocked() + ", dialogAttributeValue=" + getDialogAttributeValue() + ", dialogTitle=" + getDialogTitle() + ", dialogBody=" + getDialogBody() + ", popupAttributeValue=" + getPopupAttributeValue() + ", popupText=" + getPopupText() + ")";
    }

    public static DraftAdMetadataAttributeBuilder builder() {
        return new DraftAdMetadataAttributeBuilder();
    }

    @ConstructorProperties({"localisedLabel", "name", "type", "isRequired", "attributeValues", "isPriceSensitive", "defaultValue", "isLocked", "dialogAttributeValue", "dialogTitle", "dialogBody", "popupAttributeValue", "popupText"})
    public DraftAdMetadataAttribute(String str, String str2, MetadataType metadataType, boolean z, LinkedHashMap<String, String> linkedHashMap, boolean z2, String str3, boolean z3, String str4, String str5, String str6, String str7, String str8) {
        this.localisedLabel = str;
        this.name = str2;
        this.type = metadataType;
        this.isRequired = z;
        this.attributeValues = linkedHashMap;
        this.isPriceSensitive = z2;
        this.defaultValue = str3;
        this.isLocked = z3;
        this.dialogAttributeValue = str4;
        this.dialogTitle = str5;
        this.dialogBody = str6;
        this.popupAttributeValue = str7;
        this.popupText = str8;
    }

    public String getLocalisedLabel() {
        return this.localisedLabel;
    }

    public String getName() {
        return this.name;
    }

    public MetadataType getType() {
        return this.type;
    }

    public boolean isRequired() {
        return this.isRequired;
    }

    public boolean isPriceSensitive() {
        return this.isPriceSensitive;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    public String getDialogAttributeValue() {
        return this.dialogAttributeValue;
    }

    public String getDialogTitle() {
        return this.dialogTitle;
    }

    public String getDialogBody() {
        return this.dialogBody;
    }

    public String getPopupAttributeValue() {
        return this.popupAttributeValue;
    }

    public String getPopupText() {
        return this.popupText;
    }

    public void putAttributeValue(String str, String str2) {
        this.attributeValues.put(str, str2);
    }

    public HashMap<String, String> getAttributeValues() {
        return this.attributeValues;
    }

    public List<String> getAttributeValuesAsList() {
        if (this.attributeValues == null) {
            return Collections.emptyList();
        }
        List<String> arrayList = new ArrayList(this.attributeValues.values());
        Collections.sort(arrayList, Collections.reverseOrder());
        return arrayList;
    }
}

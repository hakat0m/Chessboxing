package com.gumtree.android.postad.customdetails.models;

import com.gumtree.android.metadata.model.MetadataType;
import java.util.Map;

public class CustomAttributeData {
    private String attributeName;
    private Map<String, String> attributeValues;
    private String defaultLocalisedValue;
    private String defaultValue;
    private String dialogAttributeValue;
    private String dialogBody;
    private String dialogTitle;
    private boolean hidden;
    private boolean isLocked;
    private boolean isPriceSensitive;
    private boolean isRequired;
    private String localisedLabel;
    private String popupAttributeValue;
    private String popupText;
    private MetadataType type;

    protected boolean canEqual(Object obj) {
        return obj instanceof CustomAttributeData;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CustomAttributeData)) {
            return false;
        }
        CustomAttributeData customAttributeData = (CustomAttributeData) obj;
        if (!customAttributeData.canEqual(this)) {
            return false;
        }
        MetadataType type = getType();
        MetadataType type2 = customAttributeData.getType();
        if (type != null ? !type.equals(type2) : type2 != null) {
            return false;
        }
        if (isRequired() != customAttributeData.isRequired()) {
            return false;
        }
        if (isPriceSensitive() != customAttributeData.isPriceSensitive()) {
            return false;
        }
        String localisedLabel = getLocalisedLabel();
        String localisedLabel2 = customAttributeData.getLocalisedLabel();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        localisedLabel = getAttributeName();
        localisedLabel2 = customAttributeData.getAttributeName();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        Map attributeValues = getAttributeValues();
        Map attributeValues2 = customAttributeData.getAttributeValues();
        if (attributeValues != null ? !attributeValues.equals(attributeValues2) : attributeValues2 != null) {
            return false;
        }
        localisedLabel = getDefaultValue();
        localisedLabel2 = customAttributeData.getDefaultValue();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        localisedLabel = getDefaultLocalisedValue();
        localisedLabel2 = customAttributeData.getDefaultLocalisedValue();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        if (isLocked() != customAttributeData.isLocked()) {
            return false;
        }
        localisedLabel = getDialogAttributeValue();
        localisedLabel2 = customAttributeData.getDialogAttributeValue();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        localisedLabel = getDialogTitle();
        localisedLabel2 = customAttributeData.getDialogTitle();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        localisedLabel = getDialogBody();
        localisedLabel2 = customAttributeData.getDialogBody();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        localisedLabel = getPopupAttributeValue();
        localisedLabel2 = customAttributeData.getPopupAttributeValue();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        localisedLabel = getPopupText();
        localisedLabel2 = customAttributeData.getPopupText();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        return isHidden() == customAttributeData.isHidden();
    }

    public int hashCode() {
        int i = 79;
        int i2 = 43;
        MetadataType type = getType();
        int hashCode = (isPriceSensitive() ? 79 : 97) + (((isRequired() ? 79 : 97) + (((type == null ? 43 : type.hashCode()) + 59) * 59)) * 59);
        String localisedLabel = getLocalisedLabel();
        hashCode = (localisedLabel == null ? 43 : localisedLabel.hashCode()) + (hashCode * 59);
        localisedLabel = getAttributeName();
        hashCode = (localisedLabel == null ? 43 : localisedLabel.hashCode()) + (hashCode * 59);
        Map attributeValues = getAttributeValues();
        hashCode = (attributeValues == null ? 43 : attributeValues.hashCode()) + (hashCode * 59);
        localisedLabel = getDefaultValue();
        hashCode = (localisedLabel == null ? 43 : localisedLabel.hashCode()) + (hashCode * 59);
        localisedLabel = getDefaultLocalisedValue();
        hashCode = (isLocked() ? 79 : 97) + (((localisedLabel == null ? 43 : localisedLabel.hashCode()) + (hashCode * 59)) * 59);
        localisedLabel = getDialogAttributeValue();
        hashCode = (localisedLabel == null ? 43 : localisedLabel.hashCode()) + (hashCode * 59);
        localisedLabel = getDialogTitle();
        hashCode = (localisedLabel == null ? 43 : localisedLabel.hashCode()) + (hashCode * 59);
        localisedLabel = getDialogBody();
        hashCode = (localisedLabel == null ? 43 : localisedLabel.hashCode()) + (hashCode * 59);
        localisedLabel = getPopupAttributeValue();
        hashCode = (localisedLabel == null ? 43 : localisedLabel.hashCode()) + (hashCode * 59);
        localisedLabel = getPopupText();
        hashCode *= 59;
        if (localisedLabel != null) {
            i2 = localisedLabel.hashCode();
        }
        hashCode = (hashCode + i2) * 59;
        if (!isHidden()) {
            i = 97;
        }
        return hashCode + i;
    }

    public void setAttributeName(String str) {
        this.attributeName = str;
    }

    public void setAttributeValues(Map<String, String> map) {
        this.attributeValues = map;
    }

    public void setDefaultLocalisedValue(String str) {
        this.defaultLocalisedValue = str;
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

    public void setHidden(boolean z) {
        this.hidden = z;
    }

    public void setLocalisedLabel(String str) {
        this.localisedLabel = str;
    }

    public void setLocked(boolean z) {
        this.isLocked = z;
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
        return "CustomAttributeData(type=" + getType() + ", isRequired=" + isRequired() + ", isPriceSensitive=" + isPriceSensitive() + ", localisedLabel=" + getLocalisedLabel() + ", attributeName=" + getAttributeName() + ", attributeValues=" + getAttributeValues() + ", defaultValue=" + getDefaultValue() + ", defaultLocalisedValue=" + getDefaultLocalisedValue() + ", isLocked=" + isLocked() + ", dialogAttributeValue=" + getDialogAttributeValue() + ", dialogTitle=" + getDialogTitle() + ", dialogBody=" + getDialogBody() + ", popupAttributeValue=" + getPopupAttributeValue() + ", popupText=" + getPopupText() + ", hidden=" + isHidden() + ")";
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

    public String getLocalisedLabel() {
        return this.localisedLabel;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public Map<String, String> getAttributeValues() {
        return this.attributeValues;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public String getDefaultLocalisedValue() {
        return this.defaultLocalisedValue;
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

    public boolean isHidden() {
        return this.hidden;
    }
}

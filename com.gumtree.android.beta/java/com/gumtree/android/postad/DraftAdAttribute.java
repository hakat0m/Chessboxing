package com.gumtree.android.postad;

import java.beans.ConstructorProperties;
import java.io.Serializable;

public class DraftAdAttribute implements Serializable {
    private String localisedLabel;
    private String name;
    private String value;
    private String valueLocalisedLabel;

    protected boolean canEqual(Object obj) {
        return obj instanceof DraftAdAttribute;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DraftAdAttribute)) {
            return false;
        }
        DraftAdAttribute draftAdAttribute = (DraftAdAttribute) obj;
        if (!draftAdAttribute.canEqual(this)) {
            return false;
        }
        String name = getName();
        String name2 = draftAdAttribute.getName();
        if (name != null ? !name.equals(name2) : name2 != null) {
            return false;
        }
        name = getLocalisedLabel();
        name2 = draftAdAttribute.getLocalisedLabel();
        if (name != null ? !name.equals(name2) : name2 != null) {
            return false;
        }
        name = getValueLocalisedLabel();
        name2 = draftAdAttribute.getValueLocalisedLabel();
        if (name != null ? !name.equals(name2) : name2 != null) {
            return false;
        }
        name = getValue();
        name2 = draftAdAttribute.getValue();
        if (name == null) {
            if (name2 == null) {
                return true;
            }
        } else if (name.equals(name2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String name = getName();
        int hashCode = (name == null ? 43 : name.hashCode()) + 59;
        String localisedLabel = getLocalisedLabel();
        hashCode = (localisedLabel == null ? 43 : localisedLabel.hashCode()) + (hashCode * 59);
        localisedLabel = getValueLocalisedLabel();
        hashCode = (localisedLabel == null ? 43 : localisedLabel.hashCode()) + (hashCode * 59);
        localisedLabel = getValue();
        hashCode *= 59;
        if (localisedLabel != null) {
            i = localisedLabel.hashCode();
        }
        return hashCode + i;
    }

    public void setLocalisedLabel(String str) {
        this.localisedLabel = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public void setValueLocalisedLabel(String str) {
        this.valueLocalisedLabel = str;
    }

    public String toString() {
        return "DraftAdAttribute(name=" + getName() + ", localisedLabel=" + getLocalisedLabel() + ", valueLocalisedLabel=" + getValueLocalisedLabel() + ", value=" + getValue() + ")";
    }

    public static DraftAdAttributeBuilder builder() {
        return new DraftAdAttributeBuilder();
    }

    @ConstructorProperties({"name", "localisedLabel", "valueLocalisedLabel", "value"})
    public DraftAdAttribute(String str, String str2, String str3, String str4) {
        this.name = str;
        this.localisedLabel = str2;
        this.valueLocalisedLabel = str3;
        this.value = str4;
    }

    public String getName() {
        return this.name;
    }

    public String getLocalisedLabel() {
        return this.localisedLabel;
    }

    public String getValueLocalisedLabel() {
        return this.valueLocalisedLabel;
    }

    public String getValue() {
        return this.value;
    }
}

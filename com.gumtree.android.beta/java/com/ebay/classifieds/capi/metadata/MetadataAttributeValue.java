package com.ebay.classifieds.capi.metadata;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "value", strict = false)
@Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public final class MetadataAttributeValue {
    @Attribute(name = "localized-label", required = false)
    private final String localisedLabel;
    @Text(required = false)
    private final String value;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MetadataAttributeValue)) {
            return false;
        }
        MetadataAttributeValue metadataAttributeValue = (MetadataAttributeValue) obj;
        String localisedLabel = getLocalisedLabel();
        String localisedLabel2 = metadataAttributeValue.getLocalisedLabel();
        if (localisedLabel != null ? !localisedLabel.equals(localisedLabel2) : localisedLabel2 != null) {
            return false;
        }
        localisedLabel = getValue();
        localisedLabel2 = metadataAttributeValue.getValue();
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
        int i = 43;
        String localisedLabel = getLocalisedLabel();
        int hashCode = (localisedLabel == null ? 43 : localisedLabel.hashCode()) + 59;
        String value = getValue();
        hashCode *= 59;
        if (value != null) {
            i = value.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "MetadataAttributeValue(localisedLabel=" + getLocalisedLabel() + ", value=" + getValue() + ")";
    }

    public static MetadataAttributeValueBuilder builder() {
        return new MetadataAttributeValueBuilder();
    }

    public String getLocalisedLabel() {
        return this.localisedLabel;
    }

    public String getValue() {
        return this.value;
    }

    public MetadataAttributeValue(@Attribute(name = "localized-label") String str, @Text String str2) {
        this.localisedLabel = str;
        this.value = str2;
    }
}

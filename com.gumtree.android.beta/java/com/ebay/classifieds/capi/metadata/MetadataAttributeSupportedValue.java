package com.ebay.classifieds.capi.metadata;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "supported-value", strict = false)
@Namespace(prefix = "attr", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public class MetadataAttributeSupportedValue {
    @Attribute(name = "localized-label", required = false)
    private String localisedLabel;
    @Text(required = false)
    private String value;

    public String getLocalisedLabel() {
        return this.localisedLabel;
    }

    public String getValue() {
        return this.value;
    }

    public void setLocalisedLabel(String str) {
        this.localisedLabel = str;
    }

    public void setValue(String str) {
        this.value = str;
    }
}

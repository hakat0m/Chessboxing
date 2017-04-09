package com.ebay.classifieds.capi.metadata;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "supported-value", strict = false)
@Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public class MetadataTypesSupportedValue {
    @Attribute(name = "localized-label", required = false)
    private String localisedLabel;
    private String value;

    public String getLocalisedLabel() {
        return this.localisedLabel;
    }

    @Text
    public String getValue() {
        return this.value;
    }

    public void setLocalisedLabel(String str) {
        this.localisedLabel = str;
    }

    @Text
    public void setValue(String str) {
        this.value = str;
    }
}

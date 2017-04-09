package com.ebay.classifieds.capi.ads;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "value", strict = false)
@Namespace(prefix = "attr", reference = "http://www.ebayclassifiedsgroup.com/schema/attribute/v1")
public class AttributeValue {
    @Attribute(name = "localized-label", required = false)
    private String localizedLabel;
    @Text(required = false)
    private String value;

    public String getLocalizedLabel() {
        return this.localizedLabel;
    }

    public String getValue() {
        return this.value;
    }

    public void setLocalizedLabel(String str) {
        this.localizedLabel = str;
    }

    public void setValue(String str) {
        this.value = str;
    }
}

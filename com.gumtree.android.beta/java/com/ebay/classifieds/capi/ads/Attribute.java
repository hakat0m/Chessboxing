package com.ebay.classifieds.capi.ads;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "attribute", strict = false)
@Namespace(prefix = "attr", reference = "http://www.ebayclassifiedsgroup.com/schema/attribute/v1")
public class Attribute {
    @Element(name = "value", required = false)
    private AttributeValue attributeValue;
    @org.simpleframework.xml.Attribute(name = "localized-label", required = false)
    private String localizedLabel;
    @org.simpleframework.xml.Attribute(name = "name", required = false)
    private String name;

    public String getName() {
        return this.name;
    }

    public String getLocalizedLabel() {
        return this.localizedLabel;
    }

    public AttributeValue getAttributeValue() {
        return this.attributeValue;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setLocalizedLabel(String str) {
        this.localizedLabel = str;
    }

    public void setAttributeValue(AttributeValue attributeValue) {
        this.attributeValue = attributeValue;
    }
}

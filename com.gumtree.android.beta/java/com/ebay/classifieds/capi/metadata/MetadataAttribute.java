package com.ebay.classifieds.capi.metadata;

import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "attribute", strict = false)
@Namespace(prefix = "attr", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public class MetadataAttribute {
    @Attribute(name = "price-sensitive", required = false)
    private boolean isPriceSensitive;
    @Attribute(name = "localized-label", required = false)
    private String localisedLabel;
    @ElementList(inline = true, required = false)
    private List<MetadataAttributeOption> metadataAttributeOptions;
    @ElementList(inline = true, required = false)
    private List<MetadataAttributeSupportedValue> metadataAttributeSupportedValues;
    @Element(name = "value", required = false)
    private MetadataAttributeValue metadataAttributeValue;
    @Attribute(name = "name", required = false)
    private String name;
    @Attribute(name = "read", required = false)
    private String read;
    @Attribute(name = "type", required = false)
    private MetadataType type;
    @Attribute(name = "write", required = false)
    private String write;

    public String getLocalisedLabel() {
        return this.localisedLabel;
    }

    public void setLocalisedLabel(String str) {
        this.localisedLabel = str;
    }

    public String getWrite() {
        return this.write;
    }

    public void setWrite(String str) {
        this.write = str;
    }

    public String getRead() {
        return this.read;
    }

    public void setRead(String str) {
        this.read = str;
    }

    public MetadataType getType() {
        return this.type;
    }

    public void setType(MetadataType metadataType) {
        this.type = metadataType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public boolean isPriceSensitive() {
        return this.isPriceSensitive;
    }

    public void setPriceSensitive(boolean z) {
        this.isPriceSensitive = z;
    }

    public List<MetadataAttributeSupportedValue> getMetadataAttributeSupportedValues() {
        return this.metadataAttributeSupportedValues;
    }

    public void setMetadataAttributeSupportedValues(List<MetadataAttributeSupportedValue> list) {
        this.metadataAttributeSupportedValues = list;
    }

    public List<MetadataAttributeOption> getMetadataAttributeOptions() {
        return this.metadataAttributeOptions;
    }

    public void setMetadataAttributeOptions(List<MetadataAttributeOption> list) {
        this.metadataAttributeOptions = list;
    }

    public MetadataAttributeValue getMetadataAttributeValue() {
        return this.metadataAttributeValue;
    }

    public void setMetadataAttributeValue(MetadataAttributeValue metadataAttributeValue) {
        this.metadataAttributeValue = metadataAttributeValue;
    }
}

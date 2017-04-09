package com.ebay.classifieds.capi.metadata;

import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class PriceMetadataAttribute {
    @Attribute(name = "localized-label", required = false)
    private String localisedLabel;
    @ElementList(inline = true, required = false)
    private List<MetadataTypesSupportedValue> metadataTypesSupportedValues;
    @Attribute(name = "read", required = false)
    private String read;
    @Attribute(name = "type", required = false)
    private MetadataType type;
    @Attribute(name = "write", required = false)
    private String write;

    public String getLocalisedLabel() {
        return this.localisedLabel;
    }

    public String getWrite() {
        return this.write;
    }

    public String getRead() {
        return this.read;
    }

    public MetadataType getType() {
        return this.type;
    }

    public List<MetadataTypesSupportedValue> getMetadataTypesSupportedValues() {
        return this.metadataTypesSupportedValues;
    }

    public void setLocalisedLabel(String str) {
        this.localisedLabel = str;
    }

    public void setWrite(String str) {
        this.write = str;
    }

    public void setRead(String str) {
        this.read = str;
    }

    public void setType(MetadataType metadataType) {
        this.type = metadataType;
    }

    public void setMetadataTypesSupportedValues(List<MetadataTypesSupportedValue> list) {
        this.metadataTypesSupportedValues = list;
    }
}

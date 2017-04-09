package com.ebay.classifieds.capi.metadata;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "options", strict = false)
@Namespace(prefix = "attr", reference = "http://www.ebayclassifiedsgroup.com/schema/attribute/v1")
public class MetadataAttributeOption {
    @Attribute(name = "disabled", required = false)
    private boolean disabled;
    @Element(name = "link", required = false)
    private MetadataLink metadataLink;
    @Element(name = "pop-up", required = false)
    private MetadataPopup metadataPopup;
    @Attribute(name = "selected", required = false)
    private boolean selected;
    @Attribute(name = "supported-value", required = false)
    private String supportedValue;

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean z) {
        this.disabled = z;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean z) {
        this.selected = z;
    }

    public String getSupportedValue() {
        return this.supportedValue;
    }

    public void setSupportedValue(String str) {
        this.supportedValue = str;
    }

    public MetadataLink getMetadataLink() {
        return this.metadataLink;
    }

    public void setMetadataLink(MetadataLink metadataLink) {
        this.metadataLink = metadataLink;
    }

    public MetadataPopup getMetadataPopup() {
        return this.metadataPopup;
    }

    public void setMetadataPopup(MetadataPopup metadataPopup) {
        this.metadataPopup = metadataPopup;
    }
}

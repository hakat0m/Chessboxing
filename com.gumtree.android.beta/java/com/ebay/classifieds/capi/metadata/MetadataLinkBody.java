package com.ebay.classifieds.capi.metadata;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "body", strict = false)
@Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public class MetadataLinkBody {
    @Element(required = false)
    private MetadataLinkBodyText text;

    public MetadataLinkBodyText getText() {
        return this.text;
    }

    public void setText(MetadataLinkBodyText metadataLinkBodyText) {
        this.text = metadataLinkBodyText;
    }
}

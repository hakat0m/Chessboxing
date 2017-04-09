package com.ebay.classifieds.capi.metadata;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "link", strict = false)
@Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public class MetadataLink {
    @Element(required = false)
    private MetadataLinkBody body;
    @Element(required = false)
    private MetadataLinkTitle title;

    public MetadataLinkTitle getTitle() {
        return this.title;
    }

    public void setTitle(MetadataLinkTitle metadataLinkTitle) {
        this.title = metadataLinkTitle;
    }

    public MetadataLinkBody getBody() {
        return this.body;
    }

    public void setBody(MetadataLinkBody metadataLinkBody) {
        this.body = metadataLinkBody;
    }
}

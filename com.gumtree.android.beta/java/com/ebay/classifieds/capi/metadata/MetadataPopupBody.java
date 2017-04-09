package com.ebay.classifieds.capi.metadata;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "body", strict = false)
@Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public class MetadataPopupBody {
    @Element(required = false)
    private MetadataPopupBodyText text;

    public MetadataPopupBodyText getText() {
        return this.text;
    }

    public void setText(MetadataPopupBodyText metadataPopupBodyText) {
        this.text = metadataPopupBodyText;
    }
}

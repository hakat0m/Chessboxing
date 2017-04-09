package com.ebay.classifieds.capi.metadata;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "pop-up", strict = false)
@Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public class MetadataPopup {
    @Element(required = false)
    private MetadataPopupBody body;

    public MetadataPopupBody getBody() {
        return this.body;
    }

    public void setBody(MetadataPopupBody metadataPopupBody) {
        this.body = metadataPopupBody;
    }
}

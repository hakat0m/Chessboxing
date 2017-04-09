package com.ebay.classifieds.capi.metadata;

import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "text", strict = false)
@Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public class MetadataLinkBodyText {
    private String text;

    @Text
    public String getText() {
        return this.text;
    }

    @Text
    public void setText(String str) {
        this.text = str;
    }
}

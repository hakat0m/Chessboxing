package com.ebay.classifieds.capi.metadata;

import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "title", strict = false)
@Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public class MetadataLinkTitle {
    private String title;

    @Text
    public String getTitle() {
        return this.title;
    }

    @Text
    public void setTitle(String str) {
        this.title = str;
    }
}

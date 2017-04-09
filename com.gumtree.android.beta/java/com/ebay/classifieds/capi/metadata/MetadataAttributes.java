package com.ebay.classifieds.capi.metadata;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "attributes", strict = false)
@Namespace(prefix = "attr", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public class MetadataAttributes {
    @ElementList(inline = true, required = false)
    private List<MetadataAttribute> metadataAttributes;

    public List<MetadataAttribute> getMetadataAttributes() {
        return this.metadataAttributes;
    }

    public void setMetadataAttributes(List<MetadataAttribute> list) {
        this.metadataAttributes = list;
    }
}

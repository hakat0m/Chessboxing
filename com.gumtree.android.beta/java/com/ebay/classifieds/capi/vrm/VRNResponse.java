package com.ebay.classifieds.capi.vrm;

import com.ebay.classifieds.capi.metadata.MetadataAttributes;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "vrn-response", strict = false)
@Namespace(prefix = "vrn", reference = "http://www.ebayclassifiedsgroup.com/schema/vrn/v1")
public final class VRNResponse {
    @Element(name = "attributes", required = false)
    private final MetadataAttributes attributes;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VRNResponse)) {
            return false;
        }
        VRNResponse vRNResponse = (VRNResponse) obj;
        MetadataAttributes attributes = getAttributes();
        MetadataAttributes attributes2 = vRNResponse.getAttributes();
        if (attributes == null) {
            if (attributes2 == null) {
                return true;
            }
        } else if (attributes.equals(attributes2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        MetadataAttributes attributes = getAttributes();
        return (attributes == null ? 43 : attributes.hashCode()) + 59;
    }

    public String toString() {
        return "VRNResponse(attributes=" + getAttributes() + ")";
    }

    public static VRNResponseBuilder builder() {
        return new VRNResponseBuilder();
    }

    public MetadataAttributes getAttributes() {
        return this.attributes;
    }

    public VRNResponse(@Element(name = "attributes") MetadataAttributes metadataAttributes) {
        this.attributes = metadataAttributes;
    }
}

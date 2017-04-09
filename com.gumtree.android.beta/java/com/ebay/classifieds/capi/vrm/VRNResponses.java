package com.ebay.classifieds.capi.vrm;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "vrn-responses", strict = false)
@Namespace(prefix = "vrn", reference = "http://www.ebayclassifiedsgroup.com/schema/vrn/v1")
public final class VRNResponses {
    @Element(name = "vrn-response", required = false)
    private final VRNResponse vrnResponse;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VRNResponses)) {
            return false;
        }
        VRNResponses vRNResponses = (VRNResponses) obj;
        VRNResponse vrnResponse = getVrnResponse();
        VRNResponse vrnResponse2 = vRNResponses.getVrnResponse();
        if (vrnResponse == null) {
            if (vrnResponse2 == null) {
                return true;
            }
        } else if (vrnResponse.equals(vrnResponse2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        VRNResponse vrnResponse = getVrnResponse();
        return (vrnResponse == null ? 43 : vrnResponse.hashCode()) + 59;
    }

    public String toString() {
        return "VRNResponses(vrnResponse=" + getVrnResponse() + ")";
    }

    public static VRNResponsesBuilder builder() {
        return new VRNResponsesBuilder();
    }

    public VRNResponse getVrnResponse() {
        return this.vrnResponse;
    }

    public VRNResponses(@Element(name = "vrn-response") VRNResponse vRNResponse) {
        this.vrnResponse = vRNResponse;
    }
}

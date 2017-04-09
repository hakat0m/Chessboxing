package com.ebay.classifieds.capi.ads;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "search-distance", strict = false)
@Namespace(prefix = "ad", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
public class SearchDistance {
    @Element(name = "display-distance", required = false)
    private String displayDistance;
    @Element(name = "distance", required = false)
    private String distance;

    public String getDistance() {
        return this.distance;
    }

    public String getDisplayDistance() {
        return this.displayDistance;
    }
}

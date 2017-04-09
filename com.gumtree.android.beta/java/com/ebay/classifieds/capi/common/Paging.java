package com.ebay.classifieds.capi.common;

import com.ebay.classifieds.capi.ads.Link;
import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "paging", strict = false)
@Namespace(prefix = "types")
public final class Paging {
    @ElementList(inline = true, required = false)
    private final List<Link> links;
    @Element(name = "numFound", required = false)
    private final int numFound;
    @Element(name = "numNearbyFound", required = false)
    private final int numNearbyFound;
    @Element(name = "numTopAdsFound", required = false)
    private final int numTopAdsFound;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Paging)) {
            return false;
        }
        Paging paging = (Paging) obj;
        if (getNumFound() != paging.getNumFound()) {
            return false;
        }
        if (getNumNearbyFound() != paging.getNumNearbyFound()) {
            return false;
        }
        if (getNumTopAdsFound() != paging.getNumTopAdsFound()) {
            return false;
        }
        List links = getLinks();
        List links2 = paging.getLinks();
        if (links == null) {
            if (links2 == null) {
                return true;
            }
        } else if (links.equals(links2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int numFound = ((((getNumFound() + 59) * 59) + getNumNearbyFound()) * 59) + getNumTopAdsFound();
        List links = getLinks();
        return (links == null ? 43 : links.hashCode()) + (numFound * 59);
    }

    public String toString() {
        return "Paging(numFound=" + getNumFound() + ", numNearbyFound=" + getNumNearbyFound() + ", numTopAdsFound=" + getNumTopAdsFound() + ", links=" + getLinks() + ")";
    }

    public int getNumFound() {
        return this.numFound;
    }

    public int getNumNearbyFound() {
        return this.numNearbyFound;
    }

    public int getNumTopAdsFound() {
        return this.numTopAdsFound;
    }

    public List<Link> getLinks() {
        return this.links;
    }

    public Paging() {
        this.numFound = 0;
        this.numNearbyFound = 0;
        this.numTopAdsFound = 0;
        this.links = new ArrayList();
    }

    public Paging(@Element(name = "numFound") int i, @Element(name = "numNearbyFound") int i2, @Element(name = "numTopAdsFound") int i3, @ElementList(inline = true) List<Link> list) {
        this.numFound = i;
        this.numNearbyFound = i2;
        this.numTopAdsFound = i3;
        this.links = list;
    }
}

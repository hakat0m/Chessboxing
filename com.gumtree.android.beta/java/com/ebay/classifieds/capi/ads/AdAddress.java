package com.ebay.classifieds.capi.ads;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "ad-address", strict = false)
@Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public class AdAddress {
    @Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
    @Element(name = "latitude", required = false)
    private String latitude;
    @Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
    @Element(name = "longitude", required = false)
    private String longitude;
    @Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
    @Element(name = "zip-code", required = false)
    private String zipCode;

    public String getLongitude() {
        return this.longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLongitude(String str) {
        this.longitude = str;
    }

    public void setLatitude(String str) {
        this.latitude = str;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String str) {
        this.zipCode = str;
    }
}

package com.ebay.classifieds.capi.users.profile;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "user-address", strict = false)
@Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public class UserAddress {
    @Element(name = "city", required = false)
    private String city;
    @Element(name = "latitude", required = false)
    private String latitude;
    @Element(name = "location-id", required = false)
    private String locationId;
    @Element(name = "longitude", required = false)
    private String longitude;
    @Element(name = "neighborhood", required = false)
    private String neighborhood;
    @Element(name = "visible-on-map", required = false)
    private String visibleOnMap;
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

    public String getCity() {
        return this.city;
    }

    public void setCity(String str) {
        this.city = str;
    }

    public String getVisibleOnMap() {
        return this.visibleOnMap;
    }

    public void setVisibleOnMap(String str) {
        this.visibleOnMap = str;
    }

    public String getNeighborhood() {
        return this.neighborhood;
    }

    public void setNeighborhood(String str) {
        this.neighborhood = str;
    }

    public String getLocationId() {
        return this.locationId;
    }

    public void setLocationId(String str) {
        this.locationId = str;
    }
}

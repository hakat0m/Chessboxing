package com.ebay.classifieds.capi.locations;

import java.util.ArrayList;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "loc:locations", strict = false)
public class Locations {
    @Attribute(name = "locale", required = false)
    private String locale;
    @ElementList(inline = true)
    private ArrayList<Location> locations;
    @Attribute(name = "version", required = false)
    private String version;

    public String getLocale() {
        return this.locale;
    }

    public String getVersion() {
        return this.version;
    }

    public ArrayList<Location> getLocations() {
        return this.locations;
    }

    public void setLocations(ArrayList<Location> arrayList) {
        this.locations = arrayList;
    }
}

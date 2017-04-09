package com.ebay.classifieds.capi.seo;

import com.ebay.classifieds.capi.categories.Category;
import com.ebay.classifieds.capi.locations.Location;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

@NamespaceList({@Namespace(prefix = "loc", reference = "http://www.ebayclassifiedsgroup.com/schema/location/v1"), @Namespace(prefix = "cat", reference = "http://www.ebayclassifiedsgroup.com/schema/category/v1")})
@Root(name = "seoLookupResult", strict = false)
@Namespace(prefix = "seo", reference = "http://www.ebayclassifiedsgroup.com/schema/seolookup/v1")
public class Seo {
    @Element(required = false)
    private Category category;
    @Element(required = false)
    private Location location;

    public Category getCategory() {
        return this.category;
    }

    public Location getLocation() {
        return this.location;
    }
}

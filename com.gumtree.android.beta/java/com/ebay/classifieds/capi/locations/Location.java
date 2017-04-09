package com.ebay.classifieds.capi.locations;

import com.ebay.classifieds.capi.executor.TreeNode;
import java.util.ArrayList;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "location", strict = false)
@Namespace(prefix = "loc", reference = "http://www.ebayclassifiedsgroup.com/schema/location/v1")
public class Location implements TreeNode {
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/location/v1")
    @Element(name = "location-breadcrumb", required = false)
    private String breadCrumb;
    @Attribute(name = "id", required = false)
    private Long id;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/location/v1")
    @Element(name = "leaf", required = false)
    private boolean isLeaf;
    @Attribute(name = "locale", required = false)
    private String locale;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/location/v1")
    @Element(name = "localized-name", required = false)
    private String localizedName;
    @ElementList(entry = "location", inline = true, required = false)
    private ArrayList<Location> locations;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/location/v1")
    @Element(name = "id-name", required = false)
    private String name;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/location/v1")
    @Element(name = "parent-id", required = false)
    private long parentId;
    @Attribute(name = "version", required = false)
    private String version;

    public Location(Long l, String str) {
        this.id = l;
        this.localizedName = str;
    }

    public String getIdName() {
        return this.name;
    }

    public long getParentId() {
        return this.parentId;
    }

    public String getLocationBreadcrumb() {
        return this.breadCrumb;
    }

    public String getLocale() {
        return this.locale;
    }

    public String getVersion() {
        return this.version;
    }

    public ArrayList<Location> getLocations() {
        return this.locations;
    }

    public Long getId() {
        return this.id;
    }

    public String getLocalizedName() {
        return this.localizedName;
    }

    public boolean isLeaf() {
        return this.isLeaf;
    }

    public void setIdName(String str) {
        this.name = str;
    }

    public void setLocalizedName(String str) {
        this.localizedName = str;
    }

    public void setParentId(long j) {
        this.parentId = j;
    }

    public void setLocationBreadcrumb(String str) {
        this.breadCrumb = str;
    }

    public void setLeaf(boolean z) {
        this.isLeaf = z;
    }

    public void setLocale(String str) {
        this.locale = str;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public void setId(Long l) {
        this.id = l;
    }

    public void setLocations(ArrayList<Location> arrayList) {
        this.locations = arrayList;
    }
}

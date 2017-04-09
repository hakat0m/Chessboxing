package com.ebay.classifieds.capi.ads;

import java.io.Serializable;
import java.util.ArrayList;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "picture", strict = false)
@Namespace(prefix = "pic", reference = "http://www.ebayclassifiedsgroup.com/schema/picture/v1")
public class Picture implements Serializable {
    @Attribute(name = "deprecated", required = false)
    private String deprecated;
    @ElementList(inline = true, required = false)
    private ArrayList<PictureLink> links;
    @Attribute(name = "localized-label", required = false)
    private String localizedLabel;
    @Attribute(name = "read", required = false)
    private String read;
    @Attribute(name = "search-response-included", required = false)
    private String searchResponseIncluded;
    @Attribute(name = "since", required = false)
    private String since;
    @Attribute(name = "write", required = false)
    private String write;

    public ArrayList<PictureLink> getLinks() {
        return this.links;
    }

    public void setLinks(ArrayList<PictureLink> arrayList) {
        this.links = arrayList;
    }

    public String getSince() {
        return this.since;
    }

    public String getDeprecated() {
        return this.deprecated;
    }

    public String getLocalizedLabel() {
        return this.localizedLabel;
    }

    public String getSearchResponseIncluded() {
        return this.searchResponseIncluded;
    }

    public String getWrite() {
        return this.write;
    }

    public String getRead() {
        return this.read;
    }
}

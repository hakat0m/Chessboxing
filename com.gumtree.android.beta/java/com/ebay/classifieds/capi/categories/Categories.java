package com.ebay.classifieds.capi.categories;

import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "categories", strict = false)
@Namespace(prefix = "cat", reference = "http://www.ebayclassifiedsgroup.com/schema/category/v1")
public class Categories {
    @ElementList(entry = "category", inline = true, required = false)
    private List<Category> categories;
    @Attribute(name = "locale")
    private String locale;
    @Attribute(name = "version")
    private String version;

    public String getLocale() {
        return this.locale;
    }

    public String getVersion() {
        return this.version;
    }

    public List<Category> getCategories() {
        return this.categories;
    }
}

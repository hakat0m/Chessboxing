package com.ebay.classifieds.capi.categories;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "category", strict = false)
@Namespace(prefix = "cat", reference = "http://www.ebayclassifiedsgroup.com/schema/category/v1")
public class Category {
    public static final long CATEGORY_NOT_PRESENT = 0;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/category/v1")
    @Element(name = "categoriesTree", required = false)
    private String categoriesTree;
    @Attribute(name = "id", required = false)
    private long id;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/category/v1")
    @Element(name = "id-name", required = false)
    private String idName;
    @Attribute(name = "localized-label", required = false)
    private String localizedLabel;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/category/v1")
    @Element(name = "localized-name", required = false)
    private String localizedName;

    public String getIdName() {
        return this.idName;
    }

    public String getLocalizedName() {
        return this.localizedName;
    }

    public String getCategoriesTree() {
        return this.categoriesTree;
    }

    public String getLocalizedLabel() {
        return this.localizedLabel;
    }

    public long getId() {
        return this.id;
    }

    public void setIdName(String str) {
        this.idName = str;
    }

    public void setLocalizedName(String str) {
        this.localizedName = str;
    }

    public void setCategoriesTree(String str) {
        this.categoriesTree = str;
    }

    public void setLocalizedLabel(String str) {
        this.localizedLabel = str;
    }

    public void setId(long j) {
        this.id = j;
    }
}

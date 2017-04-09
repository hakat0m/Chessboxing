package com.ebay.classifieds.capi.features;

import java.util.List;
import org.apache.commons.lang3.Validate;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(strict = false)
@Namespace(prefix = "feat", reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
public final class Feature {
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
    @Element(name = "description")
    private final String description;
    @Attribute(name = "name")
    private final String name;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
    @ElementList(name = "options")
    private final List<Option> options;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Feature)) {
            return false;
        }
        Feature feature = (Feature) obj;
        String name = getName();
        String name2 = feature.getName();
        if (name != null ? !name.equals(name2) : name2 != null) {
            return false;
        }
        name = getDescription();
        name2 = feature.getDescription();
        if (name != null ? !name.equals(name2) : name2 != null) {
            return false;
        }
        List options = getOptions();
        List options2 = feature.getOptions();
        if (options == null) {
            if (options2 == null) {
                return true;
            }
        } else if (options.equals(options2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String name = getName();
        int hashCode = (name == null ? 43 : name.hashCode()) + 59;
        String description = getDescription();
        hashCode = (description == null ? 43 : description.hashCode()) + (hashCode * 59);
        List options = getOptions();
        hashCode *= 59;
        if (options != null) {
            i = options.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "Feature(name=" + getName() + ", description=" + getDescription() + ", options=" + getOptions() + ")";
    }

    public static FeatureBuilder builder() {
        return new FeatureBuilder();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Option> getOptions() {
        return this.options;
    }

    public Feature(@Attribute(name = "name") String str, @Element(name = "description") String str2, @ElementList(name = "options") List<Option> list) {
        this.name = str;
        this.description = str2;
        this.options = (List) Validate.notEmpty(list);
    }

    public Type getType() {
        return Type.getFeatureType(this.name);
    }
}

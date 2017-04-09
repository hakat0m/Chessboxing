package com.ebay.classifieds.capi.ads;

import com.ebay.classifieds.capi.features.Feature.Type;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "feature-active", strict = false)
@Namespace(prefix = "feat", reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
public class ActiveFeature {
    @Attribute(name = "name", required = false)
    private String name;

    public ActiveFeature(String str) {
        this.name = str;
    }

    public Type getType() {
        return Type.getFeatureType(this.name);
    }

    public String getName() {
        return this.name;
    }
}

package com.gumtree.android.ads;

import java.util.ArrayList;
import java.util.List;

public class PostAd {
    private List<CoreAttribute> coreAttributeses = new ArrayList();
    private List<DynamicAttribute> dynamicAttributes = new ArrayList();
    private List<String> featuresActive = new ArrayList();
    private String id;
    private List<Picture> pictures = new ArrayList();

    public void addCoreAttribute(CoreAttribute coreAttribute) {
        this.coreAttributeses.add(coreAttribute);
    }

    public void addFeatureActive(String str) {
        this.featuresActive.add(str);
    }

    public void addDynamicAttribute(DynamicAttribute dynamicAttribute) {
        this.dynamicAttributes.add(dynamicAttribute);
    }

    public List<CoreAttribute> getCoreAttributeses() {
        return this.coreAttributeses;
    }

    public List<DynamicAttribute> getDynamicAttributes() {
        return this.dynamicAttributes;
    }

    public List<Picture> getPictures() {
        return this.pictures;
    }

    public void addPicture(Picture picture) {
        this.pictures.add(picture);
    }

    public List<String> getFeaturesActive() {
        return this.featuresActive;
    }

    public CoreAttribute getCoreAttributeByKey(String str) {
        for (CoreAttribute coreAttribute : this.coreAttributeses) {
            if (coreAttribute.getKey().equalsIgnoreCase(str)) {
                return coreAttribute;
            }
        }
        return null;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }
}

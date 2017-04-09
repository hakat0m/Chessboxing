package com.gumtree.android.metadata;

import java.util.List;

public class SearchMetadata {
    private String description;
    private boolean disabled;
    private boolean dynamic;
    private String localizedName;
    private int minSize;
    private String name;
    private String preSelectedValue;
    private boolean priceSensitive;
    private String read;
    private String searchParam;
    private String searchResponseIncluded;
    private String searchStyle;
    private String supportedLabels;
    private List<SupportedValueOptions> supportedValueOptions;
    private String supportedValues;
    private String type;
    private String write;

    public SearchMetadata(boolean z) {
        this.dynamic = z;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getLocalizedName() {
        return this.localizedName;
    }

    public void setLocalizedName(String str) {
        this.localizedName = str;
    }

    public String getRead() {
        return this.read;
    }

    public void setRead(String str) {
        this.read = str;
    }

    public String getSearchParam() {
        return this.searchParam;
    }

    public void setSearchParam(String str) {
        this.searchParam = str;
    }

    public String getSearchResponseIncluded() {
        return this.searchResponseIncluded;
    }

    public void setSearchResponseIncluded(String str) {
        this.searchResponseIncluded = str;
    }

    public String getSearchStyle() {
        return this.searchStyle;
    }

    public void setSearchStyle(String str) {
        this.searchStyle = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getWrite() {
        return this.write;
    }

    public void setWrite(String str) {
        this.write = str;
    }

    public String getSupportedValues() {
        return this.supportedValues;
    }

    public void setSupportedValues(String str) {
        this.supportedValues = str;
    }

    public String getSupportedLabels() {
        return this.supportedLabels;
    }

    public void setSupportedLabels(String str) {
        this.supportedLabels = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public boolean isDynamic() {
        return this.dynamic;
    }

    public int getMinSize() {
        return this.minSize;
    }

    public void setMinSize(int i) {
        this.minSize = i;
    }

    public boolean isPriceSensitive() {
        return this.priceSensitive;
    }

    public void setPriceSensitive(boolean z) {
        this.priceSensitive = z;
    }

    public List<SupportedValueOptions> getSupportedValueOptions() {
        return this.supportedValueOptions;
    }

    public void setSupportedValueOptions(List<SupportedValueOptions> list) {
        this.supportedValueOptions = list;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean z) {
        this.disabled = z;
    }

    public String getPreSelectedValue() {
        return this.preSelectedValue;
    }

    public void setPreSelectedValue(String str) {
        this.preSelectedValue = str;
    }
}

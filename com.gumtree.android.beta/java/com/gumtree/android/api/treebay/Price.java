package com.gumtree.android.api.treebay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {
    private String currency;
    private String value;

    public void setCurrency(String str) {
        this.currency = str;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }

    public String getCurrency() {
        return this.currency;
    }
}

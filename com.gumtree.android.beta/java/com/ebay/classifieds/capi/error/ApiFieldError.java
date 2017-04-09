package com.ebay.classifieds.capi.error;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "api-field-error", strict = false)
public class ApiFieldError {
    @Element(name = "api-debug-errors", required = false)
    private ApiDebugErrors apiDebugErrors;
    @Element(name = "api-errors", required = false)
    private ApiErrors apiErrors;
    @Element(name = "field-xpath", required = false)
    private String fieldXPath;

    public String getFieldXPath() {
        return this.fieldXPath;
    }

    public ApiErrors getApiErrors() {
        return this.apiErrors;
    }

    public ApiDebugErrors getApiDebugErrors() {
        return this.apiDebugErrors;
    }
}

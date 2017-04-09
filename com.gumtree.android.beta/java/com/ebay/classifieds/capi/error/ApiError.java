package com.ebay.classifieds.capi.error;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "api-error", strict = false)
public class ApiError {
    @Element(name = "message", required = false)
    private String message;

    public String getMessage() {
        return this.message;
    }
}

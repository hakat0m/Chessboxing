package com.ebay.classifieds.capi.error;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "api-debug-error", strict = false)
public class ApiDebugError {
    @Element(name = "debug-message", required = false)
    private String message;

    public String getMessage() {
        return this.message;
    }
}

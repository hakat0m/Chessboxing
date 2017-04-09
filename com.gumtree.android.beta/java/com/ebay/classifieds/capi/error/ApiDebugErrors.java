package com.ebay.classifieds.capi.error;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "api-debug-errors", strict = false)
public class ApiDebugErrors {
    @ElementList(entry = "api-debug-error", inline = true, required = false)
    private ArrayList<ApiDebugError> errors;

    public List<ApiDebugError> getErrors() {
        return this.errors;
    }
}

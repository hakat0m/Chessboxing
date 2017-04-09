package com.ebay.classifieds.capi.error;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "api-field-errors", strict = false)
public class ApiFieldErrors {
    @ElementList(entry = "api-field-error", inline = true, required = false)
    private ArrayList<ApiFieldError> errors;

    public List<ApiFieldError> getErrors() {
        return this.errors;
    }
}

package com.ebay.classifieds.capi.error;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "api-errors", strict = false)
public class ApiErrors {
    @ElementList(entry = "api-error", inline = true, required = false)
    private ArrayList<ApiError> errors;

    public List<ApiError> getErrors() {
        return this.errors;
    }

    public boolean hasErrors() {
        if (this.errors == null || this.errors.size() == 0) {
            return false;
        }
        return true;
    }
}

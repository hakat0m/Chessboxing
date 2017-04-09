package com.ebay.classifieds.capi.error;

import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "api-base-error", strict = false)
@Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public class ApiBaseError implements GenericError {
    @Element(name = "api-debug-errors", required = false)
    private ApiDebugErrors apiDebugErrors;
    @Element(name = "api-errors", required = false)
    private ApiErrors apiErrors;
    @Element(name = "api-field-errors", required = false)
    private ApiFieldErrors apiFieldErrors;
    @Attribute(name = "http-status-code", required = false)
    private String statusCode;

    public ApiFieldErrors getFieldErrors() {
        return this.apiFieldErrors;
    }

    public ApiDebugErrors getDebugErrors() {
        return this.apiDebugErrors;
    }

    public ApiErrors getApiErrors() {
        return this.apiErrors;
    }

    public boolean hasApiErrors() {
        return this.apiErrors != null;
    }

    public boolean hasFieldErrors() {
        return this.apiFieldErrors != null;
    }

    public boolean hasDebugErrors() {
        return this.apiDebugErrors != null;
    }

    public int getStatusCode() {
        try {
            return Integer.parseInt(this.statusCode);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int getErrorType() {
        return 1;
    }

    public String getMessage() {
        List errors;
        if (hasApiErrors() && getApiErrors().hasErrors()) {
            errors = getApiErrors().getErrors();
            if (errors != null) {
                return ((ApiError) errors.get(0)).getMessage();
            }
        }
        if (hasFieldErrors()) {
            errors = getFieldErrors().getErrors();
            if (errors != null && errors.size() > 0) {
                ApiFieldError apiFieldError = (ApiFieldError) errors.get(0);
                if (apiFieldError != null) {
                    ApiErrors apiErrors = apiFieldError.getApiErrors();
                    if (apiErrors != null) {
                        errors = apiErrors.getErrors();
                        if (errors != null && errors.size() > 0) {
                            ApiError apiError = (ApiError) errors.get(0);
                            if (apiError != null) {
                                return apiError.getMessage();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean isAuthorizationError() {
        return Integer.parseInt(this.statusCode) == 401;
    }
}

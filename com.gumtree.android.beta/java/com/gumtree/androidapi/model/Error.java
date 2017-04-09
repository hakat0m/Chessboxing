package com.gumtree.androidapi.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Error {
    @SerializedName("errors")
    private List<CallistoError> errors = new ArrayList();

    public Error errors(List<CallistoError> list) {
        this.errors = list;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public List<CallistoError> getErrors() {
        return this.errors;
    }

    public void setErrors(List<CallistoError> list) {
        this.errors = list;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.errors, ((Error) obj).errors);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.errors});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("class Error {\n");
        stringBuilder.append("    errors: ").append(toIndentedString(this.errors)).append("\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private String toIndentedString(Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.toString().replace("\n", "\n    ");
    }
}

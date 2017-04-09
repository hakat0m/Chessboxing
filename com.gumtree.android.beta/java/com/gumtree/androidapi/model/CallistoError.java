package com.gumtree.androidapi.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

public class CallistoError {
    @SerializedName("errorCode")
    private String errorCode = null;
    @SerializedName("errorDetail")
    private String errorDetail = null;
    @SerializedName("userMessage")
    private String userMessage = null;

    public CallistoError errorCode(String str) {
        this.errorCode = str;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String str) {
        this.errorCode = str;
    }

    public CallistoError errorDetail(String str) {
        this.errorDetail = str;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public String getErrorDetail() {
        return this.errorDetail;
    }

    public void setErrorDetail(String str) {
        this.errorDetail = str;
    }

    public CallistoError userMessage(String str) {
        this.userMessage = str;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public String getUserMessage() {
        return this.userMessage;
    }

    public void setUserMessage(String str) {
        this.userMessage = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CallistoError callistoError = (CallistoError) obj;
        if (Objects.equals(this.errorCode, callistoError.errorCode) && Objects.equals(this.errorDetail, callistoError.errorDetail) && Objects.equals(this.userMessage, callistoError.userMessage)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.errorCode, this.errorDetail, this.userMessage});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("class CallistoError {\n");
        stringBuilder.append("    errorCode: ").append(toIndentedString(this.errorCode)).append("\n");
        stringBuilder.append("    errorDetail: ").append(toIndentedString(this.errorDetail)).append("\n");
        stringBuilder.append("    userMessage: ").append(toIndentedString(this.userMessage)).append("\n");
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

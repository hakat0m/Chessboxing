package com.gumtree.androidapi.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

public class Suggestion {
    @SerializedName("categoryItem")
    private CategoryItem categoryItem = null;
    @SerializedName("suggestedKeyword")
    private String suggestedKeyword = null;
    @SerializedName("suggestedTrackingId")
    private String suggestedTrackingId = null;

    public Suggestion suggestedKeyword(String str) {
        this.suggestedKeyword = str;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public String getSuggestedKeyword() {
        return this.suggestedKeyword;
    }

    public void setSuggestedKeyword(String str) {
        this.suggestedKeyword = str;
    }

    public Suggestion suggestedTrackingId(String str) {
        this.suggestedTrackingId = str;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public String getSuggestedTrackingId() {
        return this.suggestedTrackingId;
    }

    public void setSuggestedTrackingId(String str) {
        this.suggestedTrackingId = str;
    }

    public Suggestion categoryItem(CategoryItem categoryItem) {
        this.categoryItem = categoryItem;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public CategoryItem getCategoryItem() {
        return this.categoryItem;
    }

    public void setCategoryItem(CategoryItem categoryItem) {
        this.categoryItem = categoryItem;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Suggestion suggestion = (Suggestion) obj;
        if (Objects.equals(this.suggestedKeyword, suggestion.suggestedKeyword) && Objects.equals(this.suggestedTrackingId, suggestion.suggestedTrackingId) && Objects.equals(this.categoryItem, suggestion.categoryItem)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.suggestedKeyword, this.suggestedTrackingId, this.categoryItem});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("class Suggestion {\n");
        stringBuilder.append("    suggestedKeyword: ").append(toIndentedString(this.suggestedKeyword)).append("\n");
        stringBuilder.append("    suggestedTrackingId: ").append(toIndentedString(this.suggestedTrackingId)).append("\n");
        stringBuilder.append("    categoryItem: ").append(toIndentedString(this.categoryItem)).append("\n");
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

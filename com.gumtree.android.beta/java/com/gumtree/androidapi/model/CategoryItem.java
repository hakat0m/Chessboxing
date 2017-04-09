package com.gumtree.androidapi.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryItem {
    @SerializedName("categories")
    private List<CategoryItem> categories = new ArrayList();
    @SerializedName("childrenCount")
    private Long childrenCount = null;
    @SerializedName("id")
    private Long id = null;
    @SerializedName("idName")
    private String idName = null;
    @SerializedName("localizedName")
    private String localizedName = null;
    @SerializedName("parent")
    private CategoryItem parent = null;
    @SerializedName("picture")
    private String picture = null;

    public CategoryItem id(Long l) {
        this.id = l;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public Long getId() {
        return this.id;
    }

    public void setId(Long l) {
        this.id = l;
    }

    public CategoryItem parent(CategoryItem categoryItem) {
        this.parent = categoryItem;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public CategoryItem getParent() {
        return this.parent;
    }

    public void setParent(CategoryItem categoryItem) {
        this.parent = categoryItem;
    }

    public CategoryItem categories(List<CategoryItem> list) {
        this.categories = list;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public List<CategoryItem> getCategories() {
        return this.categories;
    }

    public void setCategories(List<CategoryItem> list) {
        this.categories = list;
    }

    public CategoryItem picture(String str) {
        this.picture = str;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String str) {
        this.picture = str;
    }

    public CategoryItem idName(String str) {
        this.idName = str;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public String getIdName() {
        return this.idName;
    }

    public void setIdName(String str) {
        this.idName = str;
    }

    public CategoryItem localizedName(String str) {
        this.localizedName = str;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public String getLocalizedName() {
        return this.localizedName;
    }

    public void setLocalizedName(String str) {
        this.localizedName = str;
    }

    public CategoryItem childrenCount(Long l) {
        this.childrenCount = l;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public Long getChildrenCount() {
        return this.childrenCount;
    }

    public void setChildrenCount(Long l) {
        this.childrenCount = l;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CategoryItem categoryItem = (CategoryItem) obj;
        if (Objects.equals(this.id, categoryItem.id) && Objects.equals(this.parent, categoryItem.parent) && Objects.equals(this.categories, categoryItem.categories) && Objects.equals(this.picture, categoryItem.picture) && Objects.equals(this.idName, categoryItem.idName) && Objects.equals(this.localizedName, categoryItem.localizedName) && Objects.equals(this.childrenCount, categoryItem.childrenCount)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.parent, this.categories, this.picture, this.idName, this.localizedName, this.childrenCount});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("class CategoryItem {\n");
        stringBuilder.append("    id: ").append(toIndentedString(this.id)).append("\n");
        stringBuilder.append("    parent: ").append(toIndentedString(this.parent)).append("\n");
        stringBuilder.append("    categories: ").append(toIndentedString(this.categories)).append("\n");
        stringBuilder.append("    picture: ").append(toIndentedString(this.picture)).append("\n");
        stringBuilder.append("    idName: ").append(toIndentedString(this.idName)).append("\n");
        stringBuilder.append("    localizedName: ").append(toIndentedString(this.localizedName)).append("\n");
        stringBuilder.append("    childrenCount: ").append(toIndentedString(this.childrenCount)).append("\n");
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

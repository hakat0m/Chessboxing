package com.gumtree.android.ad.search.models;

import java.beans.ConstructorProperties;

public class SuggestionItem {
    private Long categoryId;
    private String categoryLocalizedName;
    private String keyword;

    @ConstructorProperties({"keyword", "categoryId", "categoryLocalizedName"})
    public SuggestionItem(String str, Long l, String str2) {
        this.keyword = str;
        this.categoryId = l;
        this.categoryLocalizedName = str2;
    }

    public static SuggestionItemBuilder builder() {
        return new SuggestionItemBuilder();
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof SuggestionItem;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SuggestionItem)) {
            return false;
        }
        SuggestionItem suggestionItem = (SuggestionItem) obj;
        if (!suggestionItem.canEqual(this)) {
            return false;
        }
        String keyword = getKeyword();
        String keyword2 = suggestionItem.getKeyword();
        if (keyword != null ? !keyword.equals(keyword2) : keyword2 != null) {
            return false;
        }
        Long categoryId = getCategoryId();
        Long categoryId2 = suggestionItem.getCategoryId();
        if (categoryId != null ? !categoryId.equals(categoryId2) : categoryId2 != null) {
            return false;
        }
        keyword = getCategoryLocalizedName();
        keyword2 = suggestionItem.getCategoryLocalizedName();
        if (keyword == null) {
            if (keyword2 == null) {
                return true;
            }
        } else if (keyword.equals(keyword2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String keyword = getKeyword();
        int hashCode = (keyword == null ? 43 : keyword.hashCode()) + 59;
        Long categoryId = getCategoryId();
        hashCode = (categoryId == null ? 43 : categoryId.hashCode()) + (hashCode * 59);
        String categoryLocalizedName = getCategoryLocalizedName();
        hashCode *= 59;
        if (categoryLocalizedName != null) {
            i = categoryLocalizedName.hashCode();
        }
        return hashCode + i;
    }

    public void setCategoryId(Long l) {
        this.categoryId = l;
    }

    public void setCategoryLocalizedName(String str) {
        this.categoryLocalizedName = str;
    }

    public void setKeyword(String str) {
        this.keyword = str;
    }

    public String toString() {
        return "SuggestionItem(keyword=" + getKeyword() + ", categoryId=" + getCategoryId() + ", categoryLocalizedName=" + getCategoryLocalizedName() + ")";
    }

    public String getKeyword() {
        return this.keyword;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public String getCategoryLocalizedName() {
        return this.categoryLocalizedName;
    }
}

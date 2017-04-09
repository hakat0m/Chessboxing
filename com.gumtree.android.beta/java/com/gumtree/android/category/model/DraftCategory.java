package com.gumtree.android.category.model;

import java.beans.ConstructorProperties;

public class DraftCategory {
    private static final int INITIAL_ODD_NUMBER = 17;
    private static final int MULTIPLIER_ODD_NUMBER = 37;
    private String categoriesTree;
    private String id;
    private String idName;
    private boolean leaf;
    private String localisedName;
    private String name;
    private String path;

    public void setCategoriesTree(String str) {
        this.categoriesTree = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setIdName(String str) {
        this.idName = str;
    }

    public void setLeaf(boolean z) {
        this.leaf = z;
    }

    public void setLocalisedName(String str) {
        this.localisedName = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String toString() {
        return "DraftCategory(id=" + getId() + ", name=" + getName() + ", idName=" + getIdName() + ", localisedName=" + getLocalisedName() + ", categoriesTree=" + getCategoriesTree() + ", path=" + getPath() + ", leaf=" + isLeaf() + ")";
    }

    public static DraftCategoryBuilder builder() {
        return new DraftCategoryBuilder();
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof DraftCategory;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DraftCategory)) {
            return false;
        }
        DraftCategory draftCategory = (DraftCategory) obj;
        if (!draftCategory.canEqual(this)) {
            return false;
        }
        String id = getId();
        String id2 = draftCategory.getId();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getName();
        id2 = draftCategory.getName();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getIdName();
        id2 = draftCategory.getIdName();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getLocalisedName();
        id2 = draftCategory.getLocalisedName();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getCategoriesTree();
        id2 = draftCategory.getCategoriesTree();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getPath();
        id2 = draftCategory.getPath();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        return isLeaf() == draftCategory.isLeaf();
    }

    public int hashCode() {
        int i = 43;
        String id = getId();
        int hashCode = (id == null ? 43 : id.hashCode()) + 59;
        String name = getName();
        hashCode = (name == null ? 43 : name.hashCode()) + (hashCode * 59);
        name = getIdName();
        hashCode = (name == null ? 43 : name.hashCode()) + (hashCode * 59);
        name = getLocalisedName();
        hashCode = (name == null ? 43 : name.hashCode()) + (hashCode * 59);
        name = getCategoriesTree();
        hashCode = (name == null ? 43 : name.hashCode()) + (hashCode * 59);
        name = getPath();
        hashCode *= 59;
        if (name != null) {
            i = name.hashCode();
        }
        return (isLeaf() ? 79 : 97) + ((hashCode + i) * 59);
    }

    @ConstructorProperties({"id", "name", "idName", "localisedName", "categoriesTree", "path", "leaf"})
    public DraftCategory(String str, String str2, String str3, String str4, String str5, String str6, boolean z) {
        this.id = str;
        this.name = str2;
        this.idName = str3;
        this.localisedName = str4;
        this.categoriesTree = str5;
        this.path = str6;
        this.leaf = z;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getIdName() {
        return this.idName;
    }

    public String getLocalisedName() {
        return this.localisedName;
    }

    public String getCategoriesTree() {
        return this.categoriesTree;
    }

    public String getPath() {
        return this.path;
    }

    public boolean isLeaf() {
        return this.leaf;
    }
}

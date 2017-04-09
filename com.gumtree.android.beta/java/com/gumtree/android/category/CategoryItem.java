package com.gumtree.android.category;

import android.database.Cursor;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.executor.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryItem implements TreeNode {
    private static final String MINUS = "-";
    private static final String SEMICOLON = ";";
    private static final String SEPERATOR = "/";
    private List<CategoryItem> categories;
    private long childrenCount;
    private int hasCVPosting;
    private long id;
    private String idName;
    private String localizedName;
    private CategoryItem parent;
    private StringBuilder pathIdNames;
    private StringBuilder pathIds;
    private StringBuilder pathLocalizedName;
    private String picture;

    public CategoryItem() {
        this.childrenCount = -1;
        this.categories = new ArrayList();
        this.pathIds = new StringBuilder();
        this.pathIdNames = new StringBuilder();
        this.pathLocalizedName = new StringBuilder();
    }

    public CategoryItem(Cursor cursor) {
        this.childrenCount = -1;
        this.id = cursor.getLong(cursor.getColumnIndex("_id"));
        initValues(cursor);
    }

    private void initValues(Cursor cursor) {
        boolean z = true;
        this.parent = new CategoryItem();
        this.parent.setId(cursor.getLong(cursor.getColumnIndex("parent_id")));
        this.parent.setLocalizedName(cursor.getString(cursor.getColumnIndex("parent_name")));
        this.parent.setIdName(cursor.getString(cursor.getColumnIndex("parent_value")));
        CategoryItem categoryItem = this.parent;
        if (cursor.getInt(cursor.getColumnIndex("has_cv_posting")) != 1) {
            z = false;
        }
        categoryItem.setHasCVPosting(z);
        setPathFullIds(cursor.getString(cursor.getColumnIndex("path")));
        setPathFullLocalizedName(cursor.getString(cursor.getColumnIndex("path_names")));
        setPathFullIdNames(cursor.getString(cursor.getColumnIndex("path_values")));
        setChildrenCount(cursor.getLong(cursor.getColumnIndex("children_count")));
        setIdName(cursor.getString(cursor.getColumnIndex("value_raw")));
        setLocalizedName(cursor.getString(cursor.getColumnIndex("value")));
    }

    public String getLocalizedName() {
        return getName();
    }

    public void setLocalizedName(String str) {
        this.localizedName = str;
    }

    public Long getId() {
        return Long.valueOf(this.id);
    }

    public void setId(long j) {
        this.id = j;
    }

    public String getName() {
        return this.localizedName;
    }

    public String[] getPath() {
        return this.pathIds.toString().split(this.pathIds.toString().contains(SEPERATOR) ? SEPERATOR : SEMICOLON);
    }

    public String[] getPathNames() {
        return this.pathLocalizedName.toString().split(this.pathLocalizedName.toString().contains(SEPERATOR) ? SEPERATOR : SEMICOLON);
    }

    public CategoryItem getParent() {
        return this.parent;
    }

    public void setParent(CategoryItem categoryItem) {
        this.parent = categoryItem;
    }

    public StringBuilder getPathIds() {
        return this.pathIds;
    }

    public void setPathIds(String str) {
        if (this.pathIds.length() > 0) {
            this.pathIds.append(SEMICOLON);
        }
        this.pathIds.append(str);
    }

    public StringBuilder getPathLocalizedName() {
        return this.pathLocalizedName;
    }

    public void setPathLocalizedName(String str) {
        if (this.pathLocalizedName.length() > 0) {
            this.pathLocalizedName.append(SEMICOLON);
        }
        this.pathLocalizedName.append(str);
    }

    public StringBuilder getPathIdNames() {
        return this.pathIdNames;
    }

    public void setPathIdNames(String str) {
        if (this.pathIdNames.length() > 0) {
            this.pathIdNames.append(SEMICOLON);
        }
        this.pathIdNames.append(str);
    }

    public List<CategoryItem> getCategories() {
        return this.categories;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String str) {
        this.picture = str;
    }

    public String getIdName() {
        return this.idName;
    }

    public void setIdName(String str) {
        this.idName = str;
    }

    public String getPathInString() {
        StringBuilder stringBuilder = new StringBuilder();
        String str = " > ";
        for (int i = 1; i < getPathNames().length; i++) {
            if (i == getPathNames().length - 1) {
                stringBuilder.append("<b>").append(getPathNames()[i]).append("</b>");
            } else {
                stringBuilder.append(getPathNames()[i]).append(str);
            }
        }
        return stringBuilder.toString();
    }

    public String getPathNamesInString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("all;");
        String str = SEMICOLON;
        for (int i = 1; i < getPathNames().length; i++) {
            String toLowerCase = getPathNames()[i].replace(",", BuildConfig.FLAVOR).replace(" & ", MINUS).replace(" ", MINUS).toLowerCase(Locale.getDefault());
            if (i == getPathNames().length - 1) {
                stringBuilder.append(BuildConfig.FLAVOR).append(toLowerCase);
            } else {
                stringBuilder.append(toLowerCase).append(str);
            }
        }
        return stringBuilder.toString();
    }

    public void addCategory(List<CategoryItem> list) {
        this.categories.addAll(list);
    }

    public long getChildrenCount() {
        if (this.childrenCount > 0) {
            return this.childrenCount;
        }
        return (long) this.categories.size();
    }

    void setChildrenCount(long j) {
        this.childrenCount = j;
    }

    void setPathFullIds(String str) {
        this.pathIds = new StringBuilder(str);
    }

    public void setPathFullLocalizedName(String str) {
        this.pathLocalizedName = new StringBuilder(str);
    }

    void setPathFullIdNames(String str) {
        this.pathIdNames = new StringBuilder(str);
    }

    public void setHasCVPosting(boolean z) {
        this.hasCVPosting = z ? 1 : 0;
    }

    public int getHasCVPosting() {
        return this.hasCVPosting;
    }
}

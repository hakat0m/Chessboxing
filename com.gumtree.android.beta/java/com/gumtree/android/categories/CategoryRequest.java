package com.gumtree.android.categories;

import android.content.Context;
import android.database.Cursor;
import com.ebay.classifieds.capi.executor.Request;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.android.category.CategoryItem;
import com.gumtree.android.model.Categories;
import java.util.ArrayList;
import java.util.List;

public class CategoryRequest implements Request<List<CategoryItem>> {
    private static final String[] PROJECTION = new String[]{"_id", "parent_id", "parent_name", "parent_value", "value", "children_count", "path_names", "path", "path_values", "value_raw", "has_cv_posting"};
    private final long categoryId;
    private final Context context;
    private List<CategoryItem> items = new ArrayList();

    public CategoryRequest(long j, Context context) {
        this.categoryId = j;
        this.context = context.getApplicationContext();
    }

    public Result<List<CategoryItem>> execute() {
        CategoryItem buildCategoryItem = buildCategoryItem(this.categoryId);
        if (buildCategoryItem == null || buildCategoryItem.getPath() == null) {
            return new Result(this.items);
        }
        for (String parseLong : buildCategoryItem.getPath()) {
            long parseLong2 = Long.parseLong(parseLong);
            if (parseLong2 != this.categoryId || parseLong2 == 1) {
                add(buildCategoryItem(parseLong2));
            }
        }
        if (this.categoryId != 1 && buildCategoryItem.getPath().length <= 2) {
            add(buildCategoryItem(this.categoryId));
        }
        return new Result(this.items);
    }

    private void add(CategoryItem categoryItem) {
        if (categoryItem != null) {
            this.items.add(categoryItem);
        }
    }

    private CategoryItem buildCategoryItem(long j) {
        CategoryItem categoryItem = null;
        Cursor query = this.context.getContentResolver().query(Categories.URI, PROJECTION, "_id=?", new String[]{String.valueOf(j)}, null);
        if (query.moveToFirst()) {
            categoryItem = new CategoryItem(query);
        }
        query.close();
        return categoryItem;
    }
}

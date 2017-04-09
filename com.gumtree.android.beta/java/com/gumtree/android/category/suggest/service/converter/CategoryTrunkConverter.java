package com.gumtree.android.category.suggest.service.converter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.lang3.Validate;

public class CategoryTrunkConverter implements CategoryTreeConverter {
    @NonNull
    private final DefaultCategoryTreeConverter utility;

    @Inject
    public CategoryTrunkConverter(@NonNull DefaultCategoryTreeConverter defaultCategoryTreeConverter) {
        this.utility = (DefaultCategoryTreeConverter) Validate.notNull(defaultCategoryTreeConverter);
    }

    @Nullable
    public String formatCategoryTree(@Nullable String str, @NonNull String str2) {
        if (str == null) {
            return null;
        }
        List extractCategories = this.utility.extractCategories(str);
        return this.utility.format(extractCategories.subList(0, extractCategories.size() - 1), str2);
    }
}

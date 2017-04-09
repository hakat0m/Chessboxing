package com.gumtree.android.category.suggest.service.converter;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.categories.Category;
import com.gumtree.android.category.model.DraftCategory;
import javax.inject.Inject;
import org.apache.commons.lang3.Validate;

public class CategoryConverter {
    private final CategoryTreeConverter categoryTreeConverter;

    @Inject
    public CategoryConverter(@NonNull CategoryTreeConverter categoryTreeConverter) {
        this.categoryTreeConverter = (CategoryTreeConverter) Validate.notNull(categoryTreeConverter);
    }

    public DraftCategory apiToModel(Category category) {
        return DraftCategory.builder().id(String.valueOf(category.getId())).localisedName(category.getLocalizedName()).categoriesTree(category.getCategoriesTree()).path(this.categoryTreeConverter.formatCategoryTree(category.getCategoriesTree(), ";")).build();
    }
}

package com.gumtree.android.category.suggest.service.converter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface CategoryTreeConverter {
    @Nullable
    String formatCategoryTree(@Nullable String str, @NonNull String str2);
}

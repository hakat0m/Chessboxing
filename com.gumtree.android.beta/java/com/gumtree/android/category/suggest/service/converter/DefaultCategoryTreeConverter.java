package com.gumtree.android.category.suggest.service.converter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public final class DefaultCategoryTreeConverter implements CategoryTreeConverter {
    @Nullable
    public String formatCategoryTree(@Nullable String str, @NonNull String str2) {
        if (str == null) {
            return null;
        }
        return format(extractCategories(str), str2);
    }

    @NonNull
    public String format(Iterable<String> iterable, String str) {
        return StringUtils.join(iterable, str);
    }

    @NonNull
    public List<String> extractCategories(@Nullable String str) {
        return Arrays.asList(StringUtils.defaultString(str).split(",(?! )"));
    }
}

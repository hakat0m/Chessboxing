package com.gumtree.android.category.suggest.service;

import com.ebay.classifieds.capi.categories.Categories;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiCategoryService$$Lambda$3 implements Func1 {
    private static final ApiCategoryService$$Lambda$3 instance = new ApiCategoryService$$Lambda$3();

    private ApiCategoryService$$Lambda$3() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ((Categories) obj).getCategories();
    }
}

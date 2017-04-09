package com.gumtree.android.category.suggest.service;

import com.ebay.classifieds.capi.categories.Category;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiCategoryService$$Lambda$6 implements Func1 {
    private final ApiCategoryService arg$1;

    private ApiCategoryService$$Lambda$6(ApiCategoryService apiCategoryService) {
        this.arg$1 = apiCategoryService;
    }

    public static Func1 lambdaFactory$(ApiCategoryService apiCategoryService) {
        return new ApiCategoryService$$Lambda$6(apiCategoryService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$getCategorySuggestions$5((Category) obj);
    }
}

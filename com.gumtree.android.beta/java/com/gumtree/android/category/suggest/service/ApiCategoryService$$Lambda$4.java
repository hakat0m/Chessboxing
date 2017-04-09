package com.gumtree.android.category.suggest.service;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func1;

final /* synthetic */ class ApiCategoryService$$Lambda$4 implements Func1 {
    private static final ApiCategoryService$$Lambda$4 instance = new ApiCategoryService$$Lambda$4();

    private ApiCategoryService$$Lambda$4() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ApiCategoryService.lambda$getCategorySuggestions$3((List) obj);
    }
}

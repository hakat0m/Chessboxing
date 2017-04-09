package com.gumtree.android.category.suggest.service;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

final /* synthetic */ class ApiCategoryService$$Lambda$5 implements Func1 {
    private static final ApiCategoryService$$Lambda$5 instance = new ApiCategoryService$$Lambda$5();

    private ApiCategoryService$$Lambda$5() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return Observable.from((List) obj);
    }
}

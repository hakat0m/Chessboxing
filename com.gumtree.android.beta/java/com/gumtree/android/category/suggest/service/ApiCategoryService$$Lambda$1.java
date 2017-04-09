package com.gumtree.android.category.suggest.service;

import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.lang3.StringUtils;
import rx.functions.Func1;

final /* synthetic */ class ApiCategoryService$$Lambda$1 implements Func1 {
    private static final ApiCategoryService$$Lambda$1 instance = new ApiCategoryService$$Lambda$1();

    private ApiCategoryService$$Lambda$1() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return Boolean.valueOf(StringUtils.isNotBlank((String) obj));
    }
}

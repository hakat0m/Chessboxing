package com.gumtree.android.ad.treebay.services.item;

import com.gumtree.android.api.treebay.ItemSummary;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiTreebayAdDetailsService$$Lambda$1 implements Func1 {
    private final ApiTreebayAdDetailsService arg$1;
    private final String arg$2;

    private ApiTreebayAdDetailsService$$Lambda$1(ApiTreebayAdDetailsService apiTreebayAdDetailsService, String str) {
        this.arg$1 = apiTreebayAdDetailsService;
        this.arg$2 = str;
    }

    public static Func1 lambdaFactory$(ApiTreebayAdDetailsService apiTreebayAdDetailsService, String str) {
        return new ApiTreebayAdDetailsService$$Lambda$1(apiTreebayAdDetailsService, str);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$getTreebayAdDetails$0(this.arg$2, (ItemSummary) obj);
    }
}

package com.gumtree.android.priceinfo;

import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.postad.DraftLocation;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func0;

final /* synthetic */ class ApiPriceInfoService$$Lambda$1 implements Func0 {
    private final ApiPriceInfoService arg$1;
    private final String arg$2;
    private final DraftCategory arg$3;
    private final DraftLocation arg$4;
    private final String arg$5;

    private ApiPriceInfoService$$Lambda$1(ApiPriceInfoService apiPriceInfoService, String str, DraftCategory draftCategory, DraftLocation draftLocation, String str2) {
        this.arg$1 = apiPriceInfoService;
        this.arg$2 = str;
        this.arg$3 = draftCategory;
        this.arg$4 = draftLocation;
        this.arg$5 = str2;
    }

    public static Func0 lambdaFactory$(ApiPriceInfoService apiPriceInfoService, String str, DraftCategory draftCategory, DraftLocation draftLocation, String str2) {
        return new ApiPriceInfoService$$Lambda$1(apiPriceInfoService, str, draftCategory, draftLocation, str2);
    }

    @Hidden
    public Object call() {
        return this.arg$1.lambda$getFeatureInfo$0(this.arg$2, this.arg$3, this.arg$4, this.arg$5);
    }
}

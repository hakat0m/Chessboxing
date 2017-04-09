package com.gumtree.android.metadata.service;

import com.ebay.classifieds.capi.metadata.Metadata;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiMetadataService$$Lambda$1 implements Func1 {
    private final ApiMetadataService arg$1;
    private final String arg$2;

    private ApiMetadataService$$Lambda$1(ApiMetadataService apiMetadataService, String str) {
        this.arg$1 = apiMetadataService;
        this.arg$2 = str;
    }

    public static Func1 lambdaFactory$(ApiMetadataService apiMetadataService, String str) {
        return new ApiMetadataService$$Lambda$1(apiMetadataService, str);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$getCategoryMetadata$0(this.arg$2, (Metadata) obj);
    }
}

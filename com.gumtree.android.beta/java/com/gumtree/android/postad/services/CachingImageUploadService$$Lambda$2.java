package com.gumtree.android.postad.services;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class CachingImageUploadService$$Lambda$2 implements Action1 {
    private final CachingImageUploadService arg$1;

    private CachingImageUploadService$$Lambda$2(CachingImageUploadService cachingImageUploadService) {
        this.arg$1 = cachingImageUploadService;
    }

    public static Action1 lambdaFactory$(CachingImageUploadService cachingImageUploadService) {
        return new CachingImageUploadService$$Lambda$2(cachingImageUploadService);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$handle$1((Upload) obj);
    }
}

package com.gumtree.android.postad.services;

import java.io.File;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class CachingImageUploadService$$Lambda$1 implements Func1 {
    private final CachingImageUploadService arg$1;

    private CachingImageUploadService$$Lambda$1(CachingImageUploadService cachingImageUploadService) {
        this.arg$1 = cachingImageUploadService;
    }

    public static Func1 lambdaFactory$(CachingImageUploadService cachingImageUploadService) {
        return new CachingImageUploadService$$Lambda$1(cachingImageUploadService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$upload$0((File) obj);
    }
}

package com.gumtree.android.postad.services;

import java.io.File;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class CapiImageUploadService$$Lambda$1 implements Func1 {
    private final CapiImageUploadService arg$1;

    private CapiImageUploadService$$Lambda$1(CapiImageUploadService capiImageUploadService) {
        this.arg$1 = capiImageUploadService;
    }

    public static Func1 lambdaFactory$(CapiImageUploadService capiImageUploadService) {
        return new CapiImageUploadService$$Lambda$1(capiImageUploadService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$upload$0((File) obj);
    }
}

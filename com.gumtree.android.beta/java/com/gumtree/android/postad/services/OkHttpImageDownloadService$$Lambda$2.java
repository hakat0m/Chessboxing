package com.gumtree.android.postad.services;

import java.lang.invoke.LambdaForm.Hidden;
import java.net.URL;
import rx.functions.Func1;

final /* synthetic */ class OkHttpImageDownloadService$$Lambda$2 implements Func1 {
    private final OkHttpImageDownloadService arg$1;

    private OkHttpImageDownloadService$$Lambda$2(OkHttpImageDownloadService okHttpImageDownloadService) {
        this.arg$1 = okHttpImageDownloadService;
    }

    public static Func1 lambdaFactory$(OkHttpImageDownloadService okHttpImageDownloadService) {
        return new OkHttpImageDownloadService$$Lambda$2(okHttpImageDownloadService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$null$0((URL) obj);
    }
}

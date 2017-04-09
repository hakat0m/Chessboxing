package com.gumtree.android.postad.services;

import java.lang.invoke.LambdaForm.Hidden;
import rx.Observable;
import rx.functions.Func0;

final /* synthetic */ class OkHttpImageDownloadService$$Lambda$1 implements Func0 {
    private final OkHttpImageDownloadService arg$1;
    private final Observable arg$2;

    private OkHttpImageDownloadService$$Lambda$1(OkHttpImageDownloadService okHttpImageDownloadService, Observable observable) {
        this.arg$1 = okHttpImageDownloadService;
        this.arg$2 = observable;
    }

    public static Func0 lambdaFactory$(OkHttpImageDownloadService okHttpImageDownloadService, Observable observable) {
        return new OkHttpImageDownloadService$$Lambda$1(okHttpImageDownloadService, observable);
    }

    @Hidden
    public Object call() {
        return this.arg$1.lambda$download$1(this.arg$2);
    }
}

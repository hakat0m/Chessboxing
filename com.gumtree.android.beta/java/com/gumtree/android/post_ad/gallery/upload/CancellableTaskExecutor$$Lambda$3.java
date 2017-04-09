package com.gumtree.android.post_ad.gallery.upload;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CancellableTaskExecutor$$Lambda$3 implements Visitor {
    private final String arg$1;

    private CancellableTaskExecutor$$Lambda$3(String str) {
        this.arg$1 = str;
    }

    public static Visitor lambdaFactory$(String str) {
        return new CancellableTaskExecutor$$Lambda$3(str);
    }

    @Hidden
    public void visit(Object obj) {
        CancellableTaskExecutor.lambda$cancelBy$2(this.arg$1, (CancellableTask) obj);
    }
}

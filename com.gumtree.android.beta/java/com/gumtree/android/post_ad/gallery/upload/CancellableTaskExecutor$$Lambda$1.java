package com.gumtree.android.post_ad.gallery.upload;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CancellableTaskExecutor$$Lambda$1 implements Runnable {
    private final CancellableTaskExecutor arg$1;

    private CancellableTaskExecutor$$Lambda$1(CancellableTaskExecutor cancellableTaskExecutor) {
        this.arg$1 = cancellableTaskExecutor;
    }

    public static Runnable lambdaFactory$(CancellableTaskExecutor cancellableTaskExecutor) {
        return new CancellableTaskExecutor$$Lambda$1(cancellableTaskExecutor);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$appendScheduleNextTaskInstance$0();
    }
}

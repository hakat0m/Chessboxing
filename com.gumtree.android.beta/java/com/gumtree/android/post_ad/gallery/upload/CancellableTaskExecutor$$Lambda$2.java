package com.gumtree.android.post_ad.gallery.upload;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CancellableTaskExecutor$$Lambda$2 implements Runnable {
    private final CancellableTaskExecutor arg$1;
    private final CancellableTask arg$2;

    private CancellableTaskExecutor$$Lambda$2(CancellableTaskExecutor cancellableTaskExecutor, CancellableTask cancellableTask) {
        this.arg$1 = cancellableTaskExecutor;
        this.arg$2 = cancellableTask;
    }

    public static Runnable lambdaFactory$(CancellableTaskExecutor cancellableTaskExecutor, CancellableTask cancellableTask) {
        return new CancellableTaskExecutor$$Lambda$2(cancellableTaskExecutor, cancellableTask);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$scheduleNextTask$1(this.arg$2);
    }
}

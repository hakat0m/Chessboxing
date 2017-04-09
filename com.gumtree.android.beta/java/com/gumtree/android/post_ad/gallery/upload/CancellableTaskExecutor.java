package com.gumtree.android.post_ad.gallery.upload;

import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CancellableTaskExecutor<T extends CancellableTask> {
    private final ExecutorService executorService;
    private final PendingTasks<T> pendingIntentTasks = new PendingTasks();

    public CancellableTaskExecutor(int i) {
        this.executorService = Executors.newFixedThreadPool(i);
        appendScheduleNextTaskInstance();
    }

    private void appendScheduleNextTaskInstance() {
        this.executorService.execute(CancellableTaskExecutor$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$appendScheduleNextTaskInstance$0() {
        scheduleNextInactiveTask();
    }

    private void scheduleNextInactiveTask() {
        try {
            scheduleNextTask(this.pendingIntentTasks.activateNextTask());
            appendScheduleNextTaskInstance();
        } catch (Throwable e) {
            CrashlyticsHelper.getInstance().catchGumtreeException(e);
        }
    }

    private void scheduleNextTask(T t) {
        this.executorService.execute(CancellableTaskExecutor$$Lambda$2.lambdaFactory$(this, t));
    }

    /* synthetic */ void lambda$scheduleNextTask$1(CancellableTask cancellableTask) {
        try {
            cancellableTask.execute();
        } finally {
            this.pendingIntentTasks.remove(cancellableTask);
        }
    }

    public void schedule(T t) {
        this.pendingIntentTasks.schedule(t);
    }

    public void cancelBy(String str) {
        this.pendingIntentTasks.visit(CancellableTaskExecutor$$Lambda$3.lambdaFactory$(str));
    }

    static /* synthetic */ void lambda$cancelBy$2(String str, CancellableTask cancellableTask) {
        if (str.equals(cancellableTask.getCancelId())) {
            cancellableTask.cancel();
        }
    }
}

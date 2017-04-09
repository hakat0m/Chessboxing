package com.gumtree.android.post_ad.gallery.upload;

import java.util.ArrayList;
import java.util.List;

class PendingTasks<T extends CancellableTask> {
    private final List<T> tasks = new ArrayList();

    PendingTasks() {
    }

    public synchronized T activateNextTask() throws InterruptedException {
        T findNextInactiveIntent;
        while (findNextInactiveIntent() == null) {
            wait();
        }
        findNextInactiveIntent = findNextInactiveIntent();
        findNextInactiveIntent.activate();
        return findNextInactiveIntent;
    }

    private T findNextInactiveIntent() {
        for (CancellableTask cancellableTask : this.tasks) {
            if (!cancellableTask.isActivated()) {
                return cancellableTask;
            }
        }
        return null;
    }

    public synchronized void schedule(T t) {
        this.tasks.add(t);
        notifyAll();
    }

    public synchronized void remove(T t) {
        this.tasks.remove(t);
    }

    public synchronized void visit(Visitor<CancellableTask> visitor) {
        for (CancellableTask visit : this.tasks) {
            visitor.visit(visit);
        }
    }
}

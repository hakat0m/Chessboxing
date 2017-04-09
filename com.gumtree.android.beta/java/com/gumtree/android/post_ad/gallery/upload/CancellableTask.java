package com.gumtree.android.post_ad.gallery.upload;

abstract class CancellableTask {
    private boolean activated;
    private boolean cancelled;

    protected abstract void execute();

    protected abstract String getCancelId();

    CancellableTask() {
    }

    public void activate() {
        this.activated = true;
    }

    public boolean isActivated() {
        return this.activated;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public final boolean isCancelled() {
        return this.cancelled;
    }
}

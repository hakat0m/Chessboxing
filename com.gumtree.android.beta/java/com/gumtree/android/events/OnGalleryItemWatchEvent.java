package com.gumtree.android.events;

public class OnGalleryItemWatchEvent {
    private boolean mJustOpened;
    private int mPosition;

    public OnGalleryItemWatchEvent(int i, boolean z) {
        this.mPosition = i;
        this.mJustOpened = z;
    }

    public int getPosition() {
        return this.mPosition;
    }

    public boolean isJustOpened() {
        return this.mJustOpened;
    }
}

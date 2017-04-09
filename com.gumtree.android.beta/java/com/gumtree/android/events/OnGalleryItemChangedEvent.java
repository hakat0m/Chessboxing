package com.gumtree.android.events;

import android.graphics.Rect;

public class OnGalleryItemChangedEvent {
    private Rect mRect;

    public OnGalleryItemChangedEvent(Rect rect) {
        this.mRect = rect;
    }

    public Rect getRect() {
        return this.mRect;
    }
}

package com.facebook.stetho.inspector.elements.android;

import android.view.View;
import android.view.Window;
import com.facebook.stetho.common.Accumulator;
import com.facebook.stetho.inspector.elements.AbstractChainedDescriptor;
import javax.annotation.Nullable;

final class WindowDescriptor extends AbstractChainedDescriptor<Window> implements HighlightableDescriptor {
    WindowDescriptor() {
    }

    protected void onGetChildren(Window window, Accumulator<Object> accumulator) {
        View peekDecorView = window.peekDecorView();
        if (peekDecorView != null) {
            accumulator.store(peekDecorView);
        }
    }

    @Nullable
    public View getViewForHighlighting(Object obj) {
        return ((Window) obj).peekDecorView();
    }
}

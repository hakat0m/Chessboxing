package com.gumtree.android.postad.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ScrollViewListenerScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener;

    public interface ScrollViewListener {
        void onScrollChanged(ScrollViewListenerScrollView scrollViewListenerScrollView, int i, int i2, int i3, int i4);
    }

    public ScrollViewListenerScrollView(Context context) {
        super(context);
    }

    public ScrollViewListenerScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ScrollViewListenerScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        if (this.scrollViewListener != null) {
            this.scrollViewListener.onScrollChanged(this, i, i2, i3, i4);
        }
    }
}

package com.gumtree.android.vip.bing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.api.bing.BingAd;
import com.gumtree.android.srp.bing.BingAdView;
import javax.inject.Inject;

public class BingAdVIPView extends BingAdView {
    @Inject
    BingVIPCache bingVIPCache;

    public BingAdVIPView(Context context) {
        this(context, null);
        init();
    }

    public BingAdVIPView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        init();
    }

    public BingAdVIPView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        GumtreeApplication.component().inject(this);
    }

    protected int getLayoutId() {
        return 2130903326;
    }

    protected BingAd getBingAdFromCache(int i) {
        return this.bingVIPCache.get(i);
    }

    protected void updateViewToHeightAndChangeVisibility(int i, int i2) {
        ViewParent parent = getParent();
        if (parent != null) {
            ((View) parent).setVisibility(i2);
        }
    }
}

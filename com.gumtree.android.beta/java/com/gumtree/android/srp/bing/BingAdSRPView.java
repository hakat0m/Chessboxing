package com.gumtree.android.srp.bing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.GridView;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.api.bing.BingAd;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;
import com.gumtree.android.events.EventBus;
import com.squareup.otto.Subscribe;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

public class BingAdSRPView extends BingAdView {
    @Inject
    BingSRPCache bingSRPCache;
    private View container;
    @Inject
    EventBus eventBus;
    private GridView gridView;
    private Map<Integer, Integer> positionMap = new HashMap();

    public BingAdSRPView(Context context) {
        super(context);
        init();
    }

    public BingAdSRPView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public BingAdSRPView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        GumtreeApplication.component().inject(this);
        this.eventBus.register(this);
    }

    protected int getLayoutId() {
        return 2130903268;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.gridView != null && this.container != null) {
            try {
                updateView(this.gridView.getPositionForView(this.container));
            } catch (Throwable e) {
                CrashlyticsHelper.getInstance().catchGumtreeException(e);
            }
        }
    }

    @Subscribe
    public void onBingSRPLoadEvent(OnBingSRPLoadEvent onBingSRPLoadEvent) {
        updateWithLastKnownPosition();
    }

    protected BingAd getBingAdFromCache(int i) {
        int intValue;
        if (this.positionMap.containsKey(Integer.valueOf(i))) {
            intValue = ((Integer) this.positionMap.get(Integer.valueOf(i))).intValue();
        } else if (this.positionMap.containsKey(Integer.valueOf(i - 1))) {
            intValue = ((Integer) this.positionMap.get(Integer.valueOf(i - 1))).intValue();
        } else {
            this.positionMap.put(Integer.valueOf(i), Integer.valueOf(this.positionMap.keySet().size()));
            intValue = ((Integer) this.positionMap.get(Integer.valueOf(i))).intValue();
        }
        return this.bingSRPCache.get(intValue);
    }

    public void setContainer(GridView gridView, View view) {
        this.gridView = gridView;
        this.container = view;
    }

    public void destroy() {
        this.eventBus.unregister(this);
    }

    protected void updateViewToHeightAndChangeVisibility(int i, int i2) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent = parent.getParent();
            if (parent != null) {
                try {
                    View view = (View) parent;
                    view.setVisibility(i2);
                    LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = i;
                    view.setLayoutParams(layoutParams);
                } catch (Throwable e) {
                    Log.w("Problem changing height of BingAdView " + e.getMessage(), e);
                }
            }
        }
    }
}

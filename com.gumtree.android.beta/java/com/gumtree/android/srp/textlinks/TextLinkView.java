package com.gumtree.android.srp.textlinks;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.gumtree.android.home.dfp.HomeTextLinkProcessor;
import com.gumtree.android.srp.bing.BingAdSRPView;

public class TextLinkView extends FrameLayout {
    private BingAdSRPView bingAdSRPView;
    private int position;
    private PublisherAdView publisherAdView;

    public TextLinkView(Context context) {
        super(context);
        init(context);
    }

    public TextLinkView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public TextLinkView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        if (getContext().getResources().getBoolean(2131427350)) {
            this.bingAdSRPView = new BingAdSRPView(context);
            addView(this.bingAdSRPView);
            return;
        }
        this.publisherAdView = new PublisherAdView(context);
        addView(this.publisherAdView);
    }

    public void setAdMob(HomeTextLinkProcessor homeTextLinkProcessor, String str) {
        if (this.publisherAdView != null) {
            this.publisherAdView.setAdSizes(homeTextLinkProcessor.getAdSizes());
            this.publisherAdView.setAdUnitId(homeTextLinkProcessor.getBanner(str));
            this.publisherAdView.setId(2131623941);
        }
    }

    public void setAdMob(TextLinkProcessor textLinkProcessor, String str) {
        if (this.publisherAdView != null) {
            this.publisherAdView.setAdSizes(textLinkProcessor.getAdSizes());
            this.publisherAdView.setAdUnitId(textLinkProcessor.getBanner(str));
            this.publisherAdView.setId(2131623941);
        }
    }

    public void resume() {
        if (this.publisherAdView != null) {
            this.publisherAdView.resume();
        }
    }

    public void pause() {
        if (this.publisherAdView != null) {
            this.publisherAdView.pause();
        }
    }

    public void destroy() {
        if (this.publisherAdView != null) {
            this.publisherAdView.destroy();
        }
        if (this.bingAdSRPView != null) {
            this.bingAdSRPView.destroy();
        }
    }

    public void loadAd(PublisherAdRequest publisherAdRequest) {
        if (this.publisherAdView != null) {
            this.publisherAdView.loadAd(publisherAdRequest);
            this.publisherAdView.setAdListener(new 1(this));
        }
    }

    public void update(int i) {
        if (this.position != i) {
            this.position = i;
            if (this.bingAdSRPView != null) {
                this.bingAdSRPView.updateView(i);
            }
        }
    }

    public void setContainers(GridView gridView, View view) {
        if (this.bingAdSRPView != null) {
            this.bingAdSRPView.setContainer(gridView, view);
        }
    }
}

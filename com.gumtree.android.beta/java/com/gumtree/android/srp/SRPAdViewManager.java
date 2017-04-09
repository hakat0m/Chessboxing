package com.gumtree.android.srp;

import android.content.Context;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.gumtree.android.srp.dfp.SRPDFPProcessor;
import com.gumtree.android.srp.textlinks.TextLinkProcessor;
import com.gumtree.android.srp.textlinks.TextLinkView;

public class SRPAdViewManager {
    private PublisherAdView leaderboardView;
    private PublisherAdView mStickyBannerView;
    private PublisherAdView mpuView;
    private PublisherAdView mpuViewRow;
    private String path;
    private TextLinkProcessor textLinkDfp;
    private TextLinkView textLinkView;

    public void prepareAdView(String str, Context context) {
        this.textLinkDfp = new TextLinkProcessor();
        if (!(this.path == null || this.path.equals(str))) {
            this.textLinkView = null;
            this.mpuView = null;
            this.leaderboardView = null;
            this.mStickyBannerView = null;
        }
        this.path = str;
        if (this.textLinkView == null) {
            this.textLinkView = new TextLinkView(context);
            this.textLinkView.setAdMob(this.textLinkDfp, str);
        }
        if (this.mpuView == null) {
            this.mpuView = new PublisherAdView(context);
            this.mpuView.setAdSizes(this.textLinkDfp.getMpuAdSizes());
            this.mpuView.setAdUnitId(this.textLinkDfp.getMpuBanner(str));
            this.mpuView.setId(2131623941);
        }
        if (this.mpuViewRow == null) {
            this.mpuViewRow = new PublisherAdView(context);
            this.mpuViewRow.setAdSizes(new AdSize[]{AdSize.FLUID, this.textLinkDfp.getMpuAdSizes()[0]});
            this.mpuViewRow.setAdUnitId(this.textLinkDfp.getMpuBanner(str));
            this.mpuViewRow.setId(2131623941);
        }
        if (this.leaderboardView == null) {
            this.leaderboardView = new PublisherAdView(context);
            this.leaderboardView.setAdSizes(this.textLinkDfp.getLeaderboardAdSizes());
            this.leaderboardView.setAdUnitId(this.textLinkDfp.getLeaderboardBanner(str));
            this.leaderboardView.setId(2131623941);
        }
        if (this.mStickyBannerView == null) {
            this.mStickyBannerView = new PublisherAdView(context);
            this.mStickyBannerView.setAdSizes(this.textLinkDfp.getStickyBannerSizes());
            this.mStickyBannerView.setAdUnitId(this.textLinkDfp.getStickyBanner(str));
            this.mStickyBannerView.setId(2131623941);
        }
    }

    public TextLinkView getTextLinkView() {
        return this.textLinkView;
    }

    public PublisherAdView getMpuView() {
        return this.mpuView;
    }

    public PublisherAdView getMpuRowView() {
        return this.mpuViewRow;
    }

    public PublisherAdView getLeaderboardView() {
        return this.leaderboardView;
    }

    public PublisherAdRequest createRequest(String str, String str2, String str3) {
        return this.textLinkDfp.createRequest(this.path, str3, new SRPDFPProcessor().getBasicExtras(str, str2));
    }

    public PublisherAdRequest createStickyBannerRequest(String str, String str2) {
        return this.textLinkDfp.createRequest(this.path, str2, new SRPDFPProcessor().getBasicExtras(str, "floating"));
    }

    public PublisherAdView getStickyBannerView() {
        return this.mStickyBannerView;
    }

    public void resume() {
        if (this.textLinkView != null) {
            this.textLinkView.resume();
        }
        if (this.mpuView != null) {
            this.mpuView.resume();
        }
        if (this.leaderboardView != null) {
            this.leaderboardView.resume();
        }
    }

    public void pause() {
        if (this.textLinkView != null) {
            this.textLinkView.pause();
        }
        if (this.mpuView != null) {
            this.mpuView.pause();
        }
        if (this.leaderboardView != null) {
            this.leaderboardView.pause();
        }
    }

    public void destroy() {
        if (this.textLinkView != null) {
            this.textLinkView.destroy();
            this.textLinkView = null;
        }
        if (this.mpuView != null) {
            this.mpuView.destroy();
            this.mpuView = null;
        }
        if (this.leaderboardView != null) {
            this.leaderboardView.destroy();
            this.leaderboardView = null;
        }
    }
}

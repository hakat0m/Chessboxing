package com.gumtree.android.home.dfp;

import android.content.Context;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.gumtree.android.srp.textlinks.TextLinkView;

public class HomeAdViewManager {
    private PublisherAdView leaderboardView;
    private PublisherAdView mpuView;
    private String path;
    private HomeTextLinkProcessor textLinkDfp;
    private TextLinkView textLinkView;

    public void prepareAdView(String str, Context context) {
        this.textLinkDfp = new HomeTextLinkProcessor();
        if (!(this.path == null || this.path.equals(str))) {
            this.textLinkView = null;
            this.mpuView = null;
            this.leaderboardView = null;
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
        if (this.leaderboardView == null) {
            this.leaderboardView = new PublisherAdView(context);
            this.leaderboardView.setAdSizes(this.textLinkDfp.getLeaderboardAdSizes());
            this.leaderboardView.setAdUnitId(this.textLinkDfp.getLeaderboardBanner(str));
            this.leaderboardView.setId(2131623941);
        }
    }

    public TextLinkView getTextLinkView() {
        return this.textLinkView;
    }

    public PublisherAdView getMpuView() {
        return this.mpuView;
    }

    public PublisherAdView getLeaderboardView() {
        return this.leaderboardView;
    }

    public PublisherAdRequest createRequest(String str, String str2, String str3) {
        return this.textLinkDfp.createRequest(this.path, str3, new HomeDFPProcessor().getBasicExtras(str, str2));
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

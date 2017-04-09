package com.gumtree.android.manageads;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.common.Paging;
import com.gumtree.android.manageads.Ad.Status;
import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

public class Ads {
    private List<Ad> ads = new ArrayList();
    private List<Ad> liveAds = new ArrayList();
    private Paging paging;
    private List<Ad> pendingAds = new ArrayList();

    public AdsBuilder toBuilder() {
        return new AdsBuilder().pendingAds(this.pendingAds).liveAds(this.liveAds).ads(this.ads).paging(this.paging);
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof Ads;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Ads)) {
            return false;
        }
        Ads ads = (Ads) obj;
        if (!ads.canEqual(this)) {
            return false;
        }
        List pendingAds = getPendingAds();
        List pendingAds2 = ads.getPendingAds();
        if (pendingAds != null ? !pendingAds.equals(pendingAds2) : pendingAds2 != null) {
            return false;
        }
        pendingAds = getLiveAds();
        pendingAds2 = ads.getLiveAds();
        if (pendingAds != null ? !pendingAds.equals(pendingAds2) : pendingAds2 != null) {
            return false;
        }
        pendingAds = getAds();
        pendingAds2 = ads.getAds();
        if (pendingAds != null ? !pendingAds.equals(pendingAds2) : pendingAds2 != null) {
            return false;
        }
        Paging paging = getPaging();
        Paging paging2 = ads.getPaging();
        if (paging == null) {
            if (paging2 == null) {
                return true;
            }
        } else if (paging.equals(paging2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        List pendingAds = getPendingAds();
        int hashCode = (pendingAds == null ? 43 : pendingAds.hashCode()) + 59;
        List liveAds = getLiveAds();
        hashCode = (liveAds == null ? 43 : liveAds.hashCode()) + (hashCode * 59);
        liveAds = getAds();
        hashCode = (liveAds == null ? 43 : liveAds.hashCode()) + (hashCode * 59);
        Paging paging = getPaging();
        hashCode *= 59;
        if (paging != null) {
            i = paging.hashCode();
        }
        return hashCode + i;
    }

    public void setAds(List<Ad> list) {
        this.ads = list;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public String toString() {
        return "Ads(pendingAds=" + getPendingAds() + ", liveAds=" + getLiveAds() + ", ads=" + getAds() + ", paging=" + getPaging() + ")";
    }

    public static AdsBuilder builder() {
        return new AdsBuilder();
    }

    @ConstructorProperties({"pendingAds", "liveAds", "ads", "paging"})
    public Ads(List<Ad> list, List<Ad> list2, List<Ad> list3, Paging paging) {
        this.pendingAds = list;
        this.liveAds = list2;
        this.ads = list3;
        this.paging = paging;
    }

    public List<Ad> getPendingAds() {
        return this.pendingAds;
    }

    public List<Ad> getLiveAds() {
        return this.liveAds;
    }

    public List<Ad> getAds() {
        return this.ads;
    }

    public Paging getPaging() {
        return this.paging;
    }

    public void setPendingAds(@NonNull List<Ad> list) {
        this.pendingAds = list;
    }

    public void addPendingHeader() {
        Ad ad = new Ad();
        ad.setHeader(true);
        ad.setStatusIdentifier(Status.PENDING);
        this.pendingAds.add(0, ad);
    }

    public void setLiveAds(@NonNull List<Ad> list) {
        this.liveAds = list;
    }

    public void addLiveHeader(int i) {
        Ad ad = new Ad();
        ad.setHeader(true);
        ad.setStatusIdentifier(Status.LIVE);
        ad.setAdsCount(i);
        this.liveAds.add(0, ad);
    }

    public void updateAdsList() {
        resetAdsList();
        this.ads.addAll(this.pendingAds);
        this.ads.addAll(this.liveAds);
    }

    private void resetAdsList() {
        if (!this.ads.isEmpty()) {
            this.ads.clear();
        }
    }
}

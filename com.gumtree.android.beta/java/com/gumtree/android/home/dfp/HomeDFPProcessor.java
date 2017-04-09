package com.gumtree.android.home.dfp;

import android.os.Bundle;
import com.google.android.gms.ads.AdSize;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.location.AppLocation;
import com.gumtree.android.common.location.PreferenceBasedRadiusDAO;
import com.gumtree.android.common.utils.AppUtils;
import com.gumtree.android.dfp.DFPProcessor;

public class HomeDFPProcessor extends DFPProcessor {
    private static final String AD_UNIT_STANDARD_BANNER = "/5144/m.android.home.";
    private static final String AD_UNIT_STANDARD_TABLET_BANNER = "/5144/t.android.home.";
    private static final String LEADERBOARD_BANNER = "/5144/t.android.home.";
    private static final String MPU_BANNER = "/5144/m.android.home.";
    private static final String MPU_TABLET_BANNER = "/5144/t.android.home.";

    public AdSize[] getAdSizes() {
        return new AdSize[]{AdSize.BANNER};
    }

    public String getBanner(String str) {
        if (AppUtils.isTablet()) {
            return getAdUnitId(str, MPU_TABLET_BANNER);
        }
        return getAdUnitId(str, MPU_BANNER);
    }

    public String getMpuBanner(String str) {
        if (AppUtils.isTablet()) {
            return getAdUnitId(str, MPU_TABLET_BANNER);
        }
        return getAdUnitId(str, MPU_BANNER);
    }

    public String getLeaderboardBanner(String str) {
        return getAdUnitId(str, MPU_TABLET_BANNER);
    }

    public AdSize[] getLeaderboardAdSizes() {
        return new AdSize[]{AdSize.LEADERBOARD};
    }

    public Bundle getBasicExtras(String str, String str2) {
        AppLocation globalBuyerLocation = ((GumtreeApplication) GumtreeApplication.getContext()).getGlobalBuyerLocation();
        Bundle bundle = new Bundle();
        String radiusInDecimal = PreferenceBasedRadiusDAO.get(GumtreeApplication.getContext()).getRadiusInDecimal(globalBuyerLocation);
        for (String str3 : globalBuyerLocation.getAdMobData().keySet()) {
            bundle.putString(str3, (String) globalBuyerLocation.getAdMobData().get(str3));
        }
        bundle.putString("pos", str2);
        bundle.putString(StatefulActivity.NAME_DISTANCE, radiusInDecimal);
        return getBasicExtras(bundle, str);
    }

    public String getPageType() {
        return "search";
    }
}

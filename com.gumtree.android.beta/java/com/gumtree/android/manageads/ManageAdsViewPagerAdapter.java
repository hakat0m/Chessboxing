package com.gumtree.android.manageads;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.manageads.active.ActiveAdsFragment;
import com.gumtree.android.manageads.inactive.InactiveAdsFragment;

public class ManageAdsViewPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 2;
    private ActiveAdsFragment activeAdsFragment;
    private final Context context;
    private InactiveAdsFragment inactiveAdsFragment;

    public ManageAdsViewPagerAdapter(BaseActivity baseActivity) {
        super(baseActivity.getSupportFragmentManager());
        this.context = baseActivity.getApplicationContext();
    }

    public Fragment getItem(int i) {
        if (i == 0) {
            this.activeAdsFragment = new ActiveAdsFragment();
            return this.activeAdsFragment;
        }
        this.inactiveAdsFragment = new InactiveAdsFragment();
        return this.inactiveAdsFragment;
    }

    public CharSequence getPageTitle(int i) {
        if (i == 0) {
            return this.context.getString(2131165805);
        }
        return this.context.getString(2131165806);
    }

    public int getCount() {
        return PAGE_COUNT;
    }

    public void update() {
        if (this.activeAdsFragment != null) {
            this.activeAdsFragment.refreshContent();
        }
        if (this.inactiveAdsFragment != null) {
            this.inactiveAdsFragment.refreshContent();
        }
    }
}

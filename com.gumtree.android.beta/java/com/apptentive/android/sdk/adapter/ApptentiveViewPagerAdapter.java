package com.apptentive.android.sdk.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.apptentive.android.sdk.BuildConfig;
import com.apptentive.android.sdk.module.engagement.interaction.fragment.ApptentiveBaseFragment;
import java.util.ArrayList;
import java.util.List;

public class ApptentiveViewPagerAdapter extends FragmentPagerAdapter {
    private List<ApptentiveBaseFragment> fragments = new ArrayList();
    private List<String> tabTitles = new ArrayList();
    private List<String> tags = new ArrayList();

    public ApptentiveViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        ApptentiveBaseFragment apptentiveBaseFragment = (ApptentiveBaseFragment) super.instantiateItem(viewGroup, i);
        this.fragments.set(i, apptentiveBaseFragment);
        this.tabTitles.set(i, apptentiveBaseFragment.getTitle());
        this.tags.set(i, apptentiveBaseFragment.getTag());
        return getItem(i);
    }

    public Fragment getItem(int i) {
        return (Fragment) this.fragments.get(i);
    }

    public int getCount() {
        return this.fragments.size();
    }

    public CharSequence getPageTitle(int i) {
        return (CharSequence) this.tabTitles.get(i);
    }

    public String getFragmentTag(int i) {
        return (String) this.tags.get(i);
    }

    public void removeItem(int i) {
        if (i <= getCount()) {
            this.fragments.remove(i);
            this.tabTitles.remove(i);
            this.tags.remove(i);
        }
    }

    public void add(ApptentiveBaseFragment apptentiveBaseFragment, String str) {
        this.fragments.add(apptentiveBaseFragment);
        this.tabTitles.add(str);
        this.tags.add(BuildConfig.FLAVOR);
    }
}

package com.gumtree.android.login;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.login.login.LoginFragment;
import com.gumtree.android.login.registration.RegistrationFragment;

public class AuthAdapter extends FragmentPagerAdapter {
    private final Context context;

    public AuthAdapter(BaseActivity baseActivity) {
        super(baseActivity.getSupportFragmentManager());
        this.context = baseActivity.getApplicationContext();
    }

    public Fragment getItem(int i) {
        if (i == 0) {
            return new LoginFragment();
        }
        return new RegistrationFragment();
    }

    public CharSequence getPageTitle(int i) {
        if (i == 0) {
            return this.context.getString(2131165807);
        }
        return this.context.getString(2131165808);
    }

    public int getCount() {
        return 2;
    }
}

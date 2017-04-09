package com.gumtree.android.post_ad.steps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.List;

public class PostAdFlowManagerImpl implements PostAdFlowManager {
    public static final String STEP_KEY = "step_key";
    private int mCurrentStep;
    private FragmentManager mFragmentManager;
    private List<Fragment> postAdFragmentSteps = new ArrayList();

    public PostAdFlowManagerImpl(int i, FragmentManager fragmentManager, Bundle bundle) {
        this.mFragmentManager = fragmentManager;
        this.mCurrentStep = i;
        this.postAdFragmentSteps.add(new PostAdOneFragment2());
        this.postAdFragmentSteps.add(new PostAdTwoFragment2());
        this.postAdFragmentSteps.add(new PostAdThreeFragment2());
        if (bundle == null) {
            String valueOf = String.valueOf(this.mCurrentStep);
            Fragment findFragmentByTag = this.mFragmentManager.findFragmentByTag(valueOf);
            if (findFragmentByTag == null) {
                findFragmentByTag = (Fragment) this.postAdFragmentSteps.get(this.mCurrentStep);
            }
            addPostAdFragment(findFragmentByTag, valueOf, false);
            return;
        }
        this.mCurrentStep = bundle.getInt(STEP_KEY, 0);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(STEP_KEY, this.mCurrentStep);
    }

    public boolean onNext() {
        if (this.mCurrentStep >= this.postAdFragmentSteps.size() - 1) {
            return false;
        }
        this.mCurrentStep++;
        addPostAdFragment((Fragment) this.postAdFragmentSteps.get(this.mCurrentStep), String.valueOf(this.mCurrentStep), true);
        return true;
    }

    public boolean onPrevious() {
        if (this.mCurrentStep <= 0) {
            return false;
        }
        this.mCurrentStep--;
        return true;
    }

    private void addPostAdFragment(Fragment fragment, String str, boolean z) {
        FragmentTransaction beginTransaction = this.mFragmentManager.beginTransaction();
        if (z) {
            beginTransaction.setCustomAnimations(2130968591, 2130968590, 2130968589, 2130968592);
            beginTransaction.addToBackStack(str);
        }
        beginTransaction.replace(2131624199, fragment, str);
        beginTransaction.commit();
    }
}

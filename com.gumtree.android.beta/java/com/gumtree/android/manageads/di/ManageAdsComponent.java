package com.gumtree.android.manageads.di;

import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.manageads.ManageAdsActivity;
import com.gumtree.android.manageads.active.ActiveAdsFragment;
import com.gumtree.android.manageads.inactive.InactiveAdsFragment;
import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {ManageAdsModule.class})
@ManageAdsScope
public interface ManageAdsComponent extends BaseComponent {
    public static final String KEY = ManageAdsComponent.class.getSimpleName();

    void inject(ManageAdsActivity manageAdsActivity);

    void inject(ActiveAdsFragment activeAdsFragment);

    void inject(InactiveAdsFragment inactiveAdsFragment);
}

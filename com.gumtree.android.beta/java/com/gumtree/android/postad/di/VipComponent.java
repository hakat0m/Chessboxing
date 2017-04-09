package com.gumtree.android.postad.di;

import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.vip.VIPContactFragment;
import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {VipModule.class})
@VipScope
public interface VipComponent extends BaseComponent {
    public static final String KEY = VipComponent.class.getSimpleName();

    void inject(VIPContactFragment vIPContactFragment);
}

package com.gumtree.android.postad.customdetails.di;

import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.postad.customdetails.CustomDetailsActivity;
import dagger.Component;

@CustomDetailsScope
@Component(dependencies = {ApplicationComponent.class}, modules = {CustomDetailsModule.class})
public interface CustomDetailsComponent extends BaseComponent {
    public static final String KEY = CustomDetailsComponent.class.getSimpleName();

    void inject(CustomDetailsActivity customDetailsActivity);
}

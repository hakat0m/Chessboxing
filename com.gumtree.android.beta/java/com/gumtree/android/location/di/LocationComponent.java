package com.gumtree.android.location.di;

import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.location.LocationActivity;
import dagger.Component;

@LocationScope
@Component(dependencies = {ApplicationComponent.class}, modules = {LocationModule.class})
public interface LocationComponent extends BaseComponent {
    public static final String KEY = LocationComponent.class.getSimpleName();

    void inject(LocationActivity locationActivity);
}

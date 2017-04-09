package com.gumtree.android.splash.di;

import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.splash.SplashActivity;
import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {SplashModule.class})
@SplashScope
public interface SplashComponent extends BaseComponent {
    public static final String KEY = SplashComponent.class.getSimpleName();

    void inject(SplashActivity splashActivity);
}

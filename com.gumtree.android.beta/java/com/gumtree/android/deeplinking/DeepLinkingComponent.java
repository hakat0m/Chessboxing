package com.gumtree.android.deeplinking;

import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {DeepLinkingModule.class})
@DeepLinkingScope
public interface DeepLinkingComponent extends BaseComponent {
    public static final String KEY = DeepLinkingComponent.class.getSimpleName();

    void inject(DeepLinkingActivity deepLinkingActivity);
}

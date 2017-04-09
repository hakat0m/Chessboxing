package com.gumtree.android.postad.di;

import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.postad.PostAdActivity;
import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {PostAdModule.class})
@PostAdScope
public interface PostAdComponent extends BaseComponent {
    public static final String KEY = PostAdComponent.class.getSimpleName();

    void inject(PostAdActivity postAdActivity);
}

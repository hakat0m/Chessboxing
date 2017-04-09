package com.gumtree.android.postad.contactdetails.di;

import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.postad.contactdetails.PostAdContactDetailsActivity;
import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {PostAdContactDetailsModule.class})
@PostAdContactDetailsScope
public interface PostAdContactDetailsComponent extends BaseComponent {
    public static final String KEY = PostAdContactDetailsComponent.class.getSimpleName();

    void inject(PostAdContactDetailsActivity postAdContactDetailsActivity);
}

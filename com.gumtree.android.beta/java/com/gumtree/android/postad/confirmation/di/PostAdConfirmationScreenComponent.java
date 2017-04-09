package com.gumtree.android.postad.confirmation.di;

import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenActivity;
import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenPresenter;
import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {PostAdConfirmationScreenModule.class})
@PostAdConfirmationScreenScope
public interface PostAdConfirmationScreenComponent extends BaseComponent {
    public static final String KEY = PostAdConfirmationScreenComponent.class.getSimpleName();

    void inject(PostAdConfirmationScreenActivity postAdConfirmationScreenActivity);

    PostAdConfirmationScreenPresenter providePostAdConfirmationScreenPresenter();
}

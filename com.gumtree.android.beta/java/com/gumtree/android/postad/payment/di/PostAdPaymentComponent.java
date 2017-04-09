package com.gumtree.android.postad.payment.di;

import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.postad.payment.PostAdPaymentActivity;
import dagger.Component;

@PostAdPaymentScope
@Component(dependencies = {ApplicationComponent.class}, modules = {PostAdPaymentModule.class})
public interface PostAdPaymentComponent extends BaseComponent {
    public static final String KEY = PostAdPaymentComponent.class.getSimpleName();

    void inject(PostAdPaymentActivity postAdPaymentActivity);
}

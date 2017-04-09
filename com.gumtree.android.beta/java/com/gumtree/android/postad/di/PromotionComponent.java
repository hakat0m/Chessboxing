package com.gumtree.android.postad.di;

import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.postad.promote.PromotionActivity;
import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {PromotionModule.class})
@PromotionScope
public interface PromotionComponent extends BaseComponent {
    public static final String KEY = PromotionComponent.class.getSimpleName();

    void inject(PromotionActivity promotionActivity);
}

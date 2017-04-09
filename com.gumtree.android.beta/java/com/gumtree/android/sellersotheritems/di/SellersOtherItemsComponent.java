package com.gumtree.android.sellersotheritems.di;

import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.sellersotheritems.SellersOtherItemsActivity;
import dagger.Component;

@SellersOtherItemsScope
@Component(dependencies = {ApplicationComponent.class}, modules = {SellersOtherItemsModule.class})
public interface SellersOtherItemsComponent extends BaseComponent {
    public static final String KEY = SellersOtherItemsComponent.class.getSimpleName();

    void inject(SellersOtherItemsActivity sellersOtherItemsActivity);
}

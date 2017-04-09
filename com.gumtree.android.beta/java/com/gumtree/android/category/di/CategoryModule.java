package com.gumtree.android.category.di;

import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.categories.CategoriesApi;
import com.gumtree.android.category.suggest.presenter.DefaultPostAdCategoryPresenter;
import com.gumtree.android.category.suggest.presenter.GatedPostAdCategoryView;
import com.gumtree.android.category.suggest.presenter.PostAdCategoryPresenter;
import com.gumtree.android.category.suggest.service.ApiCategoryService;
import com.gumtree.android.category.suggest.service.BackgroundCategoryService;
import com.gumtree.android.category.suggest.service.CategoryService;
import com.gumtree.android.category.suggest.service.converter.CategoryConverter;
import com.gumtree.android.category.suggest.service.converter.CategoryTreeConverter;
import com.gumtree.android.category.suggest.service.converter.CategoryTrunkConverter;
import com.gumtree.android.category.suggest.service.converter.DefaultCategoryTreeConverter;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class CategoryModule {
    private final String adTitle;

    public CategoryModule(String str) {
        this.adTitle = str;
    }

    @Provides
    @CategoryScope
    public String provideAdTitle() {
        return this.adTitle;
    }

    @Provides
    @CategoryScope
    public CategoryService provideCategoryService(@Named("xmlClient") ICapiClient iCapiClient, @Named("background") Scheduler scheduler, DefaultCategoryTreeConverter defaultCategoryTreeConverter) {
        return new BackgroundCategoryService(new ApiCategoryService((CategoriesApi) iCapiClient.api(CategoriesApi.class), new CategoryConverter(defaultCategoryTreeConverter)), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @CategoryScope
    public CategoryTreeConverter provideCategoryTrunkConverter(CategoryTrunkConverter categoryTrunkConverter) {
        return categoryTrunkConverter;
    }

    @Provides
    @CategoryScope
    public PostAdCategoryPresenter providePostAdCategoryPresenter(CategoryService categoryService, GatedPostAdCategoryView gatedPostAdCategoryView) {
        return new DefaultPostAdCategoryPresenter(categoryService, gatedPostAdCategoryView);
    }
}

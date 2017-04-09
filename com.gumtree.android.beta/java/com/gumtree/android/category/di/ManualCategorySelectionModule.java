package com.gumtree.android.category.di;

import android.content.Context;
import com.gumtree.android.category.manual.presenter.DefaultManualCategorySelectionPresenter;
import com.gumtree.android.category.manual.presenter.GatedManualCategorySelectionView;
import com.gumtree.android.category.manual.presenter.ManualCategorySelectionPresenter;
import com.gumtree.android.category.manual.service.BackgroundCategoryRepositoryService;
import com.gumtree.android.category.manual.service.CategoryRepositoryService;
import com.gumtree.android.postad.services.ContentProviderCategoryRepositoryService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class ManualCategorySelectionModule {
    @Provides
    @ManualCategorySelectionScope
    public ManualCategorySelectionPresenter provideManualCategorySelectionPresenter(GatedManualCategorySelectionView gatedManualCategorySelectionView) {
        return new DefaultManualCategorySelectionPresenter(gatedManualCategorySelectionView);
    }

    @Provides
    @ManualCategorySelectionScope
    public CategoryRepositoryService provideCategoryRepositoryService(Context context, @Named("background") Scheduler scheduler) {
        return new BackgroundCategoryRepositoryService(new ContentProviderCategoryRepositoryService(context), AndroidSchedulers.mainThread(), scheduler);
    }
}

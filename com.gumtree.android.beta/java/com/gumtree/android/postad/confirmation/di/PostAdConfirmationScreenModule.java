package com.gumtree.android.postad.confirmation.di;

import com.gumtree.android.postad.confirmation.DefaultPostAdConfirmationScreenPresenter;
import com.gumtree.android.postad.confirmation.GatedPostAdConfirmationScreenView;
import com.gumtree.android.postad.confirmation.GetDraftAdHelper;
import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenPresenter;
import com.gumtree.android.postad.services.PostAdService;
import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

@Module
public class PostAdConfirmationScreenModule {
    private String adId;

    public PostAdConfirmationScreenModule(String str) {
        this.adId = str;
    }

    @Provides
    @PostAdConfirmationScreenScope
    GetDraftAdHelper provideGetDraftAdHelper(PostAdService postAdService) {
        return new GetDraftAdHelper(postAdService, this.adId, Schedulers.computation());
    }

    @Provides
    @PostAdConfirmationScreenScope
    PostAdConfirmationScreenPresenter providePostAdConfirmationScreenPresenter(GatedPostAdConfirmationScreenView gatedPostAdConfirmationScreenView, GetDraftAdHelper getDraftAdHelper) {
        return new DefaultPostAdConfirmationScreenPresenter(gatedPostAdConfirmationScreenView, getDraftAdHelper);
    }
}

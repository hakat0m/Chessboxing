package com.gumtree.android.category.suggest.presenter;

import android.support.annotation.Nullable;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.category.suggest.presenter.PostAdCategoryPresenter.View;
import com.gumtree.android.mvp.Gate;
import java.util.List;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedPostAdCategoryView implements View {
    private Gate<DraftCategory> forwardSelectedSuggestionToCaller = new Gate();
    private Gate<Boolean> showLoading = new Gate();
    private Gate<Boolean> showManualCategorySelection = new Gate();
    private Gate<Boolean> showSuggestedCategoriesTitle = new Gate();
    private Gate<List<DraftCategory>> showSuggestions = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedPostAdCategoryView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    private void close() {
        this.showSuggestions.close();
        this.forwardSelectedSuggestionToCaller.close();
        this.showLoading.close();
        this.showManualCategorySelection.close();
        this.showSuggestedCategoriesTitle.close();
    }

    private void open(View view) {
        this.showSuggestions.open(GatedPostAdCategoryView$$Lambda$2.lambdaFactory$(view));
        this.forwardSelectedSuggestionToCaller.open(GatedPostAdCategoryView$$Lambda$3.lambdaFactory$(view));
        this.showLoading.open(GatedPostAdCategoryView$$Lambda$4.lambdaFactory$(view));
        this.showManualCategorySelection.open(GatedPostAdCategoryView$$Lambda$5.lambdaFactory$(view));
        this.showSuggestedCategoriesTitle.open(GatedPostAdCategoryView$$Lambda$6.lambdaFactory$(view));
    }

    public void showSuggestions(List<DraftCategory> list) {
        this.showSuggestions.perform(list);
    }

    public void completeWithCategory(DraftCategory draftCategory) {
        this.forwardSelectedSuggestionToCaller.perform(draftCategory);
    }

    public void showLoading(boolean z) {
        this.showLoading.perform(Boolean.valueOf(z));
    }

    public void showManualCategorySelection(boolean z) {
        this.showManualCategorySelection.perform(Boolean.valueOf(z));
    }

    public void showSuggestedCategoriesTitle(boolean z) {
        this.showSuggestedCategoriesTitle.perform(Boolean.valueOf(z));
    }
}

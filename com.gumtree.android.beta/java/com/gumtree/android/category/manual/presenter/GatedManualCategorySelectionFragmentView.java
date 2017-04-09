package com.gumtree.android.category.manual.presenter;

import android.support.annotation.Nullable;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.mvp.Gate;
import java.util.List;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedManualCategorySelectionFragmentView implements ManualCategorySelectionFragmentPresenter$View {
    private Gate<List<DraftCategory>> categoriesUpdated = new Gate();
    private Gate<Boolean> showWaiting = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedManualCategorySelectionFragmentView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<ManualCategorySelectionFragmentPresenter$View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(ManualCategorySelectionFragmentPresenter$View manualCategorySelectionFragmentPresenter$View) {
        if (manualCategorySelectionFragmentPresenter$View == null) {
            close();
        } else {
            open(manualCategorySelectionFragmentPresenter$View);
        }
    }

    public void setDecorated(@Nullable ManualCategorySelectionFragmentPresenter$View manualCategorySelectionFragmentPresenter$View) {
        this.trigger.onNext(manualCategorySelectionFragmentPresenter$View);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    private void close() {
        this.showWaiting.close();
        this.categoriesUpdated.close();
    }

    private void open(ManualCategorySelectionFragmentPresenter$View manualCategorySelectionFragmentPresenter$View) {
        this.showWaiting.open(GatedManualCategorySelectionFragmentView$$Lambda$4.lambdaFactory$(manualCategorySelectionFragmentPresenter$View));
        this.categoriesUpdated.open(GatedManualCategorySelectionFragmentView$$Lambda$5.lambdaFactory$(manualCategorySelectionFragmentPresenter$View));
    }

    public void onCategoriesUpdated(List<DraftCategory> list) {
        this.categoriesUpdated.perform(list);
    }

    public void onShowWaiting(boolean z) {
        this.showWaiting.perform(Boolean.valueOf(z));
    }
}

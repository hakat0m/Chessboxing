package com.gumtree.android.category.manual.presenter;

import android.support.annotation.Nullable;
import com.gumtree.android.category.manual.presenter.ManualCategorySelectionPresenter.View;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.mvp.Gate;
import org.apache.commons.lang3.tuple.Pair;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedManualCategorySelectionView implements View {
    private final Gate<DraftCategory> onCategorySelected = new Gate();
    private final Gate<Pair<Integer, String>> showNextLevel = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedManualCategorySelectionView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();
    private final Gate<String> updateTitle = new Gate();

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
        this.showNextLevel.close();
        this.onCategorySelected.close();
        this.updateTitle.close();
    }

    private void open(View view) {
        this.showNextLevel.open(GatedManualCategorySelectionView$$Lambda$2.lambdaFactory$(view));
        this.onCategorySelected.open(GatedManualCategorySelectionView$$Lambda$3.lambdaFactory$(view));
        this.updateTitle.open(GatedManualCategorySelectionView$$Lambda$4.lambdaFactory$(view));
    }

    public void onShowNextLevel(int i, String str) {
        this.showNextLevel.perform(Pair.of(Integer.valueOf(i), str));
    }

    public void onCategorySelected(DraftCategory draftCategory) {
        this.onCategorySelected.perform(draftCategory);
    }

    public void onUpdateTitle(String str) {
        this.updateTitle.perform(str);
    }
}

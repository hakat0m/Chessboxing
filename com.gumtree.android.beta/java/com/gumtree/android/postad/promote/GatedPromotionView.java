package com.gumtree.android.postad.promote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.gumtree.android.mvp.Gate;
import com.gumtree.android.postad.DraftFeature.Type;
import com.gumtree.android.postad.promote.PromotionPresenter.View;
import java.util.List;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedPromotionView implements View {
    private final Gate<String> cancelOnError = new Gate();
    private final Gate<Type> promptInfo = new Gate();
    private final Gate<List<PromotionFeature>> showFeatures = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedPromotionView$$Lambda$1.lambdaFactory$(this));
    private final BehaviorSubject<View> trigger = BehaviorSubject.create();
    private final Gate<List<PromotionFeature>> updateSelection = new Gate();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    private void open(View view) {
        this.showFeatures.open(GatedPromotionView$$Lambda$2.lambdaFactory$(view));
        this.cancelOnError.open(GatedPromotionView$$Lambda$3.lambdaFactory$(view));
        this.updateSelection.open(GatedPromotionView$$Lambda$4.lambdaFactory$(view));
        this.promptInfo.open(GatedPromotionView$$Lambda$5.lambdaFactory$(view));
    }

    private void close() {
        this.showFeatures.close();
        this.cancelOnError.close();
        this.updateSelection.close();
        this.promptInfo.close();
    }

    public void showFeatures(List<PromotionFeature> list) {
        this.showFeatures.perform(list);
    }

    public void cancelOnError(String str) {
        this.cancelOnError.perform(str);
    }

    public void updateSelection(@NonNull List<PromotionFeature> list) {
        this.updateSelection.perform(list);
    }

    public void promptInfo(Type type) {
        this.promptInfo.perform(type);
    }
}

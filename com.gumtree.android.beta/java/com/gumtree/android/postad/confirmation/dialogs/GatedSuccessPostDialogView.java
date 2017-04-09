package com.gumtree.android.postad.confirmation.dialogs;

import android.support.annotation.Nullable;
import com.gumtree.android.mvp.Gate;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedSuccessPostDialogView implements SuccessPostDialogPresenter$View {
    private Gate<String> showItemImage = new Gate();
    private Gate<String> showItemPrice = new Gate();
    private Gate<String> showItemSubTitle = new Gate();
    private Gate<String> showItemTitle = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedSuccessPostDialogView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<SuccessPostDialogPresenter$View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(SuccessPostDialogPresenter$View successPostDialogPresenter$View) {
        if (successPostDialogPresenter$View == null) {
            close();
        } else {
            open(successPostDialogPresenter$View);
        }
    }

    public void setDecorated(@Nullable SuccessPostDialogPresenter$View successPostDialogPresenter$View) {
        this.trigger.onNext(successPostDialogPresenter$View);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    protected void open(SuccessPostDialogPresenter$View successPostDialogPresenter$View) {
        this.showItemTitle.open(GatedSuccessPostDialogView$$Lambda$2.lambdaFactory$(successPostDialogPresenter$View));
        this.showItemSubTitle.open(GatedSuccessPostDialogView$$Lambda$3.lambdaFactory$(successPostDialogPresenter$View));
        this.showItemPrice.open(GatedSuccessPostDialogView$$Lambda$4.lambdaFactory$(successPostDialogPresenter$View));
        this.showItemImage.open(GatedSuccessPostDialogView$$Lambda$5.lambdaFactory$(successPostDialogPresenter$View));
    }

    protected void close() {
        this.showItemTitle.close();
        this.showItemSubTitle.close();
        this.showItemPrice.close();
        this.showItemImage.close();
    }

    public void showItemTitle(String str) {
        this.showItemTitle.perform(str);
    }

    public void showItemSubTitle(String str) {
        this.showItemSubTitle.perform(str);
    }

    public void showItemPrice(String str) {
        this.showItemPrice.perform(str);
    }

    public void showItemImage(String str) {
        this.showItemImage.perform(str);
    }
}

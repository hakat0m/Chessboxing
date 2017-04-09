package com.gumtree.android.postad.confirmation;

import android.support.annotation.Nullable;
import com.gumtree.android.mvp.Gate;
import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenPresenter.View;
import com.gumtree.android.postad.confirmation.models.ConfirmationScreenResults;
import com.gumtree.android.postad.confirmation.models.SuccessPostResult;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedPostAdConfirmationScreenView implements View {
    private Gate<ConfirmationScreenResults> completeWithResult = new Gate();
    private Gate<Void> showFailedPostDialog = new Gate();
    private Gate<Void> showPostOnHoldDialog = new Gate();
    private Gate<SuccessPostResult> showSuccessPostDialog = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedPostAdConfirmationScreenView$$Lambda$1.lambdaFactory$(this));
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

    protected void open(View view) {
        this.showFailedPostDialog.open(GatedPostAdConfirmationScreenView$$Lambda$2.lambdaFactory$(view));
        this.showPostOnHoldDialog.open(GatedPostAdConfirmationScreenView$$Lambda$3.lambdaFactory$(view));
        this.showSuccessPostDialog.open(GatedPostAdConfirmationScreenView$$Lambda$4.lambdaFactory$(view));
        this.completeWithResult.open(GatedPostAdConfirmationScreenView$$Lambda$5.lambdaFactory$(view));
    }

    protected void close() {
        this.showFailedPostDialog.close();
        this.showPostOnHoldDialog.close();
        this.showSuccessPostDialog.close();
        this.completeWithResult.close();
    }

    public void showFailedPost() {
        this.showFailedPostDialog.perform(null);
    }

    public void showPostOnHold() {
        this.showPostOnHoldDialog.perform(null);
    }

    public void showSuccessPost(SuccessPostResult successPostResult) {
        this.showSuccessPostDialog.perform(successPostResult);
    }

    public void completeWithResult(ConfirmationScreenResults confirmationScreenResults) {
        this.completeWithResult.perform(confirmationScreenResults);
    }
}

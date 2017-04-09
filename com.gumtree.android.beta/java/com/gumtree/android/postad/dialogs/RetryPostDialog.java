package com.gumtree.android.postad.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import org.apache.commons.lang3.Validate;

public class RetryPostDialog {
    private Context context;
    private Listener listener;

    public interface Listener {
        void onPostWithoutImages();

        void onRetryPostWithImages();
    }

    public RetryPostDialog(@NonNull Context context, @NonNull Listener listener) {
        this.context = (Context) Validate.notNull(context);
        this.listener = (Listener) Validate.notNull(listener);
    }

    public void showDialog() {
        Builder builder = new Builder(this.context);
        builder.content(2131165705);
        builder.positiveText(2131165707);
        builder.onPositive(RetryPostDialog$$Lambda$1.lambdaFactory$(this));
        builder.neutralText(2131165706);
        builder.onNeutral(RetryPostDialog$$Lambda$2.lambdaFactory$(this));
        builder.autoDismiss(true);
        builder.build().show();
    }

    /* synthetic */ void lambda$showDialog$0(MaterialDialog materialDialog, DialogAction dialogAction) {
        this.listener.onRetryPostWithImages();
    }

    /* synthetic */ void lambda$showDialog$1(MaterialDialog materialDialog, DialogAction dialogAction) {
        this.listener.onPostWithoutImages();
    }
}

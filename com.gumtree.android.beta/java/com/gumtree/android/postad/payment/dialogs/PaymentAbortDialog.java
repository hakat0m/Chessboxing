package com.gumtree.android.postad.payment.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import org.apache.commons.lang3.Validate;

public class PaymentAbortDialog {
    private Context context;
    private Listener listener;

    public interface Listener {
        void onAbortConfirmed();
    }

    public PaymentAbortDialog(@NonNull Context context, @NonNull Listener listener) {
        this.context = (Context) Validate.notNull(context);
        this.listener = (Listener) Validate.notNull(listener);
    }

    public void showDialog() {
        Builder builder = new Builder(this.context);
        builder.iconRes(2130837801);
        builder.title(2131165924);
        builder.content(2131165624);
        builder.positiveText(2131165351);
        builder.onPositive(PaymentAbortDialog$$Lambda$1.lambdaFactory$(this));
        builder.negativeText(2131165350);
        builder.autoDismiss(true);
        builder.build().show();
    }

    /* synthetic */ void lambda$showDialog$0(MaterialDialog materialDialog, DialogAction dialogAction) {
        this.listener.onAbortConfirmed();
    }
}

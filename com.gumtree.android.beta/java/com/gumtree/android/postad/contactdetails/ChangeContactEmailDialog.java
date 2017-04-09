package com.gumtree.android.postad.contactdetails;

import android.content.Context;
import android.support.annotation.NonNull;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import java.io.Serializable;
import org.apache.commons.lang3.Validate;

public class ChangeContactEmailDialog {
    private Context context;
    private Listener listener;

    public interface Listener extends Serializable {
        void onGoClick();
    }

    public ChangeContactEmailDialog(@NonNull Context context, @NonNull Listener listener) {
        this.context = (Context) Validate.notNull(context);
        this.listener = (Listener) Validate.notNull(listener);
    }

    public void showDialog() {
        Builder builder = new Builder(this.context);
        builder.content(2131165626).title(2131165644).positiveText(2131165671).onPositive(ChangeContactEmailDialog$$Lambda$1.lambdaFactory$(this)).negativeText(2131165372).autoDismiss(true);
        builder.build().show();
    }

    /* synthetic */ void lambda$showDialog$0(MaterialDialog materialDialog, DialogAction dialogAction) {
        this.listener.onGoClick();
    }
}

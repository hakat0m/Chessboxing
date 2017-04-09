package com.gumtree.android.message_box.conversation;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;

public class DeletionDialog extends DialogFragment {
    private final OnClickListener doesNothing = DeletionDialog$$Lambda$1.lambdaFactory$();
    private OnClickListener listener;
    private final OnClickListener listenerDelegate = new 1(this);

    static /* synthetic */ void lambda$new$0(DialogInterface dialogInterface, int i) {
    }

    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog create = new Builder(getActivity()).setTitle(2131165417).setMessage(2131165413).setPositiveButton(2131165415, this.listenerDelegate).setNegativeButton(2131165414, this.doesNothing).setCancelable(true).create();
        create.setCanceledOnTouchOutside(true);
        return create;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (OnClickListener) activity;
    }

    public void onDetach() {
        this.listener = null;
        super.onDetach();
    }
}

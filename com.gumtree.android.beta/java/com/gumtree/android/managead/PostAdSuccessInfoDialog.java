package com.gumtree.android.managead;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog.Builder;

public class PostAdSuccessInfoDialog extends DialogFragment {
    private OnDismissListener listener;
    private final OnClickListener onOK = new 1(this);

    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (OnDismissListener) context;
    }

    public void onDetach() {
        this.listener = null;
        super.onDetach();
    }

    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        return new Builder(getActivity()).setTitle(2131165715).setMessage(2131165712).setPositiveButton(2131165670, this.onOK).setCancelable(true).create();
    }

    public void onCancel(DialogInterface dialogInterface) {
        this.listener.onDismiss(dialogInterface);
        super.onCancel(dialogInterface);
    }
}

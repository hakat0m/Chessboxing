package com.afollestad.materialdialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.afollestad.materialdialogs.internal.MDRootLayout;

class DialogBase extends Dialog implements OnShowListener {
    private OnShowListener mShowListener;
    protected MDRootLayout view;

    protected DialogBase(Context context, int i) {
        super(context, i);
    }

    public View findViewById(int i) {
        return this.view.findViewById(i);
    }

    public final void setOnShowListener(OnShowListener onShowListener) {
        this.mShowListener = onShowListener;
    }

    protected final void setOnShowListenerInternal() {
        super.setOnShowListener(this);
    }

    protected final void setViewInternal(View view) {
        super.setContentView(view);
    }

    public void onShow(DialogInterface dialogInterface) {
        if (this.mShowListener != null) {
            this.mShowListener.onShow(dialogInterface);
        }
    }

    @Deprecated
    public void setContentView(int i) throws IllegalAccessError {
        throw new IllegalAccessError("setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
    }

    @Deprecated
    public void setContentView(View view) throws IllegalAccessError {
        throw new IllegalAccessError("setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
    }

    @Deprecated
    public void setContentView(View view, LayoutParams layoutParams) throws IllegalAccessError {
        throw new IllegalAccessError("setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
    }
}

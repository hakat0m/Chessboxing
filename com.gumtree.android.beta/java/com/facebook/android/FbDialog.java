package com.facebook.android;

import android.content.Context;
import android.os.Bundle;
import com.facebook.FacebookDialogException;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.widget.WebDialog;

@Deprecated
public class FbDialog extends WebDialog {
    private DialogListener mListener;

    public FbDialog(Context context, String str, DialogListener dialogListener) {
        this(context, str, dialogListener, 16973840);
    }

    public FbDialog(Context context, String str, DialogListener dialogListener, int i) {
        super(context, str, i);
        setDialogListener(dialogListener);
    }

    public FbDialog(Context context, String str, Bundle bundle, DialogListener dialogListener) {
        super(context, str, bundle, 16973840, null);
        setDialogListener(dialogListener);
    }

    public FbDialog(Context context, String str, Bundle bundle, DialogListener dialogListener, int i) {
        super(context, str, bundle, i, null);
        setDialogListener(dialogListener);
    }

    private void setDialogListener(DialogListener dialogListener) {
        this.mListener = dialogListener;
        setOnCompleteListener(new 1(this));
    }

    private void callDialogListener(Bundle bundle, FacebookException facebookException) {
        if (this.mListener != null) {
            if (bundle != null) {
                this.mListener.onComplete(bundle);
            } else if (facebookException instanceof FacebookDialogException) {
                FacebookDialogException facebookDialogException = (FacebookDialogException) facebookException;
                this.mListener.onError(new DialogError(facebookDialogException.getMessage(), facebookDialogException.getErrorCode(), facebookDialogException.getFailingUrl()));
            } else if (facebookException instanceof FacebookOperationCanceledException) {
                this.mListener.onCancel();
            } else {
                this.mListener.onFacebookError(new FacebookError(facebookException.getMessage()));
            }
        }
    }
}

package com.ebay.kleinanzeigen.imagepicker.permissions;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import com.ebay.kleinanzeigen.imagepicker.R$string;

public class PermissionsDialogFragment extends DialogFragment {
    private static final String KEY_TEXT = "permissionText";
    private static final String KEY_TITLE = "titleText";
    private static Context context;
    private int permissionText;

    public static PermissionsDialogFragment newInstance(Context context, int i, int i2) {
        context = context;
        PermissionsDialogFragment permissionsDialogFragment = new PermissionsDialogFragment();
        permissionsDialogFragment.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TITLE, i);
        bundle.putInt(KEY_TEXT, i2);
        permissionsDialogFragment.setArguments(bundle);
        return permissionsDialogFragment;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        int i = getArguments().getInt(KEY_TITLE);
        this.permissionText = getArguments().getInt(KEY_TEXT);
        Dialog alertDialog = getAlertDialog(i);
        alertDialog.setCancelable(false);
        this.permissionText = getArguments().getInt(KEY_TEXT);
        return alertDialog;
    }

    private Dialog getAlertDialog(int i) {
        return new Builder(getActivity()).setTitle(i).setMessage(this.permissionText).setPositiveButton(R$string.ebk_image_picker_settings_text, new 2(this)).setNegativeButton(R$string.ebk_image_picker_cancel, new 1(this)).setCancelable(false).create();
    }
}

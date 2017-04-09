package com.gumtree.android.common.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog.Builder;

public class NeverAskAgainPermissionsDialogFragment extends DialogFragment {
    private static final String KEY_TEXT = "com.gumtree.android.common.fragments.permission_text";
    private static final String KEY_TITLE = "com.gumtree.android.common.fragments.permission_title";
    private static final String PACKAGE = "package";

    public static NeverAskAgainPermissionsDialogFragment newInstance(@StringRes int i, @StringRes int i2) {
        NeverAskAgainPermissionsDialogFragment neverAskAgainPermissionsDialogFragment = new NeverAskAgainPermissionsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TITLE, i);
        bundle.putInt(KEY_TEXT, i2);
        neverAskAgainPermissionsDialogFragment.setArguments(bundle);
        return neverAskAgainPermissionsDialogFragment;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        int i = getArguments().getInt(KEY_TITLE);
        return new Builder(getActivity()).setTitle(i).setMessage(getArguments().getInt(KEY_TEXT)).setPositiveButton(2131165677, NeverAskAgainPermissionsDialogFragment$$Lambda$1.lambdaFactory$(this)).setNegativeButton(2131165676, NeverAskAgainPermissionsDialogFragment$$Lambda$2.lambdaFactory$(this)).create();
    }

    /* synthetic */ void lambda$onCreateDialog$0(DialogInterface dialogInterface, int i) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(new Uri.Builder().scheme(PACKAGE).opaquePart(getActivity().getPackageName()).build());
        intent.addFlags(268435456);
        intent.addFlags(1073741824);
        intent.addFlags(8388608);
        startActivity(intent);
    }

    /* synthetic */ void lambda$onCreateDialog$1(DialogInterface dialogInterface, int i) {
        dismiss();
    }
}

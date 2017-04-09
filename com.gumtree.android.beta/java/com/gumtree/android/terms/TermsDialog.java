package com.gumtree.android.terms;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import com.gumtree.android.GumtreeApplication;

public class TermsDialog extends DialogFragment {
    public static final int VIEW_SPACING = 20;
    private Context context;

    public static TermsDialog newInstance() {
        return new TermsDialog();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity.getApplicationContext();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setCancelable(false);
    }

    public Dialog onCreateDialog(Bundle bundle) {
        Dialog create = new Builder(getActivity()).setIcon(getAppIcon()).setTitle(2131165371).setPositiveButton(2131165588, TermsDialog$$Lambda$1.lambdaFactory$(this)).setNegativeButton(2131165593, TermsDialog$$Lambda$2.lambdaFactory$(this)).create();
        create.setView(getMessageView(), VIEW_SPACING, VIEW_SPACING, VIEW_SPACING, VIEW_SPACING);
        return create;
    }

    /* synthetic */ void lambda$onCreateDialog$0(DialogInterface dialogInterface, int i) {
        GumtreeApplication.setFirstInstallStatus(false, getActivity());
    }

    /* synthetic */ void lambda$onCreateDialog$1(DialogInterface dialogInterface, int i) {
        GumtreeApplication.setFirstInstallStatus(true, getActivity());
        getActivity().finish();
    }

    private TextView getMessageView() {
        TextView textView = new TextView(getActivity(), null, 2131362157);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(Html.fromHtml(getString(2131165865)));
        return textView;
    }

    private Drawable getAppIcon() {
        return getActivity().getApplicationInfo().loadIcon(getActivity().getPackageManager());
    }
}

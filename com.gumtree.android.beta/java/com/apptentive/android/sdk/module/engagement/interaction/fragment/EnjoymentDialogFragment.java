package com.apptentive.android.sdk.module.engagement.interaction.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.apptentive.android.sdk.R;
import com.apptentive.android.sdk.module.engagement.EngagementModule;
import com.apptentive.android.sdk.module.engagement.interaction.model.EnjoymentDialogInteraction;

public class EnjoymentDialogFragment extends ApptentiveBaseFragment<EnjoymentDialogInteraction> implements OnClickListener {
    private static final String CODE_POINT_CANCEL = "cancel";
    private static final String CODE_POINT_DISMISS = "dismiss";
    private static final String CODE_POINT_NO = "no";
    private static final String CODE_POINT_YES = "yes";

    public static EnjoymentDialogFragment newInstance(Bundle bundle) {
        EnjoymentDialogFragment enjoymentDialogFragment = new EnjoymentDialogFragment();
        enjoymentDialogFragment.setArguments(bundle);
        return enjoymentDialogFragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.apptentive_enjoyment_dialog_interaction, viewGroup, false);
        ((TextView) inflate.findViewById(R.id.title)).setText(((EnjoymentDialogInteraction) this.interaction).getTitle());
        CharSequence noText = ((EnjoymentDialogInteraction) this.interaction).getNoText();
        Button button = (Button) inflate.findViewById(R.id.no);
        if (noText != null) {
            button.setText(noText);
        }
        button.setOnClickListener(this);
        noText = ((EnjoymentDialogInteraction) this.interaction).getYesText();
        button = (Button) inflate.findViewById(R.id.yes);
        button.setActivated(true);
        if (noText != null) {
            button.setText(noText);
        }
        button.setOnClickListener(this);
        boolean showDismissButton = ((EnjoymentDialogInteraction) this.interaction).showDismissButton();
        CharSequence dismissText = ((EnjoymentDialogInteraction) this.interaction).getDismissText();
        ImageButton imageButton = (ImageButton) inflate.findViewById(R.id.dismiss);
        if (showDismissButton) {
            if (dismissText != null) {
                imageButton.setContentDescription(dismissText);
            }
            imageButton.setVisibility(0);
        }
        imageButton.setOnClickListener(this);
        return inflate;
    }

    public boolean onBackPressed(boolean z) {
        EngagementModule.engageInternal(getActivity(), this.interaction, CODE_POINT_CANCEL);
        return false;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.yes) {
            EngagementModule.engageInternal(getActivity(), this.interaction, CODE_POINT_YES);
        } else if (id == R.id.no) {
            EngagementModule.engageInternal(getActivity(), this.interaction, CODE_POINT_NO);
        } else if (id == R.id.dismiss) {
            EngagementModule.engageInternal(getActivity(), this.interaction, CODE_POINT_DISMISS);
        }
        transit();
    }
}

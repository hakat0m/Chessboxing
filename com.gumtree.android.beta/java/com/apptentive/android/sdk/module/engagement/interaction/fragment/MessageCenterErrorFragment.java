package com.apptentive.android.sdk.module.engagement.interaction.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.apptentive.android.sdk.Apptentive;
import com.apptentive.android.sdk.ApptentiveInternal;
import com.apptentive.android.sdk.R;
import com.apptentive.android.sdk.model.ExtendedData;
import com.apptentive.android.sdk.module.engagement.EngagementModule;
import com.apptentive.android.sdk.module.engagement.interaction.model.Interaction;
import com.apptentive.android.sdk.util.Util;

public class MessageCenterErrorFragment extends ApptentiveBaseFragment<Interaction> {
    private static final String EVENT_NAME_NO_INTERACTION_ATTEMPTING = "no_interaction_attempting";
    private static final String EVENT_NAME_NO_INTERACTION_CLOSE = "no_interaction_close";
    private static final String EVENT_NAME_NO_INTERACTION_NO_INTERNET = "no_interaction_no_internet";
    private View progress;
    private View root;

    public static MessageCenterErrorFragment newInstance(Bundle bundle) {
        MessageCenterErrorFragment messageCenterErrorFragment = new MessageCenterErrorFragment();
        messageCenterErrorFragment.setArguments(bundle);
        return messageCenterErrorFragment;
    }

    protected void sendLaunchEvent(Activity activity) {
        if (wasLastAttemptServerError(getContext()) || Util.isNetworkConnectionPresent()) {
            EngagementModule.engage(getActivity(), "com.apptentive", "MessageCenter", null, EVENT_NAME_NO_INTERACTION_ATTEMPTING, null, null, (ExtendedData[]) null);
            return;
        }
        EngagementModule.engage(getActivity(), "com.apptentive", "MessageCenter", null, EVENT_NAME_NO_INTERACTION_NO_INTERNET, null, null, (ExtendedData[]) null);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.root = layoutInflater.inflate(R.layout.apptentive_message_center_error, viewGroup, false);
        this.progress = this.root.findViewById(R.id.config_loading_progress);
        return this.root;
    }

    public void onResume() {
        super.onResume();
        updateStatus();
    }

    public void onInteractionUpdated(boolean z) {
        if (z && Apptentive.canShowMessageCenter()) {
            ApptentiveInternal.getInstance().showMessageCenterInternal(getActivity(), null);
            transit();
            return;
        }
        updateStatus();
    }

    private boolean wasLastAttemptServerError(Context context) {
        return context.getSharedPreferences("APPTENTIVE", 0).getBoolean("messageCenterServerErrorLastAttempt", false);
    }

    public boolean onBackPressed() {
        EngagementModule.engage(getActivity(), "com.apptentive", "MessageCenter", null, EVENT_NAME_NO_INTERACTION_CLOSE, null, null, (ExtendedData[]) null);
        return false;
    }

    private void updateStatus() {
        if (wasLastAttemptServerError(getContext()) || Util.isNetworkConnectionPresent()) {
            this.progress.setVisibility(0);
            ((ImageView) this.root.findViewById(R.id.icon)).setImageResource(R.drawable.apptentive_icon_server_error);
            ((TextView) this.root.findViewById(R.id.message)).setText(R.string.apptentive_message_center_server_error);
            return;
        }
        this.progress.setVisibility(8);
        ((ImageView) this.root.findViewById(R.id.icon)).setImageResource(R.drawable.apptentive_icon_no_connection);
        ((TextView) this.root.findViewById(R.id.message)).setText(R.string.apptentive_message_center_no_connection);
    }
}

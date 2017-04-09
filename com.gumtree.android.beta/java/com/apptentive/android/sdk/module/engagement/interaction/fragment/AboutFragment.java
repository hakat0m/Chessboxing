package com.apptentive.android.sdk.module.engagement.interaction.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.apptentive.android.sdk.R;
import com.apptentive.android.sdk.model.ExtendedData;
import com.apptentive.android.sdk.module.engagement.EngagementModule;
import com.apptentive.android.sdk.module.engagement.interaction.model.Interaction;
import com.apptentive.android.sdk.util.Util;

public class AboutFragment extends ApptentiveBaseFragment<Interaction> {
    private static final String EVENT_NAME_CANCEL = "cancel";
    private static final String EVENT_NAME_CLOSE = "close";
    private static final String INTERACTION_NAME = "About";
    private View root;
    private boolean showBrandingBand;

    public static AboutFragment newInstance(Bundle bundle) {
        AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.setArguments(bundle);
        return aboutFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.showBrandingBand = arguments.getBoolean("fragmentExtraData");
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getActivity().getTheme().applyStyle(R.style.ApptentiveThemeAbout, true);
        this.root = layoutInflater.inflate(R.layout.apptentive_about, viewGroup, false);
        final String packageName = getActivity().getPackageName();
        if (!this.showBrandingBand) {
            this.root.findViewById(R.id.apptentive_branding_view).setVisibility(8);
        }
        this.root.findViewById(R.id.close_about).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                EngagementModule.engage(AboutFragment.this.getActivity(), "com.apptentive", AboutFragment.INTERACTION_NAME, null, AboutFragment.EVENT_NAME_CLOSE, null, null, (ExtendedData[]) null);
                AboutFragment.this.transit();
            }
        });
        ((Button) this.root.findViewById(R.id.about_description_link)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(String.format("http://www.apptentive.com/?source=%s", new Object[]{packageName})));
                if (Util.canLaunchIntent(AboutFragment.this.getActivity(), intent)) {
                    AboutFragment.this.getActivity().startActivity(intent);
                }
            }
        });
        ((Button) this.root.findViewById(R.id.privacy_link)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(String.format("http://www.apptentive.com/privacy/?source=%s", new Object[]{packageName})));
                if (Util.canLaunchIntent(AboutFragment.this.getActivity(), intent)) {
                    AboutFragment.this.getActivity().startActivity(intent);
                }
            }
        });
        return this.root;
    }

    public boolean onBackPressed(boolean z) {
        if (z) {
            EngagementModule.engage(getActivity(), "com.apptentive", INTERACTION_NAME, null, EVENT_NAME_CANCEL, null, null, (ExtendedData[]) null);
        } else {
            EngagementModule.engage(getActivity(), "com.apptentive", INTERACTION_NAME, null, EVENT_NAME_CLOSE, null, null, (ExtendedData[]) null);
        }
        return false;
    }

    protected void sendLaunchEvent(Activity activity) {
        EngagementModule.engage(getActivity(), "com.apptentive", INTERACTION_NAME, null, "launch", null, null, (ExtendedData[]) null);
    }
}

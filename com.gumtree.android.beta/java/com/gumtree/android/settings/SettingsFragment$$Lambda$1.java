package com.gumtree.android.settings;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SettingsFragment$$Lambda$1 implements OnConnectionFailedListener {
    private final SettingsFragment arg$1;

    private SettingsFragment$$Lambda$1(SettingsFragment settingsFragment) {
        this.arg$1 = settingsFragment;
    }

    public static OnConnectionFailedListener lambdaFactory$(SettingsFragment settingsFragment) {
        return new SettingsFragment$$Lambda$1(settingsFragment);
    }

    @Hidden
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.arg$1.lambda$signOutFromGoogleAndSmartLock$0(connectionResult);
    }
}

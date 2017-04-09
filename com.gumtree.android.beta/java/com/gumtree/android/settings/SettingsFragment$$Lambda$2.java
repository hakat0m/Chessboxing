package com.gumtree.android.settings;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SettingsFragment$$Lambda$2 implements OnConnectionFailedListener {
    private final SettingsFragment arg$1;

    private SettingsFragment$$Lambda$2(SettingsFragment settingsFragment) {
        this.arg$1 = settingsFragment;
    }

    public static OnConnectionFailedListener lambdaFactory$(SettingsFragment settingsFragment) {
        return new SettingsFragment$$Lambda$2(settingsFragment);
    }

    @Hidden
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.arg$1.lambda$smartLockDisableFailSafe$1(connectionResult);
    }
}

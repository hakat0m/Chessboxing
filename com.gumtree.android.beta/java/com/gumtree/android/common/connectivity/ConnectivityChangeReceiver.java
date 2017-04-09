package com.gumtree.android.common.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.gumtree.android.dagger.ComponentsManager;
import javax.inject.Inject;

public class ConnectivityChangeReceiver extends BroadcastReceiver {
    @Inject
    Network network;

    public ConnectivityChangeReceiver() {
        ComponentsManager.get().getAppComponent().inject(this);
    }

    public void onReceive(Context context, Intent intent) {
        this.network.updateNetworkState();
    }
}

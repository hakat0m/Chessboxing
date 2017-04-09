package com.gumtree.android.clients;

import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.clients.model.SoftUpdate;
import rx.Observable;

public class DefaultClientsService implements ClientsService {
    private final ClientsApi clientsApi;

    public DefaultClientsService(ClientsApi clientsApi) {
        this.clientsApi = clientsApi;
    }

    public Observable<SoftUpdate> getLatestVersion(SoftUpdateSettingsProvider softUpdateSettingsProvider) {
        return this.clientsApi.latestVersion(softUpdateSettingsProvider.getAppId(), softUpdateSettingsProvider.getVersionCode()).map(DefaultClientsService$$Lambda$1.lambdaFactory$(softUpdateSettingsProvider)).onErrorReturn(DefaultClientsService$$Lambda$4.lambdaFactory$(softUpdateSettingsProvider));
    }

    static /* synthetic */ SoftUpdate lambda$getLatestVersion$0(SoftUpdateSettingsProvider softUpdateSettingsProvider, LatestVersion latestVersion) {
        boolean z = 3 - softUpdateSettingsProvider.getShownCount() == 1;
        if (latestVersion == null) {
            return new SoftUpdate(false, z, BuildConfig.FLAVOR);
        }
        if (softUpdateSettingsProvider.getVersionCode() >= latestVersion.getCurrentVersion() || softUpdateSettingsProvider.isFirstInstall() || softUpdateSettingsProvider.getShownCount() >= 3) {
            return new SoftUpdate(false, z, latestVersion.getVersionMessage());
        }
        softUpdateSettingsProvider.incrementShownCount();
        return new SoftUpdate(true, z, latestVersion.getVersionMessage());
    }

    static /* synthetic */ SoftUpdate lambda$getLatestVersion$1(SoftUpdateSettingsProvider softUpdateSettingsProvider, Throwable th) {
        if (softUpdateSettingsProvider.isDebug()) {
            th.printStackTrace();
        }
        return new SoftUpdate();
    }
}

package com.gumtree.android.clients;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.gumtree.android.clients.model.SoftUpdate;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.events.EventBus;
import javax.inject.Inject;

@Deprecated
public class SoftUpdateService extends IntentService {
    @Inject
    EventBus bus;
    @Inject
    SoftUpdateSettingsProvider provider;
    @Inject
    ClientsService service;

    public SoftUpdateService() {
        super(SoftUpdateService.class.getSimpleName());
        ComponentsManager.get().getAppComponent().inject(this);
    }

    public static void start(Context context) {
        context.startService(new Intent(context, SoftUpdateService.class));
    }

    protected void onHandleIntent(Intent intent) {
        this.bus.post((SoftUpdate) this.service.getLatestVersion(this.provider).toBlocking().first());
    }
}

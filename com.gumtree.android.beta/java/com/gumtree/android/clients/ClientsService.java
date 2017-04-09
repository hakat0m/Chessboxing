package com.gumtree.android.clients;

import com.gumtree.android.clients.model.SoftUpdate;
import rx.Observable;

public interface ClientsService {
    Observable<SoftUpdate> getLatestVersion(SoftUpdateSettingsProvider softUpdateSettingsProvider);
}

package com.gumtree.android.services;

import rx.Observable;

public interface NetworkStateService {
    Observable<NetworkState> getNetworkState();
}

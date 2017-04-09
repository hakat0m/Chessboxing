package com.gumtree.android.common.connectivity;

import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.apptentive.android.sdk.R;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.logging.Timber;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import com.squareup.otto.Produce;
import javax.inject.Inject;

public class Network {
    @Inject
    Context context;
    @Inject
    EventBus eventBus;
    private NetworkState networkState;

    public Network() {
        ComponentsManager.get().getAppComponent().inject(this);
        this.eventBus.register(this);
    }

    @Produce
    public NetworkState produceNetworkState() {
        if (this.networkState == null) {
            this.networkState = getNetworkStateFromSystem();
        }
        return this.networkState;
    }

    public void updateNetworkState() {
        NetworkState networkStateFromSystem = getNetworkStateFromSystem();
        if (!networkStateFromSystem.equals(this.networkState)) {
            this.networkState = networkStateFromSystem;
            this.eventBus.post(this.networkState);
        }
    }

    public void enableConnectivityChangeReceiver() {
        this.context.getPackageManager().setComponentEnabledSetting(new ComponentName(this.context, ConnectivityChangeReceiver.class), 1, 1);
    }

    public void disableConnectivityChangeReceiver() {
        this.context.getPackageManager().setComponentEnabledSetting(new ComponentName(this.context, ConnectivityChangeReceiver.class), 2, 1);
    }

    private NetworkState getNetworkStateFromSystem() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting()) {
            return NetworkState.OFFLINE;
        }
        switch (activeNetworkInfo.getType()) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                switch (activeNetworkInfo.getSubtype()) {
                    case HighlightView.GROW_NONE /*1*/:
                    case HighlightView.GROW_LEFT_EDGE /*2*/:
                        return NetworkState.ONLINE_2G;
                    case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                    case HighlightView.GROW_RIGHT_EDGE /*4*/:
                    case Timber.WARN /*5*/:
                    case Timber.ERROR /*6*/:
                    case R.styleable.Toolbar_titleTextAppearance /*12*/:
                        return NetworkState.ONLINE_3G;
                    case R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                    case R.styleable.TabLayout_tabPaddingBottom /*14*/:
                    case R.styleable.Toolbar_titleMarginStart /*15*/:
                        return NetworkState.ONLINE_4G;
                    default:
                        return NetworkState.ONLINE_2G;
                }
            case HighlightView.GROW_NONE /*1*/:
            case Timber.ERROR /*6*/:
            case R.styleable.TextInputLayout_counterOverflowTextAppearance /*9*/:
                return NetworkState.ONLINE_WIFI;
            default:
                return NetworkState.ONLINE_2G;
        }
    }
}

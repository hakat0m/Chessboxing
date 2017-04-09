package com.gumtree.android.services;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import com.apptentive.android.sdk.R;
import com.gumtree.android.logging.Timber;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import rx.Observable;
import rx.Scheduler.Worker;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

public class SimpleNetworkStateService implements NetworkStateService {
    private Context context;

    public SimpleNetworkStateService(Context context) {
        this.context = context;
    }

    public Observable<NetworkState> getNetworkState() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        return Observable.create(new 1(this, intentFilter));
    }

    private NetworkState getNetworkStateFromDevice() {
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

    private Subscription unsubscribeNetworkState(Action0 action0) {
        return Subscriptions.create(SimpleNetworkStateService$$Lambda$1.lambdaFactory$(action0));
    }

    static /* synthetic */ void lambda$unsubscribeNetworkState$1(Action0 action0) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            action0.call();
            return;
        }
        Worker createWorker = AndroidSchedulers.mainThread().createWorker();
        createWorker.schedule(SimpleNetworkStateService$$Lambda$2.lambdaFactory$(action0, createWorker));
    }

    static /* synthetic */ void lambda$null$0(Action0 action0, Worker worker) {
        action0.call();
        worker.unsubscribe();
    }
}

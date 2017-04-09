package com.gumtree.android.login.google;

import android.app.Activity;
import com.gumtree.android.login.google.DefaultGoogleService.OnConnected;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class DefaultGoogleService$$Lambda$1 implements OnConnected {
    private final DefaultGoogleService arg$1;
    private final Activity arg$2;

    private DefaultGoogleService$$Lambda$1(DefaultGoogleService defaultGoogleService, Activity activity) {
        this.arg$1 = defaultGoogleService;
        this.arg$2 = activity;
    }

    public static OnConnected lambdaFactory$(DefaultGoogleService defaultGoogleService, Activity activity) {
        return new DefaultGoogleService$$Lambda$1(defaultGoogleService, activity);
    }

    @Hidden
    public void connected() {
        this.arg$1.lambda$requestHint$0(this.arg$2);
    }
}

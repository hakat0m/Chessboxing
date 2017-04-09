package com.gumtree.android.clients;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultClientsService$$Lambda$4 implements Func1 {
    private final SoftUpdateSettingsProvider arg$1;

    private DefaultClientsService$$Lambda$4(SoftUpdateSettingsProvider softUpdateSettingsProvider) {
        this.arg$1 = softUpdateSettingsProvider;
    }

    public static Func1 lambdaFactory$(SoftUpdateSettingsProvider softUpdateSettingsProvider) {
        return new DefaultClientsService$$Lambda$4(softUpdateSettingsProvider);
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultClientsService.lambda$getLatestVersion$1(this.arg$1, (Throwable) obj);
    }
}

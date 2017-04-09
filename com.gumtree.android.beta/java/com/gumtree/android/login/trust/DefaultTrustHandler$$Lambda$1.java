package com.gumtree.android.login.trust;

import com.gumtree.android.auth.trust.TrustHandler.Callback;
import com.threatmetrix.TrustDefender.EndNotifier;
import com.threatmetrix.TrustDefender.ProfilingResult;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class DefaultTrustHandler$$Lambda$1 implements EndNotifier {
    private final Callback arg$1;

    private DefaultTrustHandler$$Lambda$1(Callback callback) {
        this.arg$1 = callback;
    }

    public static EndNotifier lambdaFactory$(Callback callback) {
        return new DefaultTrustHandler$$Lambda$1(callback);
    }

    @Hidden
    public void complete(ProfilingResult profilingResult) {
        DefaultTrustHandler.lambda$doProfileRequest$0(this.arg$1, profilingResult);
    }
}

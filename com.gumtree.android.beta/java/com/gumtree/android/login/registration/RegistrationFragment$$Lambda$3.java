package com.gumtree.android.login.registration;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class RegistrationFragment$$Lambda$3 implements OnFocusChangeListener {
    private final RegistrationFragment arg$1;

    private RegistrationFragment$$Lambda$3(RegistrationFragment registrationFragment) {
        this.arg$1 = registrationFragment;
    }

    public static OnFocusChangeListener lambdaFactory$(RegistrationFragment registrationFragment) {
        return new RegistrationFragment$$Lambda$3(registrationFragment);
    }

    @Hidden
    public void onFocusChange(View view, boolean z) {
        this.arg$1.lambda$onViewCreated$2(view, z);
    }
}

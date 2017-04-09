package com.gumtree.android.login.registration;

import com.gumtree.android.postad.views.EditTextSummaryValidationView.OnFieldValueChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class RegistrationFragment$$Lambda$6 implements OnFieldValueChangeListener {
    private final RegistrationFragment arg$1;

    private RegistrationFragment$$Lambda$6(RegistrationFragment registrationFragment) {
        this.arg$1 = registrationFragment;
    }

    public static OnFieldValueChangeListener lambdaFactory$(RegistrationFragment registrationFragment) {
        return new RegistrationFragment$$Lambda$6(registrationFragment);
    }

    @Hidden
    public void onFieldValueChange(String str) {
        this.arg$1.lambda$onViewCreated$5(str);
    }
}

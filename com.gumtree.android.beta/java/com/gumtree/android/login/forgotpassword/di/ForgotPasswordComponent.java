package com.gumtree.android.login.forgotpassword.di;

import com.gumtree.android.auth.forgotpassword.services.TrackingForgotPasswordService;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.login.forgotpassword.ForgotPasswordActivity;
import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {ForgotPasswordModule.class})
@ForgotPasswordScope
public interface ForgotPasswordComponent extends BaseComponent {
    public static final String KEY = ForgotPasswordComponent.class.getSimpleName();

    void inject(ForgotPasswordActivity forgotPasswordActivity);

    TrackingForgotPasswordService trackingForgotPasswordService();
}

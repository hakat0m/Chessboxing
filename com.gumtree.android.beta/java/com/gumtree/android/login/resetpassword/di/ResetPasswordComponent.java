package com.gumtree.android.login.resetpassword.di;

import com.gumtree.android.auth.resetpassword.services.TrackingResetPasswordService;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.login.resetpassword.ResetPasswordActivity;
import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {ResetPasswordModule.class})
@ResetPasswordScope
public interface ResetPasswordComponent extends BaseComponent {
    public static final String KEY = ResetPasswordComponent.class.getSimpleName();

    void inject(ResetPasswordActivity resetPasswordActivity);

    TrackingResetPasswordService trackingResetPasswordService();
}

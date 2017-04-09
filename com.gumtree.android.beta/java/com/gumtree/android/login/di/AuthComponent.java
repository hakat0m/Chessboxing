package com.gumtree.android.login.di;

import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.login.AuthActivity;
import com.gumtree.android.login.facebook.FacebookLoginFragment;
import com.gumtree.android.login.google.GoogleLoginFragment;
import com.gumtree.android.login.login.LoginFragment;
import com.gumtree.android.login.registration.RegistrationFragment;
import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {AuthModule.class})
@AuthScope
public interface AuthComponent extends BaseComponent {
    public static final String KEY = AuthComponent.class.getSimpleName();

    void inject(AuthActivity authActivity);

    void inject(FacebookLoginFragment facebookLoginFragment);

    void inject(GoogleLoginFragment googleLoginFragment);

    void inject(LoginFragment loginFragment);

    void inject(RegistrationFragment registrationFragment);
}

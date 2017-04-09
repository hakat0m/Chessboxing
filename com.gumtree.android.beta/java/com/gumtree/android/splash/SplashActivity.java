package com.gumtree.android.splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.apptentive.android.sdk.BuildConfig;
import com.google.android.gms.auth.api.credentials.Credential;
import com.gumtree.android.auth.google.model.SmartLockCredential;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.splash.di.DaggerSplashComponent;
import com.gumtree.android.splash.di.SplashComponent;
import com.gumtree.android.splash.di.SplashModule;
import com.gumtree.android.splash.presenter.SplashPresenter;
import com.gumtree.android.splash.presenter.SplashPresenter.View;
import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements View {
    @Inject
    SplashPresenter presenter;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        init();
        setContentView(2130903113);
    }

    protected void onStart() {
        super.onStart();
        this.presenter.attachView(this);
        this.presenter.trySmartLockRequest(this);
    }

    private void init() {
        SplashComponent splashComponent = (SplashComponent) ComponentsManager.get().getBaseComponent(SplashComponent.KEY);
        if (splashComponent == null) {
            ApplicationComponent appComponent = ComponentsManager.get().getAppComponent();
            splashComponent = DaggerSplashComponent.builder().applicationComponent(appComponent).splashModule(new SplashModule()).build();
            ComponentsManager.get().putBaseComponent(SplashComponent.KEY, splashComponent);
        }
        splashComponent.inject(this);
    }

    protected void onStop() {
        super.onStop();
        this.presenter.onResolutionDone(this);
        this.presenter.detachView();
    }

    protected void onDestroy() {
        if (isFinishing()) {
            this.presenter.destroy();
            ComponentsManager.get().removeBaseComponent(SplashComponent.KEY);
        }
        super.onDestroy();
    }

    public void showSmartLockLoginFailure() {
        Toast.makeText(getApplicationContext(), getString(2131165787), 1).show();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case 911:
                SmartLockCredential smartLockCredential = new SmartLockCredential(BuildConfig.FLAVOR, BuildConfig.FLAVOR, BuildConfig.FLAVOR, false);
                if (i2 == -1) {
                    Credential credential = (Credential) intent.getParcelableExtra("com.google.android.gms.credentials.Credential");
                    this.presenter.performSmartLockLoginAfterResolve(this, new SmartLockCredential(credential.getId(), credential.getName(), credential.getPassword(), false), false);
                } else {
                    this.presenter.performSmartLockLoginAfterResolve(this, smartLockCredential, true);
                }
                this.presenter.onResolutionDone(this);
                return;
            default:
                return;
        }
    }
}

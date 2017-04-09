package com.gumtree.android.deeplinking;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.ButterKnife;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.auth.presenter.NavView;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.fragments.ProgressDialogFragment;
import com.gumtree.android.common.location.AppLocation;
import com.gumtree.android.common.location.GumtreeLocation;
import com.gumtree.android.common.location.PreferenceBasedRadiusDAO;
import com.gumtree.android.common.search.Search;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.deeplinking.presenter.DeepLinkingPresenter;
import com.gumtree.android.deeplinking.presenter.DeepLinkingView;
import com.gumtree.android.home.HomeActivity;
import com.gumtree.android.login.AuthActivity;
import com.gumtree.android.login.resetpassword.ResetPasswordActivity;
import com.gumtree.android.postad.PostAdActivity;
import javax.inject.Inject;

public class DeepLinkingActivity extends BaseActivity implements DeepLinkingView {
    private static final String KEY = DeepLinkingActivity.class.getCanonicalName();
    @BindString(2131165854)
    String defaultMessage;
    @Inject
    DeepLinkingPresenter presenter;

    @NonNull
    private Search createViewingSearch(GumtreeApplication gumtreeApplication) {
        AppLocation globalBuyerLocation = gumtreeApplication.getGlobalBuyerLocation();
        Search create = Search.create(gumtreeApplication);
        create.addRadius(gumtreeApplication, PreferenceBasedRadiusDAO.get(gumtreeApplication), globalBuyerLocation);
        return create;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ProgressDialogFragment.show(getSupportFragmentManager());
        ButterKnife.bind(this);
        injection();
        this.presenter.attachView(this);
    }

    protected void onStart() {
        super.onStart();
        this.presenter.analyse();
    }

    private void injection() {
        Object build = DaggerDeepLinkingComponent.builder().applicationComponent(ComponentsManager.get().getAppComponent()).deepLinkingModule(new DeepLinkingModule(getIntent().getDataString())).build();
        ComponentsManager.get().putBaseComponent(KEY, build);
        build.inject(this);
    }

    public void showSRP(SearchParameters searchParameters) {
        Search createViewingSearch = createViewingSearch((GumtreeApplication) getApplication());
        if (searchParameters.getQuery() != null) {
            createViewingSearch.addParameter(StatefulActivity.NAME_QUERY, searchParameters.getQuery());
        }
        if (searchParameters.getCategoryId() != null) {
            createViewingSearch.addCategory(searchParameters.getCategoryId(), searchParameters.getCategoryName());
        }
        if (searchParameters.getLocationId() != null) {
            createViewingSearch.addLocation(GumtreeLocation.get("keyword", searchParameters.getLocationId(), searchParameters.getLocationName(), "idName", "unspecified"));
        }
        Search.invoke(this, createViewingSearch);
        finish();
    }

    public void showPostAd(String str) {
        startActivity(PostAdActivity.createIntent(this, str));
        finish();
    }

    public void showResetPassword(String str, String str2, String str3) {
        if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
            Intent createIntent = ResetPasswordActivity.createIntent(str, str2, this);
            createIntent.setFlags(268468224);
            startActivity(createIntent);
            finish();
            return;
        }
        String str4 = getPackageManager().resolveActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://")), 65536).activityInfo.packageName;
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str3));
        intent.addFlags(268435456);
        intent.setPackage(str4);
        try {
            startActivity(intent);
            finish();
        } catch (ActivityNotFoundException e) {
            showHomeScreen();
        }
    }

    public void showHomeScreen() {
        ProgressDialogFragment.dismiss(getSupportFragmentManager());
        Intent createIntent = HomeActivity.createIntent(this);
        createIntent.setFlags(268468224);
        overridePendingTransition(0, 0);
        startActivity(createIntent);
        finish();
    }

    public void showError(String str) {
        if (str == null) {
            str = this.defaultMessage;
        }
        Toast.makeText(getApplicationContext(), str, 1).show();
    }

    public void showLogin() {
        ProgressDialogFragment.dismiss(getSupportFragmentManager());
        Intent createIntent = AuthenticatorActivity.createIntent(this, 7, BuildConfig.FLAVOR, BuildConfig.FLAVOR);
        if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
            createIntent = AuthActivity.createIntent(AuthIdentifier.ACTIVATE, NavView.LOGIN, this);
        }
        createIntent.setFlags(268468224);
        overridePendingTransition(0, 0);
        startActivity(createIntent);
        finish();
    }

    public void showRegistration() {
        ProgressDialogFragment.dismiss(getSupportFragmentManager());
        if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
            Intent createIntent = AuthActivity.createIntent(AuthIdentifier.ACTIVATE_FAIL, NavView.REGISTRATION, this);
            createIntent.setFlags(268468224);
            overridePendingTransition(0, 0);
            startActivity(createIntent);
            finish();
            return;
        }
        showHomeScreen();
    }
}

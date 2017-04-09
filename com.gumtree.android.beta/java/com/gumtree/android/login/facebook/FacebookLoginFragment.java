package com.gumtree.android.login.facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.ebay.classifieds.capi.users.login.LoginApi;
import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.gumtree.Log;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.FBHelper;
import com.gumtree.android.auth.facebook.presenter.FacebookLoginPresenter;
import com.gumtree.android.auth.facebook.presenter.FacebookLoginPresenter.View;
import com.gumtree.android.auth.model.AuthResult;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.fragments.ProgressDialogFragment;
import com.gumtree.android.common.providers.AppProvider;
import com.gumtree.android.login.di.AuthModule;
import java.util.Arrays;
import javax.inject.Inject;

public class FacebookLoginFragment extends BaseFragment implements View {
    private static final String EMAIL = "email";
    private static final int FACEBOOK_SPAN_START = 42;
    private static final String FIELDS = "fields";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String REQUEST_FIELDS = TextUtils.join(",", new String[]{ID, NAME, EMAIL});
    @Bind({2131624464})
    LoginButton authButton;
    private StatusCallback callback = FacebookLoginFragment$$Lambda$1.lambdaFactory$(this);
    @Bind({2131624465})
    TextView facebook_login_text;
    @Inject
    FacebookLoginPresenter presenter;
    private Session session;
    private UiLifecycleHelper uiHelper;

    /* synthetic */ void lambda$new$0(Session session, SessionState sessionState, Exception exception) {
        onSessionStateChange(session, sessionState, exception);
    }

    @Nullable
    public android.view.View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(2130903198, viewGroup, false);
    }

    public void onViewCreated(android.view.View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        AuthModule.inject(this);
        ButterKnife.bind(this, view);
        initAuthButton();
        initFacebookLoginText();
    }

    private void initFacebookLoginText() {
        CharSequence spannableString = new SpannableString(getString(2131165658));
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(2131493352)), FACEBOOK_SPAN_START, spannableString.length(), 33);
        this.facebook_login_text.setText(spannableString);
    }

    private void initAuthButton() {
        this.authButton.setFragment(this);
        this.authButton.setReadPermissions(Arrays.asList(new String[]{EMAIL}));
        this.authButton.setBackgroundResource(2130837784);
        this.authButton.setOnErrorListener(FacebookLoginFragment$$Lambda$2.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$initAuthButton$1(FacebookException facebookException) {
        Log.i("FB-Social", "Error: " + facebookException.getMessage() + " Cause: " + facebookException.getCause());
        this.authButton.setEnabled(true);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.uiHelper = new UiLifecycleHelper(getActivity(), this.callback);
        this.uiHelper.onCreate(bundle);
    }

    public void onResume() {
        super.onResume();
        this.presenter.attachView(this);
        Session activeSession = Session.getActiveSession();
        if (activeSession != null && (activeSession.isOpened() || activeSession.isClosed())) {
            onSessionStateChange(activeSession, activeSession.getState(), null);
        }
        this.uiHelper.onResume();
    }

    public void onPause() {
        super.onPause();
        this.presenter.detachView();
        this.uiHelper.onPause();
    }

    public void onDestroy() {
        this.uiHelper.onDestroy();
        if (isAdded() && getActivity().isFinishing()) {
            this.presenter.destroy();
        }
        super.onDestroy();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.uiHelper.onSaveInstanceState(bundle);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.uiHelper.onActivityResult(i, i2, intent);
    }

    @OnClick({2131624465})
    void onFacebookLoginClick() {
        this.authButton.performClick();
    }

    private void onSessionStateChange(Session session, SessionState sessionState, Exception exception) {
        if (!sessionState.isOpened()) {
            return;
        }
        if (this.session == null || isSessionChanged(session)) {
            this.session = session;
            fetchUserInfo(session);
        }
    }

    private boolean isSessionChanged(Session session) {
        if (this.session.getState() != session.getState()) {
            return true;
        }
        if (this.session.getAccessToken() != null) {
            if (!this.session.getAccessToken().equals(session.getAccessToken())) {
                return true;
            }
        } else if (session.getAccessToken() != null) {
            return true;
        }
        return false;
    }

    private void fetchUserInfo(Session session) {
        if (session != null && session.isOpened()) {
            this.authButton.setEnabled(false);
            Request newMeRequest = Request.newMeRequest(session, FacebookLoginFragment$$Lambda$3.lambdaFactory$(this, session));
            Bundle bundle = new Bundle();
            bundle.putString(FIELDS, REQUEST_FIELDS);
            newMeRequest.setParameters(bundle);
            Request.executeBatchAsync(newMeRequest);
        }
    }

    /* synthetic */ void lambda$fetchUserInfo$2(Session session, GraphUser graphUser, Response response) {
        if (graphUser == null || graphUser.getProperty(EMAIL) == null || TextUtils.isEmpty(graphUser.getProperty(EMAIL).toString())) {
            FBHelper.revokeFBPermissions();
            this.authButton.setEnabled(true);
            showError(this.context.getString(2131165441));
            return;
        }
        this.presenter.login(graphUser.getProperty(EMAIL).toString(), session.getAccessToken(), LoginApi.REL_FACEBOOK);
    }

    public void onAccountAdded(AuthIdentifier authIdentifier, AuthResult authResult) {
        Intent intent = new Intent();
        intent.putExtra("authAccount", authResult.getUserName());
        intent.putExtra("password", authResult.getPassWord());
        intent.putExtra("accountType", AppProvider.AUTHORITY);
        intent.putExtra("com.gumtree.android.auth.save_search", AuthIdentifier.SAVE_A_SEARCH == authIdentifier);
        intent.putExtra("displayname", authResult.getUserProfile().getDisplayName());
        getActivity().setResult(-1, intent);
        finish();
    }

    public void showError(String str) {
        showSnackBar(str);
    }

    public void showNoNetworkError() {
        showSnackBar(getString(2131165438));
    }

    public void showProgress() {
        ProgressDialogFragment.show(getFragmentManager());
    }

    public void hideProgress() {
        ProgressDialogFragment.dismiss(getFragmentManager());
    }

    public void handleFBLogout() {
        FBHelper.logoutViaFacebook();
    }
}

package com.gumtree.android.vip;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.utils.AppUtils;
import com.gumtree.android.conversations.Conversation;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.message_box.conversation.ConversationActivity;
import com.gumtree.android.model.Ads;
import com.gumtree.android.model.Categories;
import com.gumtree.android.postad.di.DaggerVipComponent;
import com.gumtree.android.postad.di.VipComponent;
import com.gumtree.android.postad.di.VipModule;
import com.gumtree.android.ui.widget.RichButton;
import com.gumtree.android.ui.widget.SpinnerButton;
import com.gumtree.android.vip.model.Advert;
import com.gumtree.android.vip.model.VIPRefreshFragment;
import com.gumtree.android.vip.presenters.VipPresenter;
import com.gumtree.android.vip.presenters.VipPresenter.View;
import javax.inject.Inject;

public class VIPContactFragment extends BaseFragment implements LoaderCallbacks<Cursor>, VIPRefreshFragment, View {
    public static final String CATEGORY_ID = "category_id";
    private static final String EXTRA_IS_FROM_MESSAGES = "messages";
    private static final int FREESPEE_ENABLED = 1;
    private static final String SPACE = " ";
    private static final String VIP_ID = "vipId";
    @Inject
    BaseAccountManager accountManager;
    @Bind({2131624608})
    SpinnerButton buttonCall;
    @Bind({2131624609})
    Button buttonMessage;
    @Bind({2131624610})
    Button buttonReply;
    @Bind({2131624611})
    RichButton buttonWeb;
    private String categoryId;
    private String categoryName;
    private String contactName;
    private String contactPostingSince;
    protected String emailString;
    private boolean hasCvUpload;
    private int isFreespee;
    private boolean isFromMessages;
    private boolean isListSearch;
    private boolean isNearBySearch;
    protected String numberString;
    @Inject
    VipPresenter presenter;
    private String replyTemplate;
    private String replyUrl;
    @Bind({2131624605})
    android.view.View root;
    @Bind({2131624606})
    TextView textContactName;
    @Bind({2131624607})
    TextView textContactPostSince;
    private String title;
    private String userId;
    private long vipId;

    public static VIPContactFragment newInstance(long j, String str, boolean z, boolean z2, boolean z3) {
        VIPContactFragment vIPContactFragment = new VIPContactFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(VIP_ID, j);
        bundle.putBoolean(StatefulActivity.EXTRA_NEAR_BY_SEARCH, z);
        bundle.putBoolean(StatefulActivity.EXTRA_LIST_SEARCH, z2);
        bundle.putBoolean(EXTRA_IS_FROM_MESSAGES, z3);
        bundle.putString(CATEGORY_ID, str);
        vIPContactFragment.setArguments(bundle);
        return vIPContactFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.vipId = getArguments().getLong(VIP_ID);
        this.isNearBySearch = getArguments().getBoolean(StatefulActivity.EXTRA_NEAR_BY_SEARCH, false);
        this.isListSearch = getArguments().getBoolean(StatefulActivity.EXTRA_LIST_SEARCH, false);
        this.isFromMessages = getArguments().getBoolean(EXTRA_IS_FROM_MESSAGES, false);
        this.categoryId = getArguments().getString(CATEGORY_ID);
        getVipComponent().inject(this);
    }

    public android.view.View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130903242, viewGroup, false);
    }

    public void onViewCreated(android.view.View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ButterKnife.bind(this, view);
        showView(this.textContactPostSince, getResources().getBoolean(2131427334));
        displaySmsIfPhoneIsAvailable();
        renameReplyButtonIfRequired();
        this.buttonCall.setOnClickListener(VIPContactFragment$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$onViewCreated$0(android.view.View view) {
        onActionCall();
    }

    public void onStart() {
        super.onStart();
        this.presenter.attachView(this);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (bundle == null) {
            this.hasCvUpload = false;
        } else {
            this.hasCvUpload = bundle.getBoolean("has_cv_posting");
        }
    }

    public void onStop() {
        this.presenter.detachView();
        super.onStop();
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (getActivity().isFinishing()) {
            ComponentsManager.get().removeBaseComponent(VipComponent.KEY);
            this.presenter.destroy();
        }
    }

    private VipComponent getVipComponent() {
        VipComponent vipComponent = (VipComponent) ComponentsManager.get().getBaseComponent(VipComponent.KEY);
        if (vipComponent != null) {
            return vipComponent;
        }
        ApplicationComponent appComponent = ComponentsManager.get().getAppComponent();
        Object build = DaggerVipComponent.builder().applicationComponent(appComponent).vipModule(new VipModule(this.vipId)).build();
        ComponentsManager.get().putBaseComponent(VipComponent.KEY, build);
        return build;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("has_cv_posting", this.hasCvUpload);
    }

    private void displaySmsIfPhoneIsAvailable() {
        showView(this.buttonMessage, AppUtils.hasPhoneAbility(this.context));
    }

    private void setupActions() {
        boolean z = true;
        showView(this.buttonCall, isPhoneEnabled());
        android.view.View view = this.buttonReply;
        boolean z2 = isEmailEnabled() && !this.isFromMessages;
        showView(view, z2);
        showView(this.buttonWeb, isReplyUrlEnabled());
        android.view.View view2 = this.buttonMessage;
        if (!(isPhoneEnabled() && AppUtils.isCellphoneNumber(this.numberString))) {
            z = false;
        }
        showView(view2, z);
        setContactInfo(this.contactName, 2131165941);
        setContactPostingSinceInfo(this.contactPostingSince, 2131165942);
    }

    private void setContactPostingSinceInfo(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            this.textContactPostSince.setVisibility(8);
            return;
        }
        TextView textView = this.textContactPostSince;
        String string = this.context.getString(i);
        Object[] objArr = new Object[FREESPEE_ENABLED];
        objArr[0] = str;
        textView.setText(String.format(string, objArr));
        this.textContactPostSince.setVisibility(0);
    }

    private void setContactInfo(String str, int i) {
        if (str != null) {
            TextView textView = this.textContactName;
            String string = this.context.getString(i);
            Object[] objArr = new Object[FREESPEE_ENABLED];
            objArr[0] = str;
            textView.setText(String.format(string, objArr));
            this.textContactName.setVisibility(0);
            return;
        }
        this.textContactName.setVisibility(8);
    }

    private boolean isReplyUrlEnabled() {
        return (TextUtils.isEmpty(this.replyUrl) || Uri.parse(this.replyUrl) == null) ? false : true;
    }

    private boolean isEmailEnabled() {
        return !TextUtils.isEmpty(this.emailString);
    }

    private boolean isPhoneEnabled() {
        return !TextUtils.isEmpty(this.numberString);
    }

    @OnClick({2131624611})
    public void onActionReplyViaWeb() {
        if (isReplyUrlEnabled()) {
            Track.eventActionReplyWeb(this.categoryId, this.vipId, this.isNearBySearch, this.isListSearch);
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(this.replyUrl));
            intent.putExtra(StatefulActivity.EXTRA_NEAR_BY_SEARCH, this.isNearBySearch);
            intent.putExtra(StatefulActivity.EXTRA_LIST_SEARCH, this.isListSearch);
            startActivity(intent);
        }
    }

    @OnClick({2131624610})
    public void onActionEmail() {
        if (!this.accountManager.isUserLoggedIn() || this.hasCvUpload) {
            emailLoggedOutUser();
        } else {
            emailLoggedInUser();
        }
    }

    private void emailLoggedOutUser() {
        Track.eventEmailBegin(this.categoryId, this.vipId, this.isNearBySearch, this.isListSearch);
        Intent intent = new Intent("android.intent.action.SEND", Ads.URI.buildUpon().appendPath(BuildConfig.FLAVOR + this.vipId).build());
        intent.setPackage(this.context.getPackageName());
        intent.putExtra(StatefulActivity.EXTRA_NEAR_BY_SEARCH, this.isNearBySearch);
        intent.putExtra(StatefulActivity.EXTRA_LIST_SEARCH, this.isListSearch);
        intent.putExtra(StatefulActivity.EXTRA_CATEGORY_NAME, this.categoryName);
        intent.putExtra(StatefulActivity.NAME_CATEGORY_ID, this.categoryId);
        intent.putExtra(StatefulActivity.EXTRA_REPLY_TEMPLATE, this.replyTemplate);
        intent.putExtra(NewPostAdCategoryActivity.EXTRA_TITLE, this.title);
        startActivity(intent);
    }

    private void emailLoggedInUser() {
        new 1(this, getActivity(), this.accountManager).getLocalConversation(String.valueOf(this.vipId));
    }

    private void startConversation(Conversation conversation) {
        Track.eventActionChat(this.categoryId, this.vipId, this.isNearBySearch, this.isListSearch);
        startActivity(ConversationActivity.createIntent(getActivity(), conversation, false, ConversationActivity.VIP));
    }

    @OnClick({2131624609})
    public void onActionSMS() {
        try {
            Track.eventActionSms(this.categoryId, this.vipId, this.isNearBySearch, this.isListSearch);
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("smsto:" + this.numberString)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this.context, getString(2131165443) + SPACE + this.numberString, FREESPEE_ENABLED).show();
        }
    }

    public void onActionCall() {
        try {
            Track.eventActionCall(this.categoryId, this.vipId, this.isNearBySearch, this.isListSearch);
            if (this.isFreespee == FREESPEE_ENABLED) {
                this.presenter.onCallSelected(this.numberString);
            } else {
                showPhoneDial(this.numberString);
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this.context, getString(2131165440) + SPACE + this.numberString, FREESPEE_ENABLED).show();
        }
    }

    private void showView(android.view.View view, boolean z) {
        view.setVisibility(z ? 0 : 8);
    }

    public void refreshContent(Advert advert) {
        String string;
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_ID, advert.getCategoryId());
        getLoaderManager().restartLoader(0, bundle, this);
        this.numberString = advert.getPhone();
        this.emailString = advert.getReply();
        this.replyUrl = advert.getReplyUrl();
        this.contactName = advert.getContactName();
        if (this.contactName == null || this.contactName.length() <= 0) {
            string = getResources().getString(2131165944);
        } else {
            string = this.contactName;
        }
        this.contactName = string;
        this.contactPostingSince = advert.getContactPostingSince();
        this.categoryId = advert.getCategoryId();
        this.categoryName = advert.getCategoryName();
        this.replyTemplate = advert.getReplyTemplate();
        this.title = advert.getTitle();
        this.userId = advert.getUserId();
        this.isFreespee = advert.getFreespee();
        setupActions();
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String string = bundle.getString(CATEGORY_ID);
        Context context = getContext();
        Uri uri = Categories.URI;
        String[] strArr = new String[FREESPEE_ENABLED];
        strArr[0] = "has_cv_posting";
        String[] strArr2 = new String[FREESPEE_ENABLED];
        strArr2[0] = string;
        return new CursorLoader(context, uri, strArr, "_id=?", strArr2, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        boolean z = true;
        if (cursor.moveToFirst()) {
            if (cursor.getInt(cursor.getColumnIndex("has_cv_posting")) != FREESPEE_ENABLED) {
                z = false;
            }
            this.hasCvUpload = z;
            renameReplyButtonIfRequired();
        }
    }

    private void renameReplyButtonIfRequired() {
        if (this.hasCvUpload) {
            this.buttonReply.setText(2131165820);
            this.buttonWeb.setText(2131165820);
            this.buttonWeb.setImageDrawable(VectorDrawableCompat.create(this.context.getResources(), 2130837888, this.context.getTheme()));
            return;
        }
        this.buttonReply.setText(2131165875);
        this.buttonWeb.setText(2131165913);
        this.buttonWeb.setImageDrawable(null);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void showPhoneDial(String str) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("tel:" + str)));
    }

    public void showSpinnerCallButton(boolean z) {
        if (z) {
            this.buttonCall.setSpinnerVisibility(0);
        } else {
            this.buttonCall.setSpinnerVisibility(8);
        }
    }
}

package com.gumtree.android.message_box.conversation;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.apptentive.android.sdk.BuildConfig;
import com.apptentive.android.sdk.R;
import com.ebay.classifieds.capi.users.profile.UserProfile;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.connectivity.Network.NetworkState;
import com.gumtree.android.common.fragments.BaseGridFragment;
import com.gumtree.android.common.views.ThemeUtils;
import com.gumtree.android.conversations.Conversation;
import com.gumtree.android.conversations.ReplyConversation;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnConversationResultEvent;
import com.gumtree.android.message_box.GumtreeConversationsManager;
import com.gumtree.android.message_box.PostConversationReplyIntentService;
import com.gumtree.android.model.Messages;
import com.gumtree.android.notifications.NotificationType;
import com.gumtree.android.notifications.OnPushConversationReceived;
import com.gumtree.android.userprofile.events.OnUserProfileEvent;
import com.gumtree.android.userprofile.services.UserService;
import com.gumtree.android.vip.VIPActivity;
import com.gumtree.android.vip.api.VipDetailsIntentService;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class ConversationFragment extends BaseGridFragment implements LoaderCallbacks<Cursor> {
    public static final String IS_FIRST_ENTRY = "IS_FIRST_ENTRY";
    public static final String PREFS_CONVERSATION = "Preferences Conversation";
    private static final int WELCOME_MESSAGE_DURATION = 180;
    @Inject
    BaseAccountManager accountManager;
    private MessageAdapter adapter;
    protected IConversation conversationController;
    @Inject
    EventBus eventBus;
    private boolean isFirstEntry;
    private boolean isMyAd;
    private boolean isNetworkAvailable;
    private Object mEventBusReceiver;
    private EditText messageInput;
    private View rootView;
    private ImageButton sendButton;
    @Inject
    UserService userService;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        GumtreeApplication.component().inject(this);
        this.conversationController = (IConversation) activity;
        activity.setTitle(this.conversationController.getConversation().getAdSubject());
        Object adOwnerEmail = this.conversationController.getConversation().getAdOwnerEmail();
        boolean z = !TextUtils.isEmpty(adOwnerEmail) && adOwnerEmail.equals(this.accountManager.getUsername());
        this.isMyAd = z;
        this.adapter = new MessageAdapter(this.isMyAd, activity, null);
        this.mEventBusReceiver = new Object() {
            @Subscribe
            public void onUserProfileLoadedEvent(OnUserProfileEvent onUserProfileEvent) {
                if (onUserProfileEvent.isSuccess()) {
                    UserProfile userProfile = onUserProfileEvent.getUserProfile();
                    if (userProfile != null) {
                        View access$000 = ConversationFragment.this.rootView;
                        if (ConversationFragment.this.needToShowWelcomeMessage()) {
                            ConversationFragment.this.displayWelcomeMessage((ViewGroup) access$000, userProfile.getPrimaryContactEmail());
                        }
                    }
                }
            }

            @Subscribe
            public void onNetworkStateChangedEvent(NetworkState networkState) {
                ConversationFragment.this.isNetworkAvailable = networkState.isOnline();
                ConversationFragment.this.toggleSendButton(ConversationFragment.this.isNetworkAvailable, ConversationFragment.this.getMessage());
            }

            @Subscribe
            public void onConversationResultEvent(OnConversationResultEvent onConversationResultEvent) {
                if (ConversationFragment.this.getIntent().getBooleanExtra(ConversationActivity.EXTRA_REFRESH_CALLER, false)) {
                    ConversationFragment.this.getActivity().setResult(-1);
                }
            }

            @Subscribe
            public void onPushConversationReceived(OnPushConversationReceived onPushConversationReceived) {
                Conversation conversation = ConversationFragment.this.conversationController.getConversation();
                if (onPushConversationReceived.getConversationId().equals(conversation.getUid())) {
                    ConversationFragment.this.getActivity().setResult(-1);
                    ConversationIntentService.start(conversation.getUid());
                }
            }
        };
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        if (bundle == null) {
            VipDetailsIntentService.start(Long.parseLong(this.conversationController.getConversation().getAdId()), this.context);
            this.isFirstEntry = true;
            return;
        }
        this.isFirstEntry = bundle.getBoolean(IS_FIRST_ENTRY);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(2131755020, menu);
        MenuItem findItem = menu.findItem(2131624949);
        Conversation conversation = this.conversationController.getConversation();
        if (conversation.isAdExpired() || conversation.isAdDeleted()) {
            findItem.setVisible(false);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Conversation conversation = this.conversationController.getConversation();
        if (conversation.isAdExpired() || conversation.isAdDeleted()) {
            ActionBar supportActionBar = ((BaseActivity) getActivity()).getSupportActionBar();
            String adStatus = conversation.getAdStatus();
            adStatus = adStatus.substring(0, 1).toUpperCase() + adStatus.substring(1).toLowerCase();
            supportActionBar.setSubtitle(this.context.getString(2131165389, new Object[]{adStatus}));
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131624949:
                startActivity(VIPActivity.createIntentForMessages(getActivity(), Long.parseLong(this.conversationController.getConversation().getAdId())));
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onResume() {
        super.onResume();
        this.eventBus.register(this.mEventBusReceiver);
        if (needToShowWelcomeMessage()) {
            this.userService.update();
        }
    }

    public void onPause() {
        super.onPause();
        this.eventBus.unregister(this.mEventBusReceiver);
    }

    protected int getLayoutId() {
        return 2130903194;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(IS_FIRST_ENTRY, this.isFirstEntry);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.rootView = view;
        getGridView().setTranscriptMode(1);
        getGridView().setStackFromBottom(true);
        getGridView().setOnScrollListener(null);
        this.sendButton = (ImageButton) view.findViewById(R.id.btn_send);
        this.sendButton.setOnClickListener(ConversationFragment$$Lambda$1.lambdaFactory$(this));
        this.messageInput = (EditText) view.findViewById(2131624443);
        this.messageInput.addTextChangedListener(getEmptyTextWatcher());
        this.sendButton.setEnabled(false);
        if (bundle == null) {
            Conversation conversation = this.conversationController.getConversation();
            if (!ConversationUtils.isTempConversationId(conversation)) {
                ConversationIntentService.start(conversation.getUid());
            }
        }
        getLoaderManager().initLoader(0, null, this);
        getGridView().setAdapter(this.adapter);
    }

    /* synthetic */ void lambda$onViewCreated$0(View view) {
        sendMessage(getMessage());
    }

    private void sendMessage(String str) {
        if (this.conversationController.isNewConversation()) {
            PostConversationReplyIntentService.newConversation(new ReplyConversation(getConversationController().getConversation(), str, true, false));
        } else {
            PostConversationReplyIntentService.reply(new ReplyConversation(getConversationController().getConversation(), str, false, this.adapter.isMine()), this.isMyAd);
        }
        clearInputBox();
    }

    private TextWatcher getEmptyTextWatcher() {
        return new 2(this);
    }

    void toggleSendButton(boolean z, CharSequence charSequence) {
        if (!z || charSequence == null || charSequence.toString().trim().length() <= 0) {
            this.sendButton.setEnabled(false);
        } else {
            this.sendButton.setEnabled(true);
        }
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this.context, Messages.URI, null, "conversation_uid=?", new String[]{this.conversationController.getConversation().getUid()}, "post_time_stamp ASC");
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        DatabaseUtils.dumpCursor(cursor);
        this.adapter.swapCursor(cursor);
        if (this.isFirstEntry) {
            if (cursor.getCount() > 1) {
                this.messageInput.clearFocus();
                hideKeyboard(this.messageInput);
            } else {
                this.messageInput.requestFocus();
            }
        }
        this.isFirstEntry = false;
        getGridView().smoothScrollToPosition(this.adapter.getCount() - 1);
        ((NotificationManager) getActivity().getSystemService(ConversationActivity.NOTIFICATION)).cancel(NotificationType.CHAT_MESSAGE.getType());
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        this.adapter.swapCursor(null);
    }

    public void onStart() {
        super.onStart();
        GumtreeConversationsManager.get().setCurrentConversationId(this.conversationController.getConversation().getUid());
        GumtreeConversationsManager.get().setCurrentConversationAdId(this.conversationController.getConversation().getAdId());
    }

    public void onStop() {
        super.onStop();
        GumtreeConversationsManager.get().setCurrentConversationId(null);
        GumtreeConversationsManager.get().setCurrentConversationAdId(null);
    }

    private boolean needToShowWelcomeMessage() {
        if (getActivity().getSharedPreferences(PREFS_CONVERSATION, 0).getBoolean("pref_seen_conversation_welcome", false) || this.isMyAd) {
            return false;
        }
        return true;
    }

    private void displayWelcomeMessage(ViewGroup viewGroup, String str) {
        ViewGroup viewGroup2;
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setDuration(180);
        layoutTransition.setInterpolator(3, new DecelerateInterpolator());
        layoutTransition.setInterpolator(2, new DecelerateInterpolator());
        viewGroup.setLayoutTransition(layoutTransition);
        ViewStub viewStub = (ViewStub) viewGroup.findViewById(2131624444);
        if (viewStub != null) {
            viewGroup2 = (ViewGroup) viewStub.inflate();
        } else {
            viewGroup2 = (ViewGroup) viewGroup.findViewById(2131624445);
        }
        TextView textView = (TextView) viewGroup2.findViewById(2131624707);
        Button button = (Button) viewGroup2.findViewById(2131624708);
        Button button2 = (Button) viewGroup2.findViewById(2131624709);
        textView.setText(new SpannableString(getResources().getString(2131165391)));
        textView.append(" ");
        CharSequence spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(ThemeUtils.getColor(getActivity(), 16842806)), 0, spannableString.length(), 33);
        spannableString.setSpan(new TypefaceSpan("sans-serif-medium"), 0, spannableString.length(), 33);
        textView.append(spannableString);
        button.setOnClickListener(ConversationFragment$$Lambda$2.lambdaFactory$(this, viewGroup, viewGroup2));
        button2.setOnClickListener(ConversationFragment$$Lambda$3.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$displayWelcomeMessage$1(ViewGroup viewGroup, ViewGroup viewGroup2, View view) {
        getActivity().getSharedPreferences(PREFS_CONVERSATION, 0).edit().putBoolean("pref_seen_conversation_welcome", true).commit();
        viewGroup.removeView(viewGroup2);
    }

    /* synthetic */ void lambda$displayWelcomeMessage$2(View view) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(GumtreeApplication.getAPI().getUpdateEmailUrl()));
        startActivity(intent);
        Track.eventMessageCenterEmailEditBegin();
    }

    protected IConversation getConversationController() {
        return this.conversationController;
    }

    protected void clearInputBox() {
        this.messageInput.setText(BuildConfig.FLAVOR);
    }

    protected String getMessage() {
        return this.messageInput.getText().toString().trim();
    }

    private boolean isNetworkAvailable() {
        return this.isNetworkAvailable;
    }
}

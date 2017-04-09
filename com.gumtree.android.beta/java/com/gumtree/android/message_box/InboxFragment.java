package com.gumtree.android.message_box;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.fragments.BaseGridFragment;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.conversations.Conversation;
import com.gumtree.android.conversations.ConversationsPage;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnConversationsListResultEvent;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.login.AuthActivity;
import com.gumtree.android.message_box.conversation.ConversationActivity;
import com.gumtree.android.model.ConversationsView;
import com.gumtree.android.userprofile.events.OnUserProfileEvent;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;
import org.apache.commons.lang3.ObjectUtils;

public class InboxFragment extends BaseGridFragment implements LoaderCallbacks<Cursor> {
    private static final String HAS_REQUEST_FINISHED = "hasRequestFinished";
    private static final String NUM_UNREAD_MESSAGES = "numUnreadMessages";
    private static final int SIZE = 100;
    private final ActionModeManager actionModeManager = new ActionModeManager(this, null);
    private InboxConversationsAdapter adapter;
    @Inject
    BaseAccountManager customerAccountManager;
    @Inject
    EventBus eventBus;
    private boolean hasRequestFinished;
    private int numUnreadMessages;
    private final OnItemClickListener simpleClickManager = new 1(this);

    protected int getLayoutId() {
        return 2130903210;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        setHasOptionsMenu(true);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        getGridView().setOnItemClickListener(this.simpleClickManager);
        if (DevelopmentFlags.getInstance().isDeleteInInboxEnabled()) {
            getGridView().setChoiceMode(3);
            getGridView().setOnLongClickListener(this.actionModeManager);
            getGridView().setMultiChoiceModeListener(this.actionModeManager);
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (this.customerAccountManager.isUserLoggedIn() || bundle != null) {
            init(bundle);
        } else if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
            startActivityForResult(AuthActivity.createIntent(AuthIdentifier.MESSAGES, getActivity()), LocationActivity.ACTIVITY_REQUEST_CODE);
        } else {
            AuthenticatorActivity.startActivity((Fragment) this, 6);
        }
    }

    protected String getEmptyText() {
        return ((CharSequence) ObjectUtils.defaultIfNull(getEmptyTextView().getText(), super.getEmptyText())).toString();
    }

    private void init(Bundle bundle) {
        if (bundle == null) {
            this.hasRequestFinished = false;
            getConversations();
            setTitle(getString(2131165649));
        } else {
            this.numUnreadMessages = bundle.getInt(NUM_UNREAD_MESSAGES, 0);
            updateToolbarTitle(this.numUnreadMessages);
            this.hasRequestFinished = bundle.getBoolean(HAS_REQUEST_FINISHED);
        }
        setSubtitle(this.customerAccountManager.getContactEmail());
        getLoaderManager().initLoader(0, null, this);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(HAS_REQUEST_FINISHED, this.hasRequestFinished);
        bundle.putInt(NUM_UNREAD_MESSAGES, this.numUnreadMessages);
    }

    public void onResume() {
        super.onResume();
        this.eventBus.register(this);
    }

    public void onPause() {
        super.onPause();
        this.eventBus.unregister(this);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == LocationActivity.ACTIVITY_REQUEST_CODE) {
            if (i2 == -1) {
                if (DevelopmentFlags.getInstance().isNewLoginEnabled() && intent.hasExtra("displayname")) {
                    showSnackBar(getString(2131165552, new Object[]{intent.getStringExtra("displayname")}));
                }
                init(null);
                return;
            }
            getActivity().finish();
        } else if (i == 17 && i2 == -1) {
            getConversations();
        }
    }

    private void initAdapter() {
        this.adapter = new InboxConversationsAdapter(getActivity(), null, this.customerAccountManager.getUsername());
        setGridAdapter(this.adapter);
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this.context, ConversationsView.URI, null, null, null, "message_post_time_stamp DESC");
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (getGridAdapter() == null) {
            initAdapter();
        }
        if (this.hasRequestFinished) {
            this.adapter.changeCursor(cursor);
            boolean z = isGridViewVisible() || cursor.getCount() >= 0;
            setGridShown(z);
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private void getConversations() {
        setGridShown(false);
        setSubtitle(getString(2131165866));
        ConversationsIntentService.start(0, SIZE, null);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(2131755022, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131624953:
                getConversations();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Subscribe
    public void onConversationsReceived(OnConversationsListResultEvent onConversationsListResultEvent) {
        this.hasRequestFinished = true;
        updateToolbarTitle(onConversationsListResultEvent.getConversationsPage());
        setSubtitle(this.customerAccountManager.getContactEmail());
    }

    @Subscribe
    public void onUserProfileLoadedEvent(OnUserProfileEvent onUserProfileEvent) {
        if (onUserProfileEvent.isSuccess()) {
            setSubtitle(this.customerAccountManager.getContactEmail());
        }
    }

    private void updateToolbarTitle(@Nullable ConversationsPage conversationsPage) {
        if (conversationsPage != null) {
            try {
                int totalConversationCount = conversationsPage.getTotalConversationCount();
                int totalUnreadConversationCount = conversationsPage.getTotalUnreadConversationCount();
                if (totalConversationCount > 0) {
                    this.numUnreadMessages = totalUnreadConversationCount;
                    updateToolbarTitle(totalUnreadConversationCount);
                    return;
                }
            } catch (Throwable e) {
                Log.e("Error parsing conversation counts", e);
            }
            setTitle(getString(2131165649));
        }
    }

    private void setTitle(String str) {
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle(str);
    }

    private void setSubtitle(String str) {
        ((BaseActivity) getActivity()).getSupportActionBar().setSubtitle(str);
    }

    private void updateToolbarTitle(int i) {
        String str;
        if (i > 0) {
            str = getString(2131165649) + " (" + i + ")";
        } else {
            str = getString(2131165649);
        }
        setTitle(str);
    }

    private void show(Conversation conversation) {
        startActivityForResult(ConversationActivity.createIntent(getActivity(), conversation, conversation.getNumUnreadMsg() > 0, ConversationActivity.MESSAGES), 17);
    }
}

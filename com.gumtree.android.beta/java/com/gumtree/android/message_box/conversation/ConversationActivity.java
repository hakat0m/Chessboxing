package com.gumtree.android.message_box.conversation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.conversations.Conversation;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.message_box.conversation.DeletionPresenter.View;
import javax.inject.Inject;

public class ConversationActivity extends BaseActivity implements OnClickListener, IConversation {
    private static final String EXTRA_CONVERSATION = "com.gumtree.android.message_box.conversation";
    private static final String EXTRA_ORIGIN = "com.gumtree.android.message_box.conversation.origin";
    public static final String EXTRA_REFRESH_CALLER = "com.gumtree.android.message_box.conversation.refresh_caller";
    public static final String MESSAGES = "messages";
    public static final String NOTIFICATION = "notification";
    private static final String TAG_DELETE_CONFIRMATION = "TAG_DELETE_CONFIRMATION";
    public static final String VIP = "vip";
    @Inject
    EventBus eventBus;
    private boolean isDeleting;
    private DeletionPresenter presenter;
    private final DeletionViewDelegate viewDelegate = new DeletionViewDelegate();

    public @interface ConversationOrigin {
    }

    class DeletionViewDelegate implements View {
        private DeletionViewDelegate() {
        }

        public void onDeletionSucceeded() {
            Toast.makeText(ConversationActivity.this, 2131165416, 1).show();
            ConversationActivity.this.setResult(-1);
            ConversationActivity.this.finish();
        }

        public void onDeletionFailed(Throwable th) {
            Toast.makeText(ConversationActivity.this, 2131165418, 1).show();
            ConversationActivity.this.isDeleting = false;
            ActivityCompat.invalidateOptionsMenu(ConversationActivity.this);
        }
    }

    public static Intent createIntent(Context context, Conversation conversation, boolean z, @ConversationOrigin String str) {
        Intent intent = new Intent(context, ConversationActivity.class);
        intent.putExtra(EXTRA_CONVERSATION, conversation);
        intent.putExtra(EXTRA_REFRESH_CALLER, z);
        intent.putExtra(EXTRA_ORIGIN, str);
        return intent;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        this.presenter = new ServiceBusDeletionPresenter(this.eventBus);
        setContentView(2130903075);
        if (bundle == null) {
            String stringExtra = getIntent().getStringExtra(EXTRA_ORIGIN);
            Track.viewConversation(getConversation().getAdId(), stringExtra);
            if (NOTIFICATION.equals(stringExtra)) {
                Track.eventMessageNotificationOpenToConversation();
            }
        }
    }

    protected void onDestroy() {
        this.presenter = null;
        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
        this.presenter.attachView(this.viewDelegate);
    }

    protected void onPause() {
        this.presenter.detachView(this.viewDelegate);
        super.onPause();
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131755010, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            case 2131624931:
                askDeleteConfirmation();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(2131624931).setEnabled(!this.isDeleting);
        return super.onPrepareOptionsMenu(menu);
    }

    private void askDeleteConfirmation() {
        new DeletionDialog().show(getSupportFragmentManager(), TAG_DELETE_CONFIRMATION);
    }

    public Conversation getConversation() {
        return (Conversation) getIntent().getSerializableExtra(EXTRA_CONVERSATION);
    }

    public boolean isNewConversation() {
        return ConversationUtils.isTempConversationId(getConversation());
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            this.presenter.delete(getConversation());
            this.isDeleting = true;
            ActivityCompat.invalidateOptionsMenu(this);
            return;
        }
        Log.e(getClass().getSimpleName(), "Bug! Button " + i + " is not handled here");
    }
}

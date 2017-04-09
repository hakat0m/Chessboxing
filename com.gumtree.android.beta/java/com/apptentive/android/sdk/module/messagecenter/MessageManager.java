package com.apptentive.android.sdk.module.messagecenter;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.apptentive.android.sdk.ApptentiveInternal;
import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.R;
import com.apptentive.android.sdk.comm.ApptentiveClient;
import com.apptentive.android.sdk.comm.ApptentiveHttpResponse;
import com.apptentive.android.sdk.model.Payload;
import com.apptentive.android.sdk.module.messagecenter.model.ApptentiveMessage;
import com.apptentive.android.sdk.module.messagecenter.model.ApptentiveMessage.State;
import com.apptentive.android.sdk.module.messagecenter.model.ApptentiveMessage.Type;
import com.apptentive.android.sdk.module.messagecenter.model.ApptentiveToastNotification.Builder;
import com.apptentive.android.sdk.module.messagecenter.model.CompoundMessage;
import com.apptentive.android.sdk.module.messagecenter.model.MessageCenterUtil.MessageCenterListItem;
import com.apptentive.android.sdk.module.messagecenter.model.MessageFactory;
import com.apptentive.android.sdk.storage.ApptentiveDatabase;
import com.apptentive.android.sdk.storage.MessageStore;
import com.apptentive.android.sdk.util.Util;
import com.gumtree.android.postad.DraftAd;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageManager {
    public static int SEND_PAUSE_REASON_ACTIVITY_PAUSE = 0;
    public static int SEND_PAUSE_REASON_NETWORK = UI_THREAD_MESSAGE_ON_UNREAD_HOST;
    public static int SEND_PAUSE_REASON_SERVER = UI_THREAD_MESSAGE_ON_UNREAD_INTERNAL;
    private static int TOAST_TYPE_UNREAD_MESSAGE = UI_THREAD_MESSAGE_ON_UNREAD_HOST;
    private static final int UI_THREAD_MESSAGE_ON_TOAST_NOTIFICATION = 3;
    private static final int UI_THREAD_MESSAGE_ON_UNREAD_HOST = 1;
    private static final int UI_THREAD_MESSAGE_ON_UNREAD_INTERNAL = 2;
    private WeakReference<AfterSendMessageListener> afterSendMessageListener;
    AtomicBoolean appInForeground = new AtomicBoolean(false);
    private WeakReference<Activity> currentForgroundApptentiveActivity;
    private final List<WeakReference<UnreadMessagesListener>> hostUnreadMessagesListeners = new ArrayList();
    private final List<WeakReference<OnNewIncomingMessagesListener>> internalNewMessagesListeners = new ArrayList();
    private MessagePollingWorker pollingWorker;
    private Handler uiHandler;

    public void init() {
        if (this.uiHandler == null) {
            this.uiHandler = new 1(this, Looper.getMainLooper());
        }
        if (this.pollingWorker == null) {
            this.pollingWorker = new MessagePollingWorker(this);
            SharedPreferences sharedPrefs = ApptentiveInternal.getInstance().getSharedPrefs();
            if (!sharedPrefs.getBoolean("messageCenterFeatureUsed", false)) {
                sharedPrefs.edit().putBoolean("messageCenterFeatureUsed", true).apply();
            }
        }
    }

    public void startMessagePreFetchTask() {
        init();
        2 2 = new 2(this);
        if (VERSION.SDK_INT >= 11) {
            Executor executor = AsyncTask.THREAD_POOL_EXECUTOR;
            Object[] objArr = new Object[UI_THREAD_MESSAGE_ON_UNREAD_HOST];
            objArr[0] = Boolean.valueOf(isMessageCenterInForeground());
            2.executeOnExecutor(executor, objArr);
            return;
        }
        Object[] objArr2 = new Object[UI_THREAD_MESSAGE_ON_UNREAD_HOST];
        objArr2[0] = Boolean.valueOf(isMessageCenterInForeground());
        2.execute(objArr2);
    }

    public synchronized boolean fetchAndStoreMessages(boolean z, boolean z2) {
        boolean z3 = false;
        synchronized (this) {
            if (ApptentiveInternal.getInstance().getApptentiveConversationToken() == null) {
                ApptentiveLog.d("Can't fetch messages because the conversation has not yet been initialized.", new Object[0]);
            } else if (Util.isNetworkConnectionPresent()) {
                List<ApptentiveMessage> fetchMessages = fetchMessages(getMessageStore().getLastReceivedMessageId());
                CompoundMessage compoundMessage = null;
                if (fetchMessages != null && fetchMessages.size() > 0) {
                    Message obtainMessage;
                    ApptentiveLog.d("Messages retrieved.", new Object[0]);
                    int i = 0;
                    for (ApptentiveMessage apptentiveMessage : fetchMessages) {
                        int i2;
                        if (apptentiveMessage.isOutgoingMessage()) {
                            apptentiveMessage.setRead(true);
                            i2 = i;
                        } else {
                            CompoundMessage compoundMessage2;
                            if (compoundMessage == null && apptentiveMessage.getType() == Type.CompoundMessage) {
                                compoundMessage2 = (CompoundMessage) apptentiveMessage;
                            } else {
                                compoundMessage2 = compoundMessage;
                            }
                            int i3 = i + UI_THREAD_MESSAGE_ON_UNREAD_HOST;
                            this.uiHandler.obtainMessage(UI_THREAD_MESSAGE_ON_UNREAD_INTERNAL, (CompoundMessage) apptentiveMessage).sendToTarget();
                            i2 = i3;
                            compoundMessage = compoundMessage2;
                        }
                        i = i2;
                    }
                    getMessageStore().addOrUpdateMessages((ApptentiveMessage[]) fetchMessages.toArray(new ApptentiveMessage[fetchMessages.size()]));
                    if (i > 0 && !z && z2) {
                        obtainMessage = this.uiHandler.obtainMessage(UI_THREAD_MESSAGE_ON_TOAST_NOTIFICATION, compoundMessage);
                        this.uiHandler.removeMessages(UI_THREAD_MESSAGE_ON_TOAST_NOTIFICATION);
                        obtainMessage.sendToTarget();
                    }
                    obtainMessage = this.uiHandler.obtainMessage(UI_THREAD_MESSAGE_ON_UNREAD_HOST, getUnreadMessageCount(), 0);
                    this.uiHandler.removeMessages(UI_THREAD_MESSAGE_ON_UNREAD_HOST);
                    obtainMessage.sendToTarget();
                    z3 = i > 0 ? UI_THREAD_MESSAGE_ON_UNREAD_HOST : false;
                }
            } else {
                ApptentiveLog.d("Can't fetch messages because a network connection is not present.", new Object[0]);
            }
        }
        return z3;
    }

    public List<MessageCenterListItem> getMessageCenterListItems() {
        List<MessageCenterListItem> arrayList = new ArrayList();
        for (ApptentiveMessage apptentiveMessage : getMessageStore().getAllMessages()) {
            if (!apptentiveMessage.isHidden()) {
                arrayList.add(apptentiveMessage);
            }
        }
        return arrayList;
    }

    public void sendMessage(ApptentiveMessage apptentiveMessage) {
        MessageStore messageStore = getMessageStore();
        ApptentiveMessage[] apptentiveMessageArr = new ApptentiveMessage[UI_THREAD_MESSAGE_ON_UNREAD_HOST];
        apptentiveMessageArr[0] = apptentiveMessage;
        messageStore.addOrUpdateMessages(apptentiveMessageArr);
        ApptentiveDatabase apptentiveDatabase = ApptentiveInternal.getInstance().getApptentiveDatabase();
        Payload[] payloadArr = new Payload[UI_THREAD_MESSAGE_ON_UNREAD_HOST];
        payloadArr[0] = apptentiveMessage;
        apptentiveDatabase.addPayload(payloadArr);
    }

    public void deleteAllMessages(Context context) {
        ApptentiveLog.d("Deleting all messages.", new Object[0]);
        getMessageStore().deleteAllMessages();
    }

    private List<ApptentiveMessage> fetchMessages(String str) {
        String str2 = "Fetching messages newer than: %s";
        Object[] objArr = new Object[UI_THREAD_MESSAGE_ON_UNREAD_HOST];
        objArr[0] = str == null ? DraftAd.NEW_AD_ID : str;
        ApptentiveLog.d(str2, objArr);
        ApptentiveHttpResponse messages = ApptentiveClient.getMessages(null, str, null);
        List<ApptentiveMessage> arrayList = new ArrayList();
        if (messages.isSuccessful()) {
            try {
                arrayList = parseMessagesString(messages.getContent());
            } catch (Throwable e) {
                ApptentiveLog.e("Error parsing messages JSON.", e, new Object[0]);
            } catch (Throwable e2) {
                ApptentiveLog.e("Unexpected error parsing messages JSON.", e2, new Object[0]);
            }
        }
        return arrayList;
    }

    public void updateMessage(ApptentiveMessage apptentiveMessage) {
        getMessageStore().updateMessage(apptentiveMessage);
    }

    public List<ApptentiveMessage> parseMessagesString(String str) throws JSONException {
        List<ApptentiveMessage> arrayList = new ArrayList();
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.isNull("items")) {
            JSONArray jSONArray = jSONObject.getJSONArray("items");
            for (int i = 0; i < jSONArray.length(); i += UI_THREAD_MESSAGE_ON_UNREAD_HOST) {
                ApptentiveMessage fromJson = MessageFactory.fromJson(jSONArray.getJSONObject(i).toString());
                if (fromJson != null) {
                    fromJson.setState(State.saved);
                    arrayList.add(fromJson);
                }
            }
        }
        return arrayList;
    }

    public void resumeSending() {
        if (this.afterSendMessageListener != null && this.afterSendMessageListener.get() != null) {
            ((AfterSendMessageListener) this.afterSendMessageListener.get()).onResumeSending();
        }
    }

    public void pauseSending(int i) {
        if (this.afterSendMessageListener != null && this.afterSendMessageListener.get() != null) {
            ((AfterSendMessageListener) this.afterSendMessageListener.get()).onPauseSending(i);
        }
    }

    public void onSentMessage(ApptentiveMessage apptentiveMessage, ApptentiveHttpResponse apptentiveHttpResponse) {
        if (apptentiveHttpResponse.isRejectedPermanently() || apptentiveHttpResponse.isBadPayload()) {
            if (apptentiveMessage instanceof CompoundMessage) {
                apptentiveMessage.setCreatedAt(Double.valueOf(Double.MIN_VALUE));
                getMessageStore().updateMessage(apptentiveMessage);
                if (this.afterSendMessageListener != null && this.afterSendMessageListener.get() != null) {
                    ((AfterSendMessageListener) this.afterSendMessageListener.get()).onMessageSent(apptentiveHttpResponse, apptentiveMessage);
                }
            }
        } else if (apptentiveHttpResponse.isRejectedTemporarily()) {
            pauseSending(SEND_PAUSE_REASON_SERVER);
        } else if (!apptentiveHttpResponse.isSuccessful()) {
        } else {
            if (apptentiveMessage.isHidden()) {
                ((CompoundMessage) apptentiveMessage).deleteAssociatedFiles();
                getMessageStore().deleteMessage(apptentiveMessage.getNonce());
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(apptentiveHttpResponse.getContent());
                apptentiveMessage.setState(State.sent);
                apptentiveMessage.setId(jSONObject.getString("id"));
                apptentiveMessage.setCreatedAt(Double.valueOf(jSONObject.getDouble("created_at")));
            } catch (Throwable e) {
                ApptentiveLog.e("Error parsing sent apptentiveMessage response.", e, new Object[0]);
            }
            getMessageStore().updateMessage(apptentiveMessage);
            if (this.afterSendMessageListener != null && this.afterSendMessageListener.get() != null) {
                ((AfterSendMessageListener) this.afterSendMessageListener.get()).onMessageSent(apptentiveHttpResponse, apptentiveMessage);
            }
        }
    }

    private MessageStore getMessageStore() {
        return ApptentiveInternal.getInstance().getApptentiveDatabase();
    }

    public int getUnreadMessageCount() {
        return getMessageStore().getUnreadMessageCount();
    }

    public void setAfterSendMessageListener(AfterSendMessageListener afterSendMessageListener) {
        if (afterSendMessageListener != null) {
            this.afterSendMessageListener = new WeakReference(afterSendMessageListener);
        } else {
            this.afterSendMessageListener = null;
        }
    }

    public void addInternalOnMessagesUpdatedListener(OnNewIncomingMessagesListener onNewIncomingMessagesListener) {
        if (onNewIncomingMessagesListener != null) {
            init();
            Iterator it = this.internalNewMessagesListeners.iterator();
            while (it.hasNext()) {
                OnNewIncomingMessagesListener onNewIncomingMessagesListener2 = (OnNewIncomingMessagesListener) ((WeakReference) it.next()).get();
                if (onNewIncomingMessagesListener2 != null && onNewIncomingMessagesListener2 == onNewIncomingMessagesListener) {
                    return;
                }
                if (onNewIncomingMessagesListener2 == null) {
                    it.remove();
                }
            }
            this.internalNewMessagesListeners.add(new WeakReference(onNewIncomingMessagesListener));
        }
    }

    public void clearInternalOnMessagesUpdatedListeners() {
        this.internalNewMessagesListeners.clear();
    }

    public void notifyInternalNewMessagesListeners(CompoundMessage compoundMessage) {
        for (WeakReference weakReference : this.internalNewMessagesListeners) {
            OnNewIncomingMessagesListener onNewIncomingMessagesListener = (OnNewIncomingMessagesListener) weakReference.get();
            if (onNewIncomingMessagesListener != null) {
                onNewIncomingMessagesListener.onNewMessageReceived(compoundMessage);
            }
        }
    }

    @Deprecated
    public void setHostUnreadMessagesListener(UnreadMessagesListener unreadMessagesListener) {
        clearHostUnreadMessagesListeners();
        if (unreadMessagesListener != null) {
            this.hostUnreadMessagesListeners.add(new WeakReference(unreadMessagesListener));
        }
    }

    public void addHostUnreadMessagesListener(UnreadMessagesListener unreadMessagesListener) {
        if (unreadMessagesListener != null) {
            init();
            Iterator it = this.hostUnreadMessagesListeners.iterator();
            while (it.hasNext()) {
                UnreadMessagesListener unreadMessagesListener2 = (UnreadMessagesListener) ((WeakReference) it.next()).get();
                if (unreadMessagesListener2 != null && unreadMessagesListener2 == unreadMessagesListener) {
                    return;
                }
                if (unreadMessagesListener2 == null) {
                    it.remove();
                }
            }
            this.hostUnreadMessagesListeners.add(new WeakReference(unreadMessagesListener));
        }
    }

    public void clearHostUnreadMessagesListeners() {
        this.hostUnreadMessagesListeners.clear();
    }

    public void notifyHostUnreadMessagesListeners(int i) {
        for (WeakReference weakReference : this.hostUnreadMessagesListeners) {
            UnreadMessagesListener unreadMessagesListener = (UnreadMessagesListener) weakReference.get();
            if (unreadMessagesListener != null) {
                unreadMessagesListener.onUnreadMessageCountChanged(i);
            }
        }
    }

    public void setCurrentForgroundActivity(Activity activity) {
        if (activity != null) {
            this.currentForgroundApptentiveActivity = new WeakReference(activity);
            return;
        }
        ApptentiveToastNotificationManager instance = ApptentiveToastNotificationManager.getInstance(null, false);
        if (instance != null) {
            instance.cleanUp();
        }
        this.currentForgroundApptentiveActivity = null;
    }

    public void setMessageCenterInForeground(boolean z) {
        this.pollingWorker.setMessageCenterInForeground(z);
    }

    public boolean isMessageCenterInForeground() {
        return this.pollingWorker.messageCenterInForeground.get();
    }

    private void showUnreadMessageToastNotification(CompoundMessage compoundMessage) {
        if (this.currentForgroundApptentiveActivity != null && this.currentForgroundApptentiveActivity.get() != null) {
            Activity activity = (Activity) this.currentForgroundApptentiveActivity.get();
            if (activity != null) {
                PendingIntent prepareMessageCenterPendingIntent = ApptentiveInternal.prepareMessageCenterPendingIntent(activity.getApplicationContext());
                if (prepareMessageCenterPendingIntent != null) {
                    ApptentiveToastNotificationManager instance = ApptentiveToastNotificationManager.getInstance(activity, true);
                    Builder builder = new Builder(activity);
                    builder.setContentTitle(activity.getResources().getString(R.string.apptentive_message_center_title)).setDefaults(5).setSmallIcon(R.drawable.avatar).setContentText(compoundMessage.getBody()).setContentIntent(prepareMessageCenterPendingIntent).setFullScreenIntent(prepareMessageCenterPendingIntent, false);
                    activity.runOnUiThread(new 3(this, builder, compoundMessage, instance));
                }
            }
        }
    }

    public void appWentToForeground() {
        this.appInForeground.set(true);
        if (this.pollingWorker != null) {
            this.pollingWorker.appWentToForeground();
        }
    }

    public void appWentToBackground() {
        this.appInForeground.set(false);
        if (this.pollingWorker != null) {
            this.pollingWorker.appWentToBackground();
        }
    }
}

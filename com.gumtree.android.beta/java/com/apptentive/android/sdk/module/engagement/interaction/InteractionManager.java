package com.apptentive.android.sdk.module.engagement.interaction;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import com.apptentive.android.sdk.ApptentiveInternal;
import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.comm.ApptentiveClient;
import com.apptentive.android.sdk.comm.ApptentiveHttpResponse;
import com.apptentive.android.sdk.model.CodePointStore;
import com.apptentive.android.sdk.module.engagement.interaction.model.Interaction;
import com.apptentive.android.sdk.module.engagement.interaction.model.Interactions;
import com.apptentive.android.sdk.module.engagement.interaction.model.InteractionsPayload;
import com.apptentive.android.sdk.module.engagement.interaction.model.Targets;
import com.apptentive.android.sdk.module.metric.MetricModule;
import com.apptentive.android.sdk.util.Util;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;

public class InteractionManager {
    private Interactions interactions;
    private AtomicBoolean isFetchPending = new AtomicBoolean(false);
    private Boolean pollForInteractions;
    private Targets targets;

    public interface InteractionUpdateListener {
        void onInteractionUpdated(boolean z);
    }

    public Interactions getInteractions() {
        if (this.interactions == null) {
            this.interactions = loadInteractions();
        }
        return this.interactions;
    }

    public Targets getTargets() {
        if (this.targets == null) {
            this.targets = loadTargets();
        }
        return this.targets;
    }

    public Interaction getApplicableInteraction(String str) {
        Targets targets = getTargets();
        if (targets != null) {
            String applicableInteraction = targets.getApplicableInteraction(str);
            if (applicableInteraction != null) {
                return getInteractions().getInteraction(applicableInteraction);
            }
        }
        return null;
    }

    public void asyncFetchAndStoreInteractions() {
        if (isPollForInteractions()) {
            boolean isApptentiveDebuggable = ApptentiveInternal.getInstance().isApptentiveDebuggable();
            if (this.isFetchPending.compareAndSet(false, true) && (isApptentiveDebuggable || hasCacheExpired())) {
                AsyncTask anonymousClass1 = new AsyncTask<Void, Void, Boolean>() {
                    private Exception e = null;

                    protected Boolean doInBackground(Void... voidArr) {
                        Boolean bool = new Boolean(false);
                        try {
                            bool = Boolean.valueOf(InteractionManager.this.fetchAndStoreInteractions());
                        } catch (Exception e) {
                            this.e = e;
                        }
                        return bool;
                    }

                    protected void onPostExecute(Boolean bool) {
                        InteractionManager.this.isFetchPending.set(false);
                        if (this.e == null) {
                            ApptentiveLog.i("Fetching new Interactions asyncTask finished. Result:" + bool.booleanValue(), new Object[0]);
                            ApptentiveInternal.getInstance().notifyInteractionUpdated(bool.booleanValue());
                            return;
                        }
                        ApptentiveLog.w("Unhandled Exception thrown from fetching new Interactions asyncTask", this.e, new Object[0]);
                        MetricModule.sendError(this.e, null, null);
                    }
                };
                ApptentiveLog.i("Fetching new Interactions asyncTask scheduled", new Object[0]);
                if (VERSION.SDK_INT >= 11) {
                    anonymousClass1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                    return;
                } else {
                    anonymousClass1.execute(new Void[0]);
                    return;
                }
            }
            ApptentiveLog.v("Using cached Interactions.", new Object[0]);
            return;
        }
        ApptentiveLog.v("Interaction polling is disabled.", new Object[0]);
    }

    private boolean fetchAndStoreInteractions() {
        boolean z = true;
        ApptentiveLog.i("Fetching new Interactions asyncTask started", new Object[0]);
        ApptentiveHttpResponse interactions = ApptentiveClient.getInteractions();
        SharedPreferences sharedPrefs = ApptentiveInternal.getInstance().getSharedPrefs();
        if (interactions.isException()) {
            sharedPrefs.edit().putBoolean("messageCenterServerErrorLastAttempt", false).apply();
            z = false;
        } else if (!interactions.isSuccessful()) {
            sharedPrefs.edit().putBoolean("messageCenterServerErrorLastAttempt", true).apply();
            z = false;
        }
        if (z) {
            String content = interactions.getContent();
            Integer parseCacheControlHeader = Util.parseCacheControlHeader((String) interactions.getHeaders().get("Cache-Control"));
            if (parseCacheControlHeader == null) {
                parseCacheControlHeader = Integer.valueOf(28800);
            }
            updateCacheExpiration((long) parseCacheControlHeader.intValue());
            storeInteractionsPayloadString(content);
        }
        return z;
    }

    public void storeInteractionsPayloadString(String str) {
        try {
            InteractionsPayload interactionsPayload = new InteractionsPayload(str);
            Interactions interactions = interactionsPayload.getInteractions();
            Targets targets = interactionsPayload.getTargets();
            if (interactions == null || targets == null) {
                ApptentiveLog.e("Unable to save payloads.", new Object[0]);
                return;
            }
            this.interactions = interactions;
            this.targets = targets;
            saveInteractions();
            saveTargets();
        } catch (JSONException e) {
            ApptentiveLog.w("Invalid InteractionsPayload received.", new Object[0]);
        }
    }

    public void clear() {
        SharedPreferences sharedPrefs = ApptentiveInternal.getInstance().getSharedPrefs();
        sharedPrefs.edit().remove(CodePointStore.KEY_INTERACTIONS).apply();
        sharedPrefs.edit().remove("targets").apply();
        this.interactions = null;
        this.targets = null;
    }

    private void saveInteractions() {
        ApptentiveInternal.getInstance().getSharedPrefs().edit().putString(CodePointStore.KEY_INTERACTIONS, this.interactions.toString()).apply();
    }

    private Interactions loadInteractions() {
        String string = ApptentiveInternal.getInstance().getSharedPrefs().getString(CodePointStore.KEY_INTERACTIONS, null);
        if (string != null) {
            try {
                return new Interactions(string);
            } catch (Throwable e) {
                ApptentiveLog.w("Exception creating Interactions object.", e, new Object[0]);
            }
        }
        return null;
    }

    private void saveTargets() {
        ApptentiveInternal.getInstance().getSharedPrefs().edit().putString("targets", this.targets.toString()).apply();
    }

    private Targets loadTargets() {
        String string = ApptentiveInternal.getInstance().getSharedPrefs().getString("targets", null);
        if (string != null) {
            try {
                return new Targets(string);
            } catch (Throwable e) {
                ApptentiveLog.w("Exception creating Targets object.", e, new Object[0]);
            }
        }
        return null;
    }

    private boolean hasCacheExpired() {
        return ApptentiveInternal.getInstance().getSharedPrefs().getLong("interactionsCacheExpiration", 0) < System.currentTimeMillis();
    }

    public void updateCacheExpiration(long j) {
        ApptentiveInternal.getInstance().getSharedPrefs().edit().putLong("interactionsCacheExpiration", System.currentTimeMillis() + (1000 * j)).apply();
    }

    public boolean isPollForInteractions() {
        if (this.pollForInteractions == null) {
            this.pollForInteractions = Boolean.valueOf(ApptentiveInternal.getInstance().getSharedPrefs().getBoolean("pollForInteractions", true));
        }
        return this.pollForInteractions.booleanValue();
    }

    public void setPollForInteractions(boolean z) {
        this.pollForInteractions = Boolean.valueOf(z);
        ApptentiveInternal.getInstance().getSharedPrefs().edit().putBoolean("pollForInteractions", z).apply();
    }
}

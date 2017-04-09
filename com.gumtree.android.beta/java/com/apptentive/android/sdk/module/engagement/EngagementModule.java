package com.apptentive.android.sdk.module.engagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.apptentive.android.sdk.ApptentiveInternal;
import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.ApptentiveViewActivity;
import com.apptentive.android.sdk.model.Event;
import com.apptentive.android.sdk.model.EventManager;
import com.apptentive.android.sdk.model.ExtendedData;
import com.apptentive.android.sdk.module.engagement.interaction.model.Interaction;
import com.apptentive.android.sdk.module.engagement.interaction.model.MessageCenterInteraction;
import com.apptentive.android.sdk.module.metric.MetricModule;
import com.gumtree.android.dfp.DFPProcessor;
import java.util.Map;

public class EngagementModule {
    public static synchronized boolean engageInternal(Context context, String str) {
        boolean engage;
        synchronized (EngagementModule.class) {
            engage = engage(context, "com.apptentive", "app", null, str, null, null, (ExtendedData[]) null);
        }
        return engage;
    }

    public static synchronized boolean engageInternal(Context context, String str, String str2) {
        boolean engage;
        synchronized (EngagementModule.class) {
            engage = engage(context, "com.apptentive", "app", null, str, str2, null, (ExtendedData[]) null);
        }
        return engage;
    }

    public static synchronized boolean engageInternal(Context context, Interaction interaction, String str) {
        boolean engage;
        synchronized (EngagementModule.class) {
            engage = engage(context, "com.apptentive", interaction.getType().name(), interaction.getId(), str, null, null, (ExtendedData[]) null);
        }
        return engage;
    }

    public static synchronized boolean engageInternal(Context context, Interaction interaction, String str, String str2) {
        boolean engage;
        synchronized (EngagementModule.class) {
            engage = engage(context, "com.apptentive", interaction.getType().name(), interaction.getId(), str, str2, null, (ExtendedData[]) null);
        }
        return engage;
    }

    public static synchronized boolean engage(Context context, String str, String str2, String str3, String str4, String str5, Map<String, Object> map, ExtendedData... extendedDataArr) {
        boolean z;
        synchronized (EngagementModule.class) {
            if (!ApptentiveInternal.isApptentiveRegistered() || context == null) {
                z = false;
            } else {
                try {
                    String generateEventLabel = generateEventLabel(str, str2, str4);
                    ApptentiveLog.d("engage(%s)", generateEventLabel);
                    ApptentiveInternal.getInstance().getCodePointStore().storeCodePointForCurrentAppVersion(generateEventLabel);
                    EventManager.sendEvent(new Event(generateEventLabel, str3, str5, map, extendedDataArr));
                    z = doEngage(context, generateEventLabel);
                } catch (Throwable e) {
                    MetricModule.sendError(e, null, null);
                    z = false;
                }
            }
        }
        return z;
    }

    public static boolean doEngage(Context context, String str) {
        Interaction applicableInteraction = ApptentiveInternal.getInstance().getInteractionManager().getApplicableInteraction(str);
        if (applicableInteraction != null) {
            ApptentiveInternal.getInstance().getCodePointStore().storeInteractionForCurrentAppVersion(applicableInteraction.getId());
            launchInteraction(context, applicableInteraction);
            return true;
        }
        ApptentiveLog.d("No interaction to show.", new Object[0]);
        return false;
    }

    public static void launchInteraction(Context context, Interaction interaction) {
        if (interaction != null && context != null) {
            ApptentiveLog.i("Launching interaction: %s", interaction.getType().toString());
            Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(), ApptentiveViewActivity.class);
            intent.putExtra("fragmentType", 3);
            intent.putExtra("interaction", interaction.toString());
            if (!(context instanceof Activity)) {
                Context currentTaskStackTopActivity = ApptentiveInternal.getInstance().getCurrentTaskStackTopActivity();
                if (currentTaskStackTopActivity != null) {
                    context = currentTaskStackTopActivity;
                } else {
                    intent.addFlags(402653184);
                }
            }
            context.startActivity(intent);
        }
    }

    public static void launchMessageCenterErrorActivity(Context context) {
        if (context != null) {
            Intent generateMessageCenterErrorIntent = MessageCenterInteraction.generateMessageCenterErrorIntent(context);
            if (!(context instanceof Activity)) {
                generateMessageCenterErrorIntent.addFlags(402653184);
            }
            context.startActivity(generateMessageCenterErrorIntent);
        }
    }

    public static boolean canShowInteraction(String str, String str2, String str3) {
        return canShowInteraction(generateEventLabel(str, str2, str3));
    }

    private static boolean canShowInteraction(String str) {
        return ApptentiveInternal.getInstance().getInteractionManager().getApplicableInteraction(str) != null;
    }

    public static String generateEventLabel(String str, String str2, String str3) {
        return String.format("%s#%s#%s", new Object[]{encodeEventLabelPart(str), encodeEventLabelPart(str2), encodeEventLabelPart(str3)});
    }

    private static String encodeEventLabelPart(String str) {
        return str.replace("%", "%25").replace(DFPProcessor.SEPARATOR, "%2F").replace("#", "%23");
    }
}

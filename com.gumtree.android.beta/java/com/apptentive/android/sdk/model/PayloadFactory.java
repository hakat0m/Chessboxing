package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.model.Payload.BaseType;
import com.apptentive.android.sdk.module.messagecenter.model.MessageFactory;
import com.gumtree.android.logging.Timber;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import org.json.JSONException;

public class PayloadFactory {

    /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$apptentive$android$sdk$model$Payload$BaseType = new int[BaseType.values().length];

        static {
            try {
                $SwitchMap$com$apptentive$android$sdk$model$Payload$BaseType[BaseType.message.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$model$Payload$BaseType[BaseType.event.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$model$Payload$BaseType[BaseType.device.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$model$Payload$BaseType[BaseType.sdk.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$model$Payload$BaseType[BaseType.app_release.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$model$Payload$BaseType[BaseType.person.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$model$Payload$BaseType[BaseType.survey.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$model$Payload$BaseType[BaseType.unknown.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public static Payload fromJson(String str, BaseType baseType) {
        switch (AnonymousClass1.$SwitchMap$com$apptentive$android$sdk$model$Payload$BaseType[baseType.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                return MessageFactory.fromJson(str);
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return EventFactory.fromJson(str);
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                return DeviceFactory.fromJson(str);
            case HighlightView.GROW_RIGHT_EDGE /*4*/:
                return SdkFactory.fromJson(str);
            case Timber.WARN /*5*/:
                return AppReleaseFactory.fromJson(str);
            case Timber.ERROR /*6*/:
                return PersonFactory.fromJson(str);
            case Timber.ASSERT /*7*/:
                try {
                    return new SurveyResponse(str);
                } catch (JSONException e) {
                    break;
                }
            case HighlightView.GROW_TOP_EDGE /*8*/:
                break;
        }
        ApptentiveLog.v("Ignoring unknown RecordType.", new Object[0]);
        return null;
    }
}

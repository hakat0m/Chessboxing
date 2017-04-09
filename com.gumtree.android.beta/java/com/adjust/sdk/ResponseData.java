package com.adjust.sdk;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import java.util.Locale;
import org.json.JSONObject;

public abstract class ResponseData {
    public ActivityKind activityKind;
    public String adid;
    public AdjustAttribution attribution;
    public JSONObject jsonResponse;
    public String message;
    public boolean success;
    public String timestamp;
    public boolean willRetry;

    /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$adjust$sdk$ActivityKind = new int[ActivityKind.values().length];

        static {
            try {
                $SwitchMap$com$adjust$sdk$ActivityKind[ActivityKind.SESSION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$adjust$sdk$ActivityKind[ActivityKind.ATTRIBUTION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$adjust$sdk$ActivityKind[ActivityKind.EVENT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$adjust$sdk$ActivityKind[ActivityKind.CLICK.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static ResponseData buildResponseData(ActivityPackage activityPackage) {
        ResponseData sessionResponseData;
        ActivityKind activityKind = activityPackage.getActivityKind();
        switch (AnonymousClass1.$SwitchMap$com$adjust$sdk$ActivityKind[activityKind.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                sessionResponseData = new SessionResponseData();
                break;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                sessionResponseData = new AttributionResponseData();
                break;
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                sessionResponseData = new EventResponseData(activityPackage);
                break;
            case HighlightView.GROW_RIGHT_EDGE /*4*/:
                sessionResponseData = new ClickResponseData();
                break;
            default:
                sessionResponseData = new UnknownResponseData();
                break;
        }
        sessionResponseData.activityKind = activityKind;
        return sessionResponseData;
    }

    public String toString() {
        return String.format(Locale.US, "message:%s timestamp:%s json:%s", new Object[]{this.message, this.timestamp, this.jsonResponse});
    }
}

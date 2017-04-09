package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.BuildConfig;
import com.apptentive.android.sdk.model.Payload.BaseType;
import com.apptentive.android.sdk.module.engagement.interaction.model.SurveyInteraction;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class SurveyResponse extends ConversationItem {
    private static final String KEY_SURVEY_ANSWERS = "answers";
    private static final String KEY_SURVEY_ID = "id";

    public SurveyResponse(String str) throws JSONException {
        super(str);
    }

    public SurveyResponse(SurveyInteraction surveyInteraction, Map<String, Object> map) {
        try {
            put(KEY_SURVEY_ID, surveyInteraction.getId());
            JSONObject jSONObject = new JSONObject();
            for (String str : map.keySet()) {
                jSONObject.put(str, map.get(str));
            }
            put(KEY_SURVEY_ANSWERS, jSONObject);
        } catch (Throwable e) {
            ApptentiveLog.e("Unable to construct survey payload.", e, new Object[0]);
        }
    }

    public String getId() {
        return optString(KEY_SURVEY_ID, BuildConfig.FLAVOR);
    }

    protected void initBaseType() {
        setBaseType(BaseType.survey);
    }
}

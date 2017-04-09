package com.gumtree.android.alerts.parser;

import com.gumtree.android.alerts.Alert;
import com.gumtree.android.common.gson.Parser;
import com.gumtree.android.common.http.model.CapiApiException;
import com.gumtree.android.common.utils.XmlStringUtils;
import com.gumtree.android.model.ManagedAds;
import java.io.InputStream;

public class AlertResponseParser implements Parser<Alert> {
    private static final String AD_STATUS_END_TAG = "</ad:value></ad:status>";
    private static final String AD_STATUS_START_TAG = "<ad:status><ad:value>";
    private static final String DESCRIPTION_END_TAG = "</ad:search-description>";
    private static final String DESCRIPTION_START_TAG = "<ad:search-description>";
    private static final String ID_END_TAG = "\">";
    private static final String ID_START_TAG = "id=\"";

    public Alert parse(InputStream inputStream) {
        if (inputStream == null) {
            throw new CapiApiException();
        }
        String xmlStringUtils = XmlStringUtils.toString(inputStream);
        if (xmlStringUtils.contains("<api-base-error")) {
            throw new CapiApiException();
        }
        Alert alert = new Alert();
        alert.setId(XmlStringUtils.getInTags(xmlStringUtils, ID_START_TAG, ID_END_TAG));
        alert.setDescription(XmlStringUtils.getInTags(xmlStringUtils, DESCRIPTION_START_TAG, DESCRIPTION_END_TAG));
        alert.setEmailAlertEnabled(getStatusEmailAlert(XmlStringUtils.getInTags(xmlStringUtils, AD_STATUS_START_TAG, AD_STATUS_END_TAG)));
        return alert;
    }

    private boolean getStatusEmailAlert(String str) {
        if (ManagedAds.STATUS_ACTIVE.equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }
}

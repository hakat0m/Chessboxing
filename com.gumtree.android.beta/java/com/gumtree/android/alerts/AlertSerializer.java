package com.gumtree.android.alerts;

import com.gumtree.android.model.ManagedAds;

public class AlertSerializer {
    public String serialize(Alert alert) {
        return "<alert xmlns=\"http://www.ebayclassifiedsgroup.com/schema/ad/v1\"><alert-destinations><alert-destination><destination-type><value>EMAIL</value></destination-type><status><value>" + convertEmailAlertFlag(alert) + "</value></status>" + "</alert-destination>" + "</alert-destinations>" + "<alert-type><value>SAVED_SEARCH</value></alert-type>" + "<search-link rel=\"saved-search\" href=\"" + alert.getLink() + "\"/>" + "</alert>";
    }

    private String convertEmailAlertFlag(Alert alert) {
        if (alert.isEmailAlertEnabled()) {
            return ManagedAds.STATUS_ACTIVE;
        }
        return "INACTIVE";
    }
}

package com.gumtree.android.alerts;

import com.adjust.sdk.Constants;
import com.apptentive.android.sdk.BuildConfig;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Alert implements Serializable {
    private static final String IN = "in ";
    private static final String QUOTE = "\"";
    private static final String QUOTE_XML = "&quot;";
    private static final String SAVED_SEARCH = "SAVED_SEARCH";
    private String category;
    private String description;
    private boolean emailAlertEnabled;
    private String id;
    private String link;
    private String location;
    private String title;
    private String type;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String str) {
        this.link = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        if (str != null) {
            this.description = str;
            int lastIndexOf = str.lastIndexOf(QUOTE);
            int length = QUOTE.length();
            if (lastIndexOf == -1) {
                lastIndexOf = str.lastIndexOf(QUOTE_XML);
                length = QUOTE_XML.length();
            }
            if (lastIndexOf > 1) {
                this.title = decode(str.substring(length, lastIndexOf));
                str = str.substring(lastIndexOf);
            }
            String[] split = str.split(" in ");
            if (split.length >= 2) {
                this.location = split[split.length - 1];
                this.category = split[split.length - 2];
                if (this.category.startsWith(IN)) {
                    this.category = this.category.replaceFirst(IN, BuildConfig.FLAVOR);
                }
            }
        }
    }

    private String decode(String str) {
        if (str == null) {
            return null;
        }
        try {
            return URLDecoder.decode(str, Constants.ENCODING);
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public String getLocation() {
        return this.location;
    }

    public String getCategory() {
        return this.category;
    }

    public void setType(String str) {
        this.type = str;
    }

    public boolean isSavedSearch() {
        return SAVED_SEARCH.equals(this.type);
    }

    public String getEncodedLocation() {
        return getEncodedString(this.location);
    }

    public String getEncodedCategory() {
        return getEncodedString(this.category);
    }

    private String getEncodedString(String str) {
        if (str == null) {
            return BuildConfig.FLAVOR;
        }
        return str.replace(" ", "%20");
    }

    public boolean isEmailAlertEnabled() {
        return this.emailAlertEnabled;
    }

    public void setEmailAlertEnabled(boolean z) {
        this.emailAlertEnabled = z;
    }

    public int isEmailAlertEnabledAsInt() {
        if (this.emailAlertEnabled) {
            return 1;
        }
        return 0;
    }

    public String getTitle() {
        return this.title;
    }
}

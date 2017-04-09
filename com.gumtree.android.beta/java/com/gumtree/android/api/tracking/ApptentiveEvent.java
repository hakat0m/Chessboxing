package com.gumtree.android.api.tracking;

public enum ApptentiveEvent {
    WATCHLIST_ADD("Watchlist_Add"),
    WATCHLIST_DELETE("Watchlist_Delete"),
    INIT("init"),
    POST_AD_SUCCESS("Post_Ad_Success"),
    POST_AD_FAIL("Post_Ad_Fail"),
    SAVED_SEARCHES_DELETE("Save_Search_Delete"),
    SAVED_SEARCHES_SUCCESS("Save_Search"),
    EDIT_AD_SUCCESS("Edit_Ad_Success"),
    EDIT_AD_FAIL("Edit_Ad_Fail"),
    FEATURE_AD_SUCCESS("Feature_Ad_Success"),
    FEATURE_AD_FAIL("Feature_Ad_Fail"),
    FEATURE_AD_CANCEL("Feature_Ad_Cancel"),
    REPLY_EMAIL_SUCCESS("R2S_Email_Success");
    
    private final String value;

    private ApptentiveEvent(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }
}

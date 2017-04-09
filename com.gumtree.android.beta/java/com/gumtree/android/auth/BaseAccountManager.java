package com.gumtree.android.auth;

import com.ebay.classifieds.capi.users.login.UserLogin;
import com.ebay.classifieds.capi.users.profile.UserProfile;

public interface BaseAccountManager {
    public static final String KEY_CONTACT_ID = "key_contact_email";
    public static final String KEY_CONTACT_PHONE = "key_contact_phone";
    public static final String KEY_DISPLAY_NAME = "key_display_name";
    public static final String KEY_HASHED_EMAIL = "key_hashed_email";
    public static final String KEY_LOC_ID = "key_loc_id";
    public static final String KEY_LOC_MAP = "key_loc_map";
    public static final String KEY_LOC_NAME = "key_loc_name";
    public static final String KEY_LOC_NEIGHBOURHOOD = "key_loc_txt";
    public static final String KEY_LOC_POSTCODE = "key_loc_postcode";
    public static final String KEY_LOGIN_PROVIDER = "key_loginProvider";
    public static final String KEY_PASSWORD = "key_password";
    public static final String KEY_PROGRESS = "key_progress";
    public static final String KEY_REL = "key_rel";
    public static final String KEY_USERNAME = "key_username";
    public static final String KEY_USER_ID = "key_user_id";
    public static final String PREF_SETUP_COMPLETE = "setup_complete";
    public static final long SYNC_FREQUENCY = 43200;

    void createAccount(boolean z, UserLogin userLogin);

    String getAuthentication();

    String getAuthenticationToken();

    String getContactEmail();

    String getContactPhone();

    String getDisplayName();

    String getHashedEmail();

    String getPassword();

    String getUserId();

    UserLocation getUserLocation();

    String getUsername();

    void initialiseSync();

    boolean isUserLoggedIn();

    void removeAccount(AccountRemovedCallback accountRemovedCallback);

    void setContactEmail(String str);

    void setContactPhone(String str);

    void setDisplayName(String str);

    void setHashedEmail(String str);

    void setUserLocation(UserLocation userLocation);

    void triggerSearchSync();

    void updateProfile(UserProfile userProfile);
}

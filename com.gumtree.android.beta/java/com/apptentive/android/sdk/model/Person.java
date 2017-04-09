package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.model.Payload.BaseType;
import org.json.JSONException;

public class Person extends Payload {
    public static final String KEY = "person";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_CITY = "city";
    private static final String KEY_COUNTRY = "country";
    public static final String KEY_CUSTOM_DATA = "custom_data";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_FACEBOOK_ID = "facebook_id";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_STREET = "street";
    private static final String KEY_ZIP = "zip";

    public Person(String str) throws JSONException {
        super(str);
    }

    public void initBaseType() {
        setBaseType(BaseType.person);
    }

    public String getId() {
        try {
            if (!isNull(KEY_ID)) {
                return getString(KEY_ID);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setId(String str) {
        try {
            put(KEY_ID, str);
        } catch (JSONException e) {
            ApptentiveLog.e("Unable to set field id of person", new Object[0]);
        }
    }

    public String getEmail() {
        try {
            if (!isNull(KEY_EMAIL)) {
                return getString(KEY_EMAIL);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setEmail(String str) {
        try {
            put(KEY_EMAIL, str);
        } catch (JSONException e) {
            ApptentiveLog.e("Unable to set field email of person", new Object[0]);
        }
    }

    public String getName() {
        try {
            if (!isNull(KEY_NAME)) {
                return getString(KEY_NAME);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setName(String str) {
        try {
            put(KEY_NAME, str);
        } catch (JSONException e) {
            ApptentiveLog.e("Unable to set field name of person", new Object[0]);
        }
    }

    public String getFacebookId() {
        try {
            if (!isNull(KEY_FACEBOOK_ID)) {
                return getString(KEY_FACEBOOK_ID);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setFacebookId(String str) {
        try {
            put(KEY_FACEBOOK_ID, str);
        } catch (JSONException e) {
            ApptentiveLog.e("Unable to set field facebook_id of person", new Object[0]);
        }
    }

    public String getPhoneNumber() {
        try {
            if (!isNull(KEY_PHONE_NUMBER)) {
                return getString(KEY_PHONE_NUMBER);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setPhoneNumber(String str) {
        try {
            put(KEY_PHONE_NUMBER, str);
        } catch (JSONException e) {
            ApptentiveLog.e("Unable to set field phone_number of person", new Object[0]);
        }
    }

    public String getStreet() {
        try {
            if (!isNull(KEY_STREET)) {
                return getString(KEY_STREET);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setStreet(String str) {
        try {
            put(KEY_STREET, str);
        } catch (JSONException e) {
            ApptentiveLog.e("Unable to set field street of person", new Object[0]);
        }
    }

    public String getCity() {
        try {
            if (!isNull(KEY_CITY)) {
                return getString(KEY_CITY);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setCity(String str) {
        try {
            put(KEY_CITY, str);
        } catch (JSONException e) {
            ApptentiveLog.e("Unable to set field city of person", new Object[0]);
        }
    }

    public String getZip() {
        try {
            if (!isNull(KEY_ZIP)) {
                return getString(KEY_ZIP);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setZip(String str) {
        try {
            put(KEY_ZIP, str);
        } catch (JSONException e) {
            ApptentiveLog.e("Unable to set field zip of person", new Object[0]);
        }
    }

    public String getCountry() {
        try {
            if (!isNull(KEY_COUNTRY)) {
                return getString(KEY_COUNTRY);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setCountry(String str) {
        try {
            put(KEY_COUNTRY, str);
        } catch (JSONException e) {
            ApptentiveLog.e("Unable to set field country of person", new Object[0]);
        }
    }

    public String getBirthday() {
        try {
            if (!isNull(KEY_BIRTHDAY)) {
                return getString(KEY_BIRTHDAY);
            }
        } catch (JSONException e) {
        }
        return null;
    }

    public void setBirthday(String str) {
        try {
            put(KEY_BIRTHDAY, str);
        } catch (JSONException e) {
            ApptentiveLog.e("Unable to set field birthday of person", new Object[0]);
        }
    }

    public CustomData getCustomData() {
        if (!isNull(KEY_CUSTOM_DATA)) {
            try {
                return new CustomData(getJSONObject(KEY_CUSTOM_DATA));
            } catch (JSONException e) {
            }
        }
        return null;
    }

    public void setCustomData(CustomData customData) {
        try {
            put(KEY_CUSTOM_DATA, customData);
        } catch (JSONException e) {
            ApptentiveLog.w("Error adding %s to Device.", KEY_CUSTOM_DATA);
        }
    }
}

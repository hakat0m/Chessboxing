package com.gumtree.android.postad.contactdetails.models;

import com.gumtree.android.location.model.LocationData;
import java.beans.ConstructorProperties;
import java.io.Serializable;

public class ContactDetailsData implements Serializable {
    private static final long serialVersionUID = -8845656286173218213L;
    private String email;
    private boolean isEmailSelected;
    private boolean isLocationEnabled;
    private boolean isMapVisibleOnAd;
    private boolean isPhoneSelected;
    private LocationData locationData;
    private String phone;

    @ConstructorProperties({"isMapVisibleOnAd", "locationData", "email", "isEmailSelected", "phone", "isPhoneSelected", "isLocationEnabled"})
    public ContactDetailsData(boolean z, LocationData locationData, String str, boolean z2, String str2, boolean z3, boolean z4) {
        this.isMapVisibleOnAd = z;
        this.locationData = locationData;
        this.email = str;
        this.isEmailSelected = z2;
        this.phone = str2;
        this.isPhoneSelected = z3;
        this.isLocationEnabled = z4;
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof ContactDetailsData;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ContactDetailsData)) {
            return false;
        }
        ContactDetailsData contactDetailsData = (ContactDetailsData) obj;
        if (!contactDetailsData.canEqual(this)) {
            return false;
        }
        if (isMapVisibleOnAd() != contactDetailsData.isMapVisibleOnAd()) {
            return false;
        }
        LocationData locationData = getLocationData();
        LocationData locationData2 = contactDetailsData.getLocationData();
        if (locationData != null ? !locationData.equals(locationData2) : locationData2 != null) {
            return false;
        }
        String email = getEmail();
        String email2 = contactDetailsData.getEmail();
        if (email != null ? !email.equals(email2) : email2 != null) {
            return false;
        }
        if (isEmailSelected() != contactDetailsData.isEmailSelected()) {
            return false;
        }
        email = getPhone();
        email2 = contactDetailsData.getPhone();
        if (email != null ? !email.equals(email2) : email2 != null) {
            return false;
        }
        if (isPhoneSelected() != contactDetailsData.isPhoneSelected()) {
            return false;
        }
        return isLocationEnabled() == contactDetailsData.isLocationEnabled();
    }

    public int hashCode() {
        int i = 43;
        int i2 = 79;
        int i3 = (isMapVisibleOnAd() ? 79 : 97) + 59;
        LocationData locationData = getLocationData();
        i3 = (locationData == null ? 43 : locationData.hashCode()) + (i3 * 59);
        String email = getEmail();
        i3 = (isEmailSelected() ? 79 : 97) + (((email == null ? 43 : email.hashCode()) + (i3 * 59)) * 59);
        email = getPhone();
        i3 *= 59;
        if (email != null) {
            i = email.hashCode();
        }
        i3 = ((isPhoneSelected() ? 79 : 97) + ((i3 + i) * 59)) * 59;
        if (!isLocationEnabled()) {
            i2 = 97;
        }
        return i3 + i2;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public void setEmailSelected(boolean z) {
        this.isEmailSelected = z;
    }

    public void setLocationData(LocationData locationData) {
        this.locationData = locationData;
    }

    public void setLocationEnabled(boolean z) {
        this.isLocationEnabled = z;
    }

    public void setMapVisibleOnAd(boolean z) {
        this.isMapVisibleOnAd = z;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public void setPhoneSelected(boolean z) {
        this.isPhoneSelected = z;
    }

    public String toString() {
        return "ContactDetailsData(isMapVisibleOnAd=" + isMapVisibleOnAd() + ", locationData=" + getLocationData() + ", email=" + getEmail() + ", isEmailSelected=" + isEmailSelected() + ", phone=" + getPhone() + ", isPhoneSelected=" + isPhoneSelected() + ", isLocationEnabled=" + isLocationEnabled() + ")";
    }

    public static ContactDetailsDataBuilder builder() {
        return new ContactDetailsDataBuilder();
    }

    public boolean isMapVisibleOnAd() {
        return this.isMapVisibleOnAd;
    }

    public LocationData getLocationData() {
        return this.locationData;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean isEmailSelected() {
        return this.isEmailSelected;
    }

    public String getPhone() {
        return this.phone;
    }

    public boolean isPhoneSelected() {
        return this.isPhoneSelected;
    }

    public boolean isLocationEnabled() {
        return this.isLocationEnabled;
    }

    public ContactDetailsData(boolean z, LocationData locationData, boolean z2) {
        this.isMapVisibleOnAd = z;
        this.locationData = locationData;
        this.isLocationEnabled = z2;
    }
}

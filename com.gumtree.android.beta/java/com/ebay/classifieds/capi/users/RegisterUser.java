package com.ebay.classifieds.capi.users;

import com.ebay.classifieds.capi.users.profile.UserProfile;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "user-registration", strict = false)
@Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
public final class RegisterUser implements Serializable {
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "email", required = true)
    private final String email;
    @Attribute(name = "locale")
    private final String locale = "en-UK";
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "password", required = true)
    private final String password;
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "rel", required = true)
    private final String rel;
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "user-profile", required = true)
    private final UserProfile userProfile;
    @Attribute(name = "version")
    private final String version = "1.15";

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RegisterUser)) {
            return false;
        }
        RegisterUser registerUser = (RegisterUser) obj;
        String locale = getLocale();
        String locale2 = registerUser.getLocale();
        if (locale != null ? !locale.equals(locale2) : locale2 != null) {
            return false;
        }
        locale = getVersion();
        locale2 = registerUser.getVersion();
        if (locale != null ? !locale.equals(locale2) : locale2 != null) {
            return false;
        }
        locale = getEmail();
        locale2 = registerUser.getEmail();
        if (locale != null ? !locale.equals(locale2) : locale2 != null) {
            return false;
        }
        locale = getPassword();
        locale2 = registerUser.getPassword();
        if (locale != null ? !locale.equals(locale2) : locale2 != null) {
            return false;
        }
        locale = getRel();
        locale2 = registerUser.getRel();
        if (locale != null ? !locale.equals(locale2) : locale2 != null) {
            return false;
        }
        UserProfile userProfile = getUserProfile();
        UserProfile userProfile2 = registerUser.getUserProfile();
        if (userProfile == null) {
            if (userProfile2 == null) {
                return true;
            }
        } else if (userProfile.equals(userProfile2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String locale = getLocale();
        int hashCode = (locale == null ? 43 : locale.hashCode()) + 59;
        String version = getVersion();
        hashCode = (version == null ? 43 : version.hashCode()) + (hashCode * 59);
        version = getEmail();
        hashCode = (version == null ? 43 : version.hashCode()) + (hashCode * 59);
        version = getPassword();
        hashCode = (version == null ? 43 : version.hashCode()) + (hashCode * 59);
        version = getRel();
        hashCode = (version == null ? 43 : version.hashCode()) + (hashCode * 59);
        UserProfile userProfile = getUserProfile();
        hashCode *= 59;
        if (userProfile != null) {
            i = userProfile.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "RegisterUser(locale=" + getLocale() + ", version=" + getVersion() + ", email=" + getEmail() + ", password=" + getPassword() + ", rel=" + getRel() + ", userProfile=" + getUserProfile() + ")";
    }

    @ConstructorProperties({"email", "password", "rel", "userProfile"})
    public RegisterUser(String str, String str2, String str3, UserProfile userProfile) {
        this.email = str;
        this.password = str2;
        this.rel = str3;
        this.userProfile = userProfile;
    }

    public String getLocale() {
        getClass();
        return "en-UK";
    }

    public String getVersion() {
        getClass();
        return "1.15";
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRel() {
        return this.rel;
    }

    public UserProfile getUserProfile() {
        return this.userProfile;
    }
}

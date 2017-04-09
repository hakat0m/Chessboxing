package com.ebay.classifieds.capi.users;

import java.beans.ConstructorProperties;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "user-activation", strict = false)
@Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
public final class ActivateUser {
    @Attribute(name = "locale")
    private final String locale = "en-UK";
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "signature", required = true)
    private final String signature;
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "token", required = true)
    private final String token;
    @Attribute(name = "version")
    private final String version = "1.15";

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ActivateUser)) {
            return false;
        }
        ActivateUser activateUser = (ActivateUser) obj;
        String locale = getLocale();
        String locale2 = activateUser.getLocale();
        if (locale != null ? !locale.equals(locale2) : locale2 != null) {
            return false;
        }
        locale = getVersion();
        locale2 = activateUser.getVersion();
        if (locale != null ? !locale.equals(locale2) : locale2 != null) {
            return false;
        }
        locale = getToken();
        locale2 = activateUser.getToken();
        if (locale != null ? !locale.equals(locale2) : locale2 != null) {
            return false;
        }
        locale = getSignature();
        locale2 = activateUser.getSignature();
        if (locale == null) {
            if (locale2 == null) {
                return true;
            }
        } else if (locale.equals(locale2)) {
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
        version = getToken();
        hashCode = (version == null ? 43 : version.hashCode()) + (hashCode * 59);
        version = getSignature();
        hashCode *= 59;
        if (version != null) {
            i = version.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "ActivateUser(locale=" + getLocale() + ", version=" + getVersion() + ", token=" + getToken() + ", signature=" + getSignature() + ")";
    }

    @ConstructorProperties({"token", "signature"})
    public ActivateUser(String str, String str2) {
        this.token = str;
        this.signature = str2;
    }

    public String getLocale() {
        getClass();
        return "en-UK";
    }

    public String getVersion() {
        getClass();
        return "1.15";
    }

    public String getToken() {
        return this.token;
    }

    public String getSignature() {
        return this.signature;
    }
}

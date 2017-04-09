package com.ebay.classifieds.capi.users.login;

import java.io.Serializable;
import java.util.ArrayList;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "user:user-logins", strict = false)
public class UserLogins implements Serializable {
    @Attribute(name = "locale")
    private String locale;
    @ElementList(entry = "user-login", inline = true)
    private ArrayList<UserLogin> userLogins;
    @Attribute(name = "version")
    private String version;

    public ArrayList<UserLogin> getList() {
        return this.userLogins;
    }

    public String getLocale() {
        return this.locale;
    }

    public String getVersion() {
        return this.version;
    }
}

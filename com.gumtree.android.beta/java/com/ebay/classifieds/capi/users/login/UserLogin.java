package com.ebay.classifieds.capi.users.login;

import java.io.Serializable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "user-login", strict = false)
@Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
public class UserLogin implements Serializable {
    @Element(name = "password", required = false)
    private String password;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "email")
    private String userEmail;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "id")
    private String userId;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "token")
    private String userToken;

    public String getUserId() {
        return this.userId;
    }

    public String getUserToken() {
        return this.userToken;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String str) {
        this.password = str;
    }
}

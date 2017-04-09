package com.ebay.classifieds.capi.users.resetpassword;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "reset-password", strict = false)
@Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
public class ResetPasswordBody {
    private static final String NAMESPACE_REFERENCE = "http://www.ebayclassifiedsgroup.com/schema/user/v1";
    private static final String PREFIX = "user";
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "email")
    private String email;
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "password")
    private String password;
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "token")
    private String token;

    public ResetPasswordBody(String str, String str2, String str3) {
        this.email = str;
        this.password = str2;
        this.token = str3;
    }
}

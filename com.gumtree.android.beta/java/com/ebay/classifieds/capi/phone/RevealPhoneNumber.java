package com.ebay.classifieds.capi.phone;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "reveal-phone-number", strict = false)
@Namespace(prefix = "ad", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
public class RevealPhoneNumber {
    @Namespace(prefix = "ad", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "phone", required = false)
    private Phone phone;

    public Phone getPhone() {
        return this.phone;
    }
}

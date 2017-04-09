package com.ebay.classifieds.capi.replies;

import com.gumtree.android.model.Messages;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

@Root(strict = false)
@Convert(ReplyTypeConverter.class)
public enum ReplyTypes {
    TO_OWNER(Messages.DIRECTION_SELLER);
    
    @Element
    private String types;

    private ReplyTypes(String str) {
        this.types = str;
    }

    public String getTypes() {
        return this.types;
    }
}

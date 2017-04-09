package com.ebay.classifieds.capi.users.cv;

import java.util.ArrayList;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "cv:stored-cvs", strict = false)
public class CVs {
    @Attribute(name = "locale")
    private String locale;
    @ElementList(entry = "stored-cv", inline = true, required = false)
    private ArrayList<StoredCV> storedCVs = new ArrayList();
    @Attribute(name = "version")
    private String version;

    public ArrayList<StoredCV> getList() {
        return this.storedCVs;
    }

    public String getLocale() {
        return this.locale;
    }

    public String getVersion() {
        return this.version;
    }
}

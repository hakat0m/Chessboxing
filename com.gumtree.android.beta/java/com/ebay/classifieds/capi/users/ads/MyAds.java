package com.ebay.classifieds.capi.users.ads;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.common.Paging;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "ads", strict = false)
@Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
public class MyAds {
    @ElementList(inline = true, required = false)
    private List<MyAd> list;
    @Namespace(prefix = "types")
    @Element(name = "paging", required = false)
    private Paging paging;

    protected boolean canEqual(Object obj) {
        return obj instanceof MyAds;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MyAds)) {
            return false;
        }
        MyAds myAds = (MyAds) obj;
        if (!myAds.canEqual(this)) {
            return false;
        }
        List list = getList();
        List list2 = myAds.getList();
        if (list != null ? !list.equals(list2) : list2 != null) {
            return false;
        }
        Paging paging = getPaging();
        Paging paging2 = myAds.getPaging();
        if (paging == null) {
            if (paging2 == null) {
                return true;
            }
        } else if (paging.equals(paging2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        List list = getList();
        int hashCode = (list == null ? 43 : list.hashCode()) + 59;
        Paging paging = getPaging();
        hashCode *= 59;
        if (paging != null) {
            i = paging.hashCode();
        }
        return hashCode + i;
    }

    public void setList(List<MyAd> list) {
        this.list = list;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public String toString() {
        return "MyAds(list=" + getList() + ", paging=" + getPaging() + ")";
    }

    public MyAds() {
        this.list = new ArrayList();
        this.paging = new Paging();
    }

    public MyAds(@ElementList(inline = true) List<MyAd> list, @Element(name = "paging") Paging paging) {
        if (list == null) {
            list = new ArrayList();
        }
        this.list = list;
        this.paging = (Paging) Validate.notNull(paging);
    }

    public Paging getPaging() {
        return this.paging;
    }

    @NonNull
    public List<MyAd> getList() {
        return this.list;
    }
}

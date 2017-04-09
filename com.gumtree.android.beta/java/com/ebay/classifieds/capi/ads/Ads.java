package com.ebay.classifieds.capi.ads;

import com.ebay.classifieds.capi.common.Paging;
import java.beans.ConstructorProperties;
import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "ads", strict = false)
@Namespace(prefix = "ad", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
public class Ads {
    @ElementList(inline = true, required = false)
    private List<Ad> ads;
    @Attribute(name = "locale", required = false)
    private String locale;
    @Element(name = "paging")
    private Paging paging;
    @Attribute(name = "version", required = false)
    private String version;

    @ConstructorProperties({"locale", "version", "ads", "paging"})
    public Ads(String str, String str2, List<Ad> list, Paging paging) {
        this.locale = str;
        this.version = str2;
        this.ads = list;
        this.paging = paging;
    }

    public static AdsBuilder builder() {
        return new AdsBuilder();
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof Ads;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Ads)) {
            return false;
        }
        Ads ads = (Ads) obj;
        if (!ads.canEqual(this)) {
            return false;
        }
        String locale = getLocale();
        String locale2 = ads.getLocale();
        if (locale != null ? !locale.equals(locale2) : locale2 != null) {
            return false;
        }
        locale = getVersion();
        locale2 = ads.getVersion();
        if (locale != null ? !locale.equals(locale2) : locale2 != null) {
            return false;
        }
        List ads2 = getAds();
        List ads3 = ads.getAds();
        if (ads2 != null ? !ads2.equals(ads3) : ads3 != null) {
            return false;
        }
        Paging paging = getPaging();
        Paging paging2 = ads.getPaging();
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
        String locale = getLocale();
        int hashCode = (locale == null ? 43 : locale.hashCode()) + 59;
        String version = getVersion();
        hashCode = (version == null ? 43 : version.hashCode()) + (hashCode * 59);
        List ads = getAds();
        hashCode = (ads == null ? 43 : ads.hashCode()) + (hashCode * 59);
        Paging paging = getPaging();
        hashCode *= 59;
        if (paging != null) {
            i = paging.hashCode();
        }
        return hashCode + i;
    }

    public void setAds(List<Ad> list) {
        this.ads = list;
    }

    public void setLocale(String str) {
        this.locale = str;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String toString() {
        return "Ads(locale=" + getLocale() + ", version=" + getVersion() + ", ads=" + getAds() + ", paging=" + getPaging() + ")";
    }

    public String getLocale() {
        return this.locale;
    }

    public String getVersion() {
        return this.version;
    }

    public List<Ad> getAds() {
        return this.ads;
    }

    public Paging getPaging() {
        return this.paging;
    }

    public Ads(List<Ad> list) {
        this.ads = list;
    }
}

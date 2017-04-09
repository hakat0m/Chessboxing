package com.ebay.classifieds.capi.ads;

import android.support.annotation.NonNull;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.categories.Category;
import com.ebay.classifieds.capi.features.Feature.Type;
import com.ebay.classifieds.capi.locations.Location;
import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "ad", strict = false)
@Namespace(prefix = "ad", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
public class Ad {
    @Namespace(prefix = "feat", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @ElementList(name = "features-active", required = false)
    private ArrayList<ActiveFeature> activeFeatures;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "ad-address", required = false)
    private AdAddress adAddress;
    @Namespace(prefix = "attr", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @ElementList(name = "attributes", required = false)
    private ArrayList<Attribute> attributes;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "category", required = false)
    private Category category;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "poster-contact-name", required = false)
    private String contactName;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "description", required = false)
    private String description;
    @Attribute(name = "id", required = false)
    private String id;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @ElementList(inline = true, required = false)
    private ArrayList<Link> links;
    @Attribute(name = "locale", required = false)
    private String locale;
    @Namespace(prefix = "loc", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @ElementList(name = "locations", required = false)
    private ArrayList<Location> locations;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "modification-date-time", required = false)
    private String modificationDate;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "neighborhood", required = false)
    private String neighborhood;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "phone", required = false)
    private String phone;
    @Namespace(prefix = "pic", reference = "http://www.ebayclassifiedsgroup.com/schema/picture/v1")
    @ElementList(name = "pictures", required = false)
    private ArrayList<Picture> pictures;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "posting-since", required = false)
    private String postingSince;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "price", required = false)
    private Price price;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "poster-contact-email", required = false)
    private String replyLink;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "reply-template", required = false)
    private String replyTemplate;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "search-distance", required = false)
    private SearchDistance searchDistance;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "start-date-time", required = false)
    private String startDate;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "ad-status", required = false)
    private Status status;
    @Attribute(name = "supported-locales", required = false)
    private String supportedLocales;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "title", required = false)
    private String title;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "user-id", required = false)
    private String userId;
    @Attribute(name = "version", required = false)
    private String version;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "visible-on-map", required = false)
    private boolean visibleOnMap;

    @ConstructorProperties({"title", "description", "adAddress", "userId", "replyTemplate", "postingSince", "phone", "modificationDate", "startDate", "visibleOnMap", "contactName", "status", "neighborhood", "replyLink", "searchDistance", "links", "locations", "price", "activeFeatures", "category", "pictures", "attributes", "supportedLocales", "locale", "version", "id"})
    public Ad(String str, String str2, AdAddress adAddress, String str3, String str4, String str5, String str6, String str7, String str8, boolean z, String str9, Status status, String str10, String str11, SearchDistance searchDistance, ArrayList<Link> arrayList, ArrayList<Location> arrayList2, Price price, ArrayList<ActiveFeature> arrayList3, Category category, ArrayList<Picture> arrayList4, ArrayList<Attribute> arrayList5, String str12, String str13, String str14, String str15) {
        this.title = str;
        this.description = str2;
        this.adAddress = adAddress;
        this.userId = str3;
        this.replyTemplate = str4;
        this.postingSince = str5;
        this.phone = str6;
        this.modificationDate = str7;
        this.startDate = str8;
        this.visibleOnMap = z;
        this.contactName = str9;
        this.status = status;
        this.neighborhood = str10;
        this.replyLink = str11;
        this.searchDistance = searchDistance;
        this.links = arrayList;
        this.locations = arrayList2;
        this.price = price;
        this.activeFeatures = arrayList3;
        this.category = category;
        this.pictures = arrayList4;
        this.attributes = arrayList5;
        this.supportedLocales = str12;
        this.locale = str13;
        this.version = str14;
        this.id = str15;
    }

    public static AdBuilder builder() {
        return new AdBuilder();
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public AdAddress getAdAddress() {
        return this.adAddress;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getReplyTemplate() {
        return this.replyTemplate;
    }

    public String getPostingSince() {
        return this.postingSince;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getModificationDate() {
        return this.modificationDate;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public boolean isVisibleOnMap() {
        return this.visibleOnMap;
    }

    public String getContactName() {
        return this.contactName;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getNeighborhood() {
        return this.neighborhood;
    }

    public String getReplyLink() {
        return this.replyLink;
    }

    public SearchDistance getSearchDistance() {
        return this.searchDistance;
    }

    public ArrayList<Link> getLinks() {
        return this.links;
    }

    public ArrayList<Location> getLocations() {
        return this.locations;
    }

    public Price getPrice() {
        return this.price;
    }

    public ArrayList<ActiveFeature> getActiveFeatures() {
        return this.activeFeatures;
    }

    public Category getCategory() {
        return this.category;
    }

    public ArrayList<Picture> getPictures() {
        return this.pictures;
    }

    public ArrayList<Attribute> getAttributes() {
        return this.attributes;
    }

    public String getSupportedLocales() {
        return this.supportedLocales;
    }

    public String getLocale() {
        return this.locale;
    }

    public String getVersion() {
        return this.version;
    }

    public String getId() {
        return this.id;
    }

    public boolean isUrgent() {
        if (this.activeFeatures != null) {
            Iterator it = this.activeFeatures.iterator();
            while (it.hasNext()) {
                if (Type.URGENT == ((ActiveFeature) it.next()).getType()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFeatured() {
        if (this.activeFeatures != null) {
            Iterator it = this.activeFeatures.iterator();
            while (it.hasNext()) {
                if (Type.TOP_AD == ((ActiveFeature) it.next()).getType()) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getPublicWebsite() {
        Iterator it = this.links.iterator();
        while (it.hasNext()) {
            Link link = (Link) it.next();
            if ("self-public-website".equals(link.getRel())) {
                return link.getHref();
            }
        }
        return BuildConfig.FLAVOR;
    }

    @NonNull
    public String getLocationsName() {
        if (this.locations == null) {
            return BuildConfig.FLAVOR;
        }
        List arrayList = new ArrayList();
        Iterator it = this.locations.iterator();
        while (it.hasNext()) {
            arrayList.add(((Location) it.next()).getLocalizedName());
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (this.neighborhood == null || this.neighborhood.length() <= 0) {
            stringBuilder.append((String) arrayList.get(arrayList.size() - 1)).append(", ").append((String) arrayList.get(0));
        } else {
            stringBuilder.append(this.neighborhood).append(", ").append((String) arrayList.get(arrayList.size() - 1));
        }
        return stringBuilder.toString();
    }
}

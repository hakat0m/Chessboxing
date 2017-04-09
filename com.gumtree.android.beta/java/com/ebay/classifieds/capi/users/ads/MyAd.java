package com.ebay.classifieds.capi.users.ads;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.ads.ActiveFeature;
import com.ebay.classifieds.capi.ads.AdAddress;
import com.ebay.classifieds.capi.ads.Attribute;
import com.ebay.classifieds.capi.ads.Link;
import com.ebay.classifieds.capi.ads.Picture;
import com.ebay.classifieds.capi.ads.Price;
import com.ebay.classifieds.capi.ads.PriceFrequency;
import com.ebay.classifieds.capi.ads.Stat;
import com.ebay.classifieds.capi.ads.Status;
import com.ebay.classifieds.capi.categories.Category;
import com.ebay.classifieds.capi.locations.Location;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.ListUtils;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "ad", strict = false)
@Namespace(prefix = "ad", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
public class MyAd {
    public static final String DELETED = "deleted";
    public static final String EXPIRED = "expired";
    public static final String REMOVED = "deleted_cs";
    public static final String STATUS_CREATED = "created";
    public static final String STATUS_DELAYED = "delayed";
    public static final String STATUS_LIVE = "active";
    public static final String STATUS_N_A = "n/a";
    public static final String STATUS_PAUSED = "paused";
    public static final String STATUS_PENDING = "pending";
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "account-id", required = false)
    private String accountId;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "ad-address", required = false)
    private AdAddress adAddress;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "ad-status", required = false)
    private Status adStatus;
    @Namespace(prefix = "attr", reference = "http://www.ebayclassifiedsgroup.com/schema/attribute/v1")
    @ElementList(name = "attributes", required = false)
    private ArrayList<Attribute> attributes;
    @Namespace(prefix = "cat", reference = "http://www.ebayclassifiedsgroup.com/schema/category/v1")
    @Element(name = "category", required = false)
    private Category category;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "description", required = false)
    private String description;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "email", required = false)
    private String email;
    @ElementList(empty = false, name = "features-active", required = false)
    private ArrayList<ActiveFeature> featuresActive;
    @org.simpleframework.xml.Attribute(name = "id", required = false)
    private String id;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @ElementList(inline = true, required = false)
    private ArrayList<Link> links;
    @Namespace(prefix = "loc", reference = "http://www.ebayclassifiedsgroup.com/schema/location/v1")
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
    @Element(name = "poster-contact-email", required = false)
    private String posterContactEmail;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "poster-contact-name", required = false)
    private String posterContactName;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "price", required = false)
    private Price price;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "price-frequency", required = false)
    private PriceFrequency priceFrequency;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "start-date-time", required = false)
    private String startDate;
    @Namespace(prefix = "stat", reference = "http://www.ebayclassifiedsgroup.com/schema/attribute/v1")
    @ElementList(name = "stats", required = false)
    private ArrayList<Stat> stats;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "title", required = false)
    private String title;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "user-id", required = false)
    private String userId;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "visible-on-map", required = false)
    private boolean visibleOnMap;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public Status getAdStatus() {
        return this.adStatus;
    }

    public void setAdStatus(Status status) {
        this.adStatus = status;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String str) {
        this.accountId = str;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPosterContactEmail() {
        return this.posterContactEmail;
    }

    public void setPosterContactEmail(String str) {
        this.posterContactEmail = str;
    }

    public String getPosterContactName() {
        return this.posterContactName;
    }

    public void setPosterContactName(String str) {
        this.posterContactName = str;
    }

    public boolean isVisibleOnMap() {
        return this.visibleOnMap;
    }

    public void setVisibleOnMap(boolean z) {
        this.visibleOnMap = z;
    }

    public String getNeighborhood() {
        return this.neighborhood;
    }

    public void setNeighborhood(String str) {
        this.neighborhood = str;
    }

    public ArrayList<Location> getLocations() {
        return this.locations;
    }

    public void setLocations(ArrayList<Location> arrayList) {
        this.locations = arrayList;
    }

    public AdAddress getAdAddress() {
        return this.adAddress;
    }

    public void setAdAddress(AdAddress adAddress) {
        this.adAddress = adAddress;
    }

    public ArrayList<Link> getLinks() {
        return this.links;
    }

    public void setLinks(ArrayList<Link> arrayList) {
        this.links = arrayList;
    }

    public Price getPrice() {
        return this.price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public PriceFrequency getPriceFrequency() {
        return this.priceFrequency;
    }

    public void setPriceFrequency(PriceFrequency priceFrequency) {
        this.priceFrequency = priceFrequency;
    }

    public ArrayList<Picture> getPictures() {
        return this.pictures;
    }

    public void setPictures(ArrayList<Picture> arrayList) {
        this.pictures = arrayList;
    }

    public ArrayList<Attribute> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(ArrayList<Attribute> arrayList) {
        this.attributes = arrayList;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    @NonNull
    public List<ActiveFeature> getFeaturesActive() {
        return ListUtils.emptyIfNull(this.featuresActive);
    }

    public ArrayList<Stat> getStats() {
        return this.stats;
    }

    public void setStats(ArrayList<Stat> arrayList) {
        this.stats = arrayList;
    }

    public String getModificationDate() {
        return this.modificationDate;
    }

    public void setModificationDate(String str) {
        this.modificationDate = str;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String str) {
        this.startDate = str;
    }
}

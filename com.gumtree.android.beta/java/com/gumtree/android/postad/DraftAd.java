package com.gumtree.android.postad;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.ads.Status;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.metadata.model.DraftAdMetadata;
import com.gumtree.android.metadata.model.DraftAdMetadataAttribute;
import com.gumtree.android.postad.DraftFeature.Type;
import com.gumtree.android.postad.promote.PromotionFeature;
import java.beans.ConstructorProperties;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.ListUtils;

public class DraftAd {
    public static final String NEW_AD_ID = "0";
    private String accountId;
    private List<Type> activeFeatures;
    private List<DraftAdAttribute> attributesValues;
    private DraftCategory category;
    private String contactEmail;
    private boolean contactEmailSelected;
    private String contactName;
    private boolean contactPhoneSelected;
    private DraftCoordinates coordinates;
    private String description;
    private String email;
    private String endDate;
    private String id;
    private List<DraftLink> links;
    private DraftLocation location;
    private DraftAdMetadata metadata;
    private String modificationDate;
    private String neighborhood;
    private String phone;
    private List<PostAdImage> postAdImages;
    private String price;
    private List<PromotionFeature> selectedFeatures;
    private List<String> selectedFeaturesReasons;
    private String selectedPriceFrequency;
    private String startDate;
    private Status status;
    private String title;
    private double totalAdPrice;
    private boolean visibleOnMap;
    private boolean vrmValidated;
    private String zipCode;

    public DraftAdBuilder toBuilder() {
        return new DraftAdBuilder().id(this.id).postAdImages(this.postAdImages).title(this.title).category(this.category).activeFeatures(this.activeFeatures).selectedFeatures(this.selectedFeatures).price(this.price).selectedPriceFrequency(this.selectedPriceFrequency).description(this.description).status(this.status).coordinates(this.coordinates).email(this.email).accountId(this.accountId).phone(this.phone).modificationDate(this.modificationDate).startDate(this.startDate).endDate(this.endDate).location(this.location).links(this.links).visibleOnMap(this.visibleOnMap).contactEmail(this.contactEmail).contactName(this.contactName).contactEmailSelected(this.contactEmailSelected).contactPhoneSelected(this.contactPhoneSelected).neighborhood(this.neighborhood).zipCode(this.zipCode).metadata(this.metadata).attributesValues(this.attributesValues).totalAdPrice(this.totalAdPrice).selectedFeaturesReasons(this.selectedFeaturesReasons).vrmValidated(this.vrmValidated);
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof DraftAd;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DraftAd)) {
            return false;
        }
        DraftAd draftAd = (DraftAd) obj;
        if (!draftAd.canEqual(this)) {
            return false;
        }
        String id = getId();
        String id2 = draftAd.getId();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        List postAdImages = getPostAdImages();
        List postAdImages2 = draftAd.getPostAdImages();
        if (postAdImages != null ? !postAdImages.equals(postAdImages2) : postAdImages2 != null) {
            return false;
        }
        id = getTitle();
        id2 = draftAd.getTitle();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        DraftCategory category = getCategory();
        DraftCategory category2 = draftAd.getCategory();
        if (category != null ? !category.equals(category2) : category2 != null) {
            return false;
        }
        postAdImages = getActiveFeatures();
        postAdImages2 = draftAd.getActiveFeatures();
        if (postAdImages != null ? !postAdImages.equals(postAdImages2) : postAdImages2 != null) {
            return false;
        }
        postAdImages = getSelectedFeatures();
        postAdImages2 = draftAd.getSelectedFeatures();
        if (postAdImages != null ? !postAdImages.equals(postAdImages2) : postAdImages2 != null) {
            return false;
        }
        id = getPrice();
        id2 = draftAd.getPrice();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getSelectedPriceFrequency();
        id2 = draftAd.getSelectedPriceFrequency();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getDescription();
        id2 = draftAd.getDescription();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        Status status = getStatus();
        Status status2 = draftAd.getStatus();
        if (status != null ? !status.equals(status2) : status2 != null) {
            return false;
        }
        DraftCoordinates coordinates = getCoordinates();
        DraftCoordinates coordinates2 = draftAd.getCoordinates();
        if (coordinates != null ? !coordinates.equals(coordinates2) : coordinates2 != null) {
            return false;
        }
        id = getEmail();
        id2 = draftAd.getEmail();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getAccountId();
        id2 = draftAd.getAccountId();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getPhone();
        id2 = draftAd.getPhone();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getModificationDate();
        id2 = draftAd.getModificationDate();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getStartDate();
        id2 = draftAd.getStartDate();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getEndDate();
        id2 = draftAd.getEndDate();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        DraftLocation location = getLocation();
        DraftLocation location2 = draftAd.getLocation();
        if (location != null ? !location.equals(location2) : location2 != null) {
            return false;
        }
        postAdImages = getLinks();
        postAdImages2 = draftAd.getLinks();
        if (postAdImages != null ? !postAdImages.equals(postAdImages2) : postAdImages2 != null) {
            return false;
        }
        if (isVisibleOnMap() != draftAd.isVisibleOnMap()) {
            return false;
        }
        id = getContactEmail();
        id2 = draftAd.getContactEmail();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getContactName();
        id2 = draftAd.getContactName();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        if (isContactEmailSelected() != draftAd.isContactEmailSelected()) {
            return false;
        }
        if (isContactPhoneSelected() != draftAd.isContactPhoneSelected()) {
            return false;
        }
        id = getNeighborhood();
        id2 = draftAd.getNeighborhood();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getZipCode();
        id2 = draftAd.getZipCode();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        DraftAdMetadata metadata = getMetadata();
        DraftAdMetadata metadata2 = draftAd.getMetadata();
        if (metadata != null ? !metadata.equals(metadata2) : metadata2 != null) {
            return false;
        }
        postAdImages = getAttributesValues();
        postAdImages2 = draftAd.getAttributesValues();
        if (postAdImages != null ? !postAdImages.equals(postAdImages2) : postAdImages2 != null) {
            return false;
        }
        if (Double.compare(getTotalAdPrice(), draftAd.getTotalAdPrice()) != 0) {
            return false;
        }
        postAdImages = getSelectedFeaturesReasons();
        postAdImages2 = draftAd.getSelectedFeaturesReasons();
        if (postAdImages != null ? !postAdImages.equals(postAdImages2) : postAdImages2 != null) {
            return false;
        }
        return isVrmValidated() == draftAd.isVrmValidated();
    }

    public int hashCode() {
        int i = 79;
        int i2 = 43;
        String id = getId();
        int hashCode = (id == null ? 43 : id.hashCode()) + 59;
        List postAdImages = getPostAdImages();
        hashCode = (postAdImages == null ? 43 : postAdImages.hashCode()) + (hashCode * 59);
        String title = getTitle();
        hashCode = (title == null ? 43 : title.hashCode()) + (hashCode * 59);
        DraftCategory category = getCategory();
        hashCode = (category == null ? 43 : category.hashCode()) + (hashCode * 59);
        postAdImages = getActiveFeatures();
        hashCode = (postAdImages == null ? 43 : postAdImages.hashCode()) + (hashCode * 59);
        postAdImages = getSelectedFeatures();
        hashCode = (postAdImages == null ? 43 : postAdImages.hashCode()) + (hashCode * 59);
        title = getPrice();
        hashCode = (title == null ? 43 : title.hashCode()) + (hashCode * 59);
        title = getSelectedPriceFrequency();
        hashCode = (title == null ? 43 : title.hashCode()) + (hashCode * 59);
        title = getDescription();
        hashCode = (title == null ? 43 : title.hashCode()) + (hashCode * 59);
        Status status = getStatus();
        hashCode = (status == null ? 43 : status.hashCode()) + (hashCode * 59);
        DraftCoordinates coordinates = getCoordinates();
        hashCode = (coordinates == null ? 43 : coordinates.hashCode()) + (hashCode * 59);
        title = getEmail();
        hashCode = (title == null ? 43 : title.hashCode()) + (hashCode * 59);
        title = getAccountId();
        hashCode = (title == null ? 43 : title.hashCode()) + (hashCode * 59);
        title = getPhone();
        hashCode = (title == null ? 43 : title.hashCode()) + (hashCode * 59);
        title = getModificationDate();
        hashCode = (title == null ? 43 : title.hashCode()) + (hashCode * 59);
        title = getStartDate();
        hashCode = (title == null ? 43 : title.hashCode()) + (hashCode * 59);
        title = getEndDate();
        hashCode = (title == null ? 43 : title.hashCode()) + (hashCode * 59);
        DraftLocation location = getLocation();
        hashCode = (location == null ? 43 : location.hashCode()) + (hashCode * 59);
        postAdImages = getLinks();
        hashCode = (isVisibleOnMap() ? 79 : 97) + (((postAdImages == null ? 43 : postAdImages.hashCode()) + (hashCode * 59)) * 59);
        title = getContactEmail();
        hashCode = (title == null ? 43 : title.hashCode()) + (hashCode * 59);
        title = getContactName();
        hashCode = (isContactPhoneSelected() ? 79 : 97) + (((isContactEmailSelected() ? 79 : 97) + (((title == null ? 43 : title.hashCode()) + (hashCode * 59)) * 59)) * 59);
        title = getNeighborhood();
        hashCode = (title == null ? 43 : title.hashCode()) + (hashCode * 59);
        title = getZipCode();
        hashCode = (title == null ? 43 : title.hashCode()) + (hashCode * 59);
        DraftAdMetadata metadata = getMetadata();
        hashCode = (metadata == null ? 43 : metadata.hashCode()) + (hashCode * 59);
        postAdImages = getAttributesValues();
        hashCode = (postAdImages == null ? 43 : postAdImages.hashCode()) + (hashCode * 59);
        long doubleToLongBits = Double.doubleToLongBits(getTotalAdPrice());
        hashCode = (hashCode * 59) + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
        postAdImages = getSelectedFeaturesReasons();
        hashCode *= 59;
        if (postAdImages != null) {
            i2 = postAdImages.hashCode();
        }
        hashCode = (hashCode + i2) * 59;
        if (!isVrmValidated()) {
            i = 97;
        }
        return hashCode + i;
    }

    public void setAccountId(String str) {
        this.accountId = str;
    }

    public void setActiveFeatures(List<Type> list) {
        this.activeFeatures = list;
    }

    public void setAttributesValues(List<DraftAdAttribute> list) {
        this.attributesValues = list;
    }

    public void setCategory(DraftCategory draftCategory) {
        this.category = draftCategory;
    }

    public void setContactEmail(String str) {
        this.contactEmail = str;
    }

    public void setContactEmailSelected(boolean z) {
        this.contactEmailSelected = z;
    }

    public void setContactName(String str) {
        this.contactName = str;
    }

    public void setContactPhoneSelected(boolean z) {
        this.contactPhoneSelected = z;
    }

    public void setCoordinates(DraftCoordinates draftCoordinates) {
        this.coordinates = draftCoordinates;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public void setEndDate(String str) {
        this.endDate = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setLinks(List<DraftLink> list) {
        this.links = list;
    }

    public void setLocation(DraftLocation draftLocation) {
        this.location = draftLocation;
    }

    public void setMetadata(DraftAdMetadata draftAdMetadata) {
        this.metadata = draftAdMetadata;
    }

    public void setModificationDate(String str) {
        this.modificationDate = str;
    }

    public void setNeighborhood(String str) {
        this.neighborhood = str;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public void setPostAdImages(List<PostAdImage> list) {
        this.postAdImages = list;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public void setSelectedFeatures(List<PromotionFeature> list) {
        this.selectedFeatures = list;
    }

    public void setSelectedFeaturesReasons(List<String> list) {
        this.selectedFeaturesReasons = list;
    }

    public void setSelectedPriceFrequency(String str) {
        this.selectedPriceFrequency = str;
    }

    public void setStartDate(String str) {
        this.startDate = str;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setTotalAdPrice(double d) {
        this.totalAdPrice = d;
    }

    public void setVisibleOnMap(boolean z) {
        this.visibleOnMap = z;
    }

    public void setVrmValidated(boolean z) {
        this.vrmValidated = z;
    }

    public void setZipCode(String str) {
        this.zipCode = str;
    }

    public String toString() {
        return "DraftAd(id=" + getId() + ", postAdImages=" + getPostAdImages() + ", title=" + getTitle() + ", category=" + getCategory() + ", activeFeatures=" + getActiveFeatures() + ", selectedFeatures=" + getSelectedFeatures() + ", price=" + getPrice() + ", selectedPriceFrequency=" + getSelectedPriceFrequency() + ", description=" + getDescription() + ", status=" + getStatus() + ", coordinates=" + getCoordinates() + ", email=" + getEmail() + ", accountId=" + getAccountId() + ", phone=" + getPhone() + ", modificationDate=" + getModificationDate() + ", startDate=" + getStartDate() + ", endDate=" + getEndDate() + ", location=" + getLocation() + ", links=" + getLinks() + ", visibleOnMap=" + isVisibleOnMap() + ", contactEmail=" + getContactEmail() + ", contactName=" + getContactName() + ", contactEmailSelected=" + isContactEmailSelected() + ", contactPhoneSelected=" + isContactPhoneSelected() + ", neighborhood=" + getNeighborhood() + ", zipCode=" + getZipCode() + ", metadata=" + getMetadata() + ", attributesValues=" + getAttributesValues() + ", totalAdPrice=" + getTotalAdPrice() + ", selectedFeaturesReasons=" + getSelectedFeaturesReasons() + ", vrmValidated=" + isVrmValidated() + ")";
    }

    public static DraftAdBuilder builder() {
        return new DraftAdBuilder();
    }

    @ConstructorProperties({"id", "postAdImages", "title", "category", "activeFeatures", "selectedFeatures", "price", "selectedPriceFrequency", "description", "status", "coordinates", "email", "accountId", "phone", "modificationDate", "startDate", "endDate", "location", "links", "visibleOnMap", "contactEmail", "contactName", "contactEmailSelected", "contactPhoneSelected", "neighborhood", "zipCode", "metadata", "attributesValues", "totalAdPrice", "selectedFeaturesReasons", "vrmValidated"})
    public DraftAd(String str, List<PostAdImage> list, String str2, DraftCategory draftCategory, List<Type> list2, List<PromotionFeature> list3, String str3, String str4, String str5, Status status, DraftCoordinates draftCoordinates, String str6, String str7, String str8, String str9, String str10, String str11, DraftLocation draftLocation, List<DraftLink> list4, boolean z, String str12, String str13, boolean z2, boolean z3, String str14, String str15, DraftAdMetadata draftAdMetadata, List<DraftAdAttribute> list5, double d, List<String> list6, boolean z4) {
        this.id = str;
        this.postAdImages = list;
        this.title = str2;
        this.category = draftCategory;
        this.activeFeatures = list2;
        this.selectedFeatures = list3;
        this.price = str3;
        this.selectedPriceFrequency = str4;
        this.description = str5;
        this.status = status;
        this.coordinates = draftCoordinates;
        this.email = str6;
        this.accountId = str7;
        this.phone = str8;
        this.modificationDate = str9;
        this.startDate = str10;
        this.endDate = str11;
        this.location = draftLocation;
        this.links = list4;
        this.visibleOnMap = z;
        this.contactEmail = str12;
        this.contactName = str13;
        this.contactEmailSelected = z2;
        this.contactPhoneSelected = z3;
        this.neighborhood = str14;
        this.zipCode = str15;
        this.metadata = draftAdMetadata;
        this.attributesValues = list5;
        this.totalAdPrice = d;
        this.selectedFeaturesReasons = list6;
        this.vrmValidated = z4;
    }

    public String getId() {
        return this.id;
    }

    public List<PostAdImage> getPostAdImages() {
        return this.postAdImages;
    }

    public String getTitle() {
        return this.title;
    }

    public DraftCategory getCategory() {
        return this.category;
    }

    public List<Type> getActiveFeatures() {
        return this.activeFeatures;
    }

    public String getPrice() {
        return this.price;
    }

    public String getSelectedPriceFrequency() {
        return this.selectedPriceFrequency;
    }

    public String getDescription() {
        return this.description;
    }

    public Status getStatus() {
        return this.status;
    }

    public DraftCoordinates getCoordinates() {
        return this.coordinates;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAccountId() {
        return this.accountId;
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

    public String getEndDate() {
        return this.endDate;
    }

    public DraftLocation getLocation() {
        return this.location;
    }

    public List<DraftLink> getLinks() {
        return this.links;
    }

    public boolean isVisibleOnMap() {
        return this.visibleOnMap;
    }

    public String getContactEmail() {
        return this.contactEmail;
    }

    public String getContactName() {
        return this.contactName;
    }

    public boolean isContactEmailSelected() {
        return this.contactEmailSelected;
    }

    public boolean isContactPhoneSelected() {
        return this.contactPhoneSelected;
    }

    public String getNeighborhood() {
        return this.neighborhood;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public DraftAdMetadata getMetadata() {
        return this.metadata;
    }

    public List<DraftAdAttribute> getAttributesValues() {
        return this.attributesValues;
    }

    public double getTotalAdPrice() {
        return this.totalAdPrice;
    }

    public List<String> getSelectedFeaturesReasons() {
        return this.selectedFeaturesReasons;
    }

    public boolean isVrmValidated() {
        return this.vrmValidated;
    }

    public DraftAd(String str) {
        this.id = str;
    }

    @NonNull
    public List<PromotionFeature> getSelectedFeatures() {
        return Collections.unmodifiableList(ListUtils.emptyIfNull(this.selectedFeatures));
    }

    public boolean isInSpotlightSelected() {
        return containsSelectedFeature(Type.IN_SPOTLIGHT);
    }

    public boolean isUrgentSelected() {
        return containsSelectedFeature(Type.URGENT);
    }

    public boolean isFeaturedSelected() {
        return containsSelectedFeature(Type.FEATURED);
    }

    public boolean isBumpedUpSelected() {
        return containsSelectedFeature(Type.BUMPED_UP);
    }

    private boolean containsSelectedFeature(@NonNull Type type) {
        return ((PromotionFeature) IterableUtils.find(getSelectedFeatures(), DraftAd$$Lambda$1.lambdaFactory$(type))) != null;
    }

    static /* synthetic */ boolean lambda$containsSelectedFeature$0(@NonNull Type type, PromotionFeature promotionFeature) {
        return promotionFeature.getFeature() != null && type.equals(promotionFeature.getFeature().getType());
    }

    public List<DraftAdMetadataAttribute> getMetadataAttributes() {
        if (this.metadata != null) {
            return this.metadata.getAttributes();
        }
        return null;
    }

    public boolean isNewAd() {
        return NEW_AD_ID.equals(getId());
    }

    public boolean hasInsertionFee() {
        return IterableUtils.find(getSelectedFeatures(), DraftAd$$Lambda$2.lambdaFactory$()) != null;
    }
}

package com.gumtree.android.postad.services;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.ads.AdAddress;
import com.ebay.classifieds.capi.ads.Attribute;
import com.ebay.classifieds.capi.ads.AttributeValue;
import com.ebay.classifieds.capi.ads.CurrencyIsoCode;
import com.ebay.classifieds.capi.ads.Link;
import com.ebay.classifieds.capi.ads.Picture;
import com.ebay.classifieds.capi.ads.PictureLink;
import com.ebay.classifieds.capi.ads.Price;
import com.ebay.classifieds.capi.ads.PriceFrequency;
import com.ebay.classifieds.capi.categories.Category;
import com.ebay.classifieds.capi.locations.Location;
import com.ebay.classifieds.capi.users.ads.MyAd;
import com.ebay.classifieds.capi.utils.PictureLinkUtils;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.category.suggest.service.converter.CategoryConverter;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.DraftAd.DraftAdBuilder;
import com.gumtree.android.postad.DraftAdAttribute;
import com.gumtree.android.postad.DraftCoordinates;
import com.gumtree.android.postad.DraftLink;
import com.gumtree.android.postad.DraftLocation;
import com.gumtree.android.postad.PostAdImage;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;

public class DraftAdMyAdModelConverter implements AdModelConverter {
    private final BaseAccountManager accountManager;
    private final ActiveFeatureConverter activeFeatureConverter;
    private final CategoryConverter categoryConverter;

    @Inject
    public DraftAdMyAdModelConverter(@NonNull ActiveFeatureConverter activeFeatureConverter, @NonNull CategoryConverter categoryConverter, @NonNull BaseAccountManager baseAccountManager) {
        this.categoryConverter = categoryConverter;
        this.activeFeatureConverter = (ActiveFeatureConverter) Validate.notNull(activeFeatureConverter);
        this.accountManager = (BaseAccountManager) Validate.notNull(baseAccountManager);
    }

    public DraftAd myAdToDraftAd(@NonNull MyAd myAd) {
        String str;
        Iterator it;
        List arrayList;
        DraftAdBuilder builder = DraftAd.builder();
        DraftAdBuilder id = builder.id(myAd.getId());
        if (myAd.getPrice() == null) {
            str = null;
        } else {
            str = myAd.getPrice().getAmount();
        }
        id.price(str).title(myAd.getTitle()).description(myAd.getDescription());
        AdAddress adAddress = myAd.getAdAddress();
        if (adAddress != null) {
            builder.coordinates(new DraftCoordinates(adAddress.getLongitude(), adAddress.getLatitude())).zipCode(adAddress.getZipCode());
        }
        builder.status(myAd.getAdStatus());
        builder.email(myAd.getEmail()).accountId(this.accountManager.getUserId());
        builder.phone(myAd.getPhone());
        builder.activeFeatures(this.activeFeatureConverter.apiToModel(ListUtils.emptyIfNull(myAd.getFeaturesActive())));
        Category category = myAd.getCategory();
        if (category != null) {
            builder.category(this.categoryConverter.apiToModel(category));
        }
        List emptyIfNull = ListUtils.emptyIfNull(myAd.getLocations());
        if (emptyIfNull.size() > 0) {
            Location location = (Location) emptyIfNull.get(emptyIfNull.size() - 1);
            builder.location(new DraftLocation(location.getId().longValue(), location.getIdName(), location.getLocalizedName(), location.isLeaf(), location.getParentId(), location.getLocationBreadcrumb()));
        }
        builder.neighborhood(myAd.getNeighborhood());
        PriceFrequency priceFrequency = myAd.getPriceFrequency();
        if (priceFrequency != null) {
            builder.selectedPriceFrequency(priceFrequency.getValue());
        }
        List<Attribute> attributes = myAd.getAttributes();
        if (attributes != null) {
            List arrayList2 = new ArrayList();
            for (Attribute attribute : attributes) {
                String localizedLabel;
                String name = attribute.getName();
                String localizedLabel2 = attribute.getLocalizedLabel();
                AttributeValue attributeValue = attribute.getAttributeValue();
                if (attributeValue != null) {
                    localizedLabel = attributeValue.getLocalizedLabel();
                    str = attributeValue.getValue();
                } else {
                    str = null;
                    localizedLabel = null;
                }
                DraftAdAttribute draftAdAttribute = new DraftAdAttribute();
                draftAdAttribute.setName(name);
                draftAdAttribute.setLocalisedLabel(localizedLabel2);
                draftAdAttribute.setValueLocalisedLabel(localizedLabel);
                draftAdAttribute.setValue(str);
                arrayList2.add(draftAdAttribute);
            }
            builder.attributesValues(arrayList2);
        }
        List<Link> emptyIfNull2 = ListUtils.emptyIfNull(myAd.getLinks());
        if (emptyIfNull2.size() > 0) {
            arrayList = new ArrayList();
            for (Link link : emptyIfNull2) {
                arrayList.add(new DraftLink(link.getRel(), link.getHref()));
            }
            builder.links(arrayList);
        }
        List<Picture> emptyIfNull3 = ListUtils.emptyIfNull(myAd.getPictures());
        if (emptyIfNull3 != null && emptyIfNull3.size() > 0) {
            arrayList = new ArrayList();
            for (Picture links : emptyIfNull3) {
                it = links.getLinks().iterator();
                while (it.hasNext()) {
                    PictureLink pictureLink = (PictureLink) it.next();
                    if ("extrabig".equals(pictureLink.getRel())) {
                        arrayList.add(new PostAdImage(PictureLinkUtils.getPictureLink20(pictureLink.getHref()), null, false, null));
                        break;
                    }
                }
            }
            builder.postAdImages(arrayList);
        }
        builder.visibleOnMap(myAd.isVisibleOnMap()).contactEmail(myAd.getPosterContactEmail()).contactName(myAd.getPosterContactName()).contactEmailSelected(StringUtils.isNotBlank(myAd.getPosterContactEmail())).contactPhoneSelected(StringUtils.isNotBlank(myAd.getPhone()));
        return builder.build();
    }

    public MyAd draftAdToMyAd(@NonNull DraftAd draftAd) {
        MyAd myAd = new MyAd();
        myAd.setId(draftAd.getId());
        if (draftAd.getPrice() != null) {
            Price price = new Price();
            price.setCurrencyIssueCode(new CurrencyIsoCode(PaymentConverter.DEFAULT_CURRENCY_ISO_CODE));
            price.setAmount(draftAd.getPrice());
            myAd.setPrice(price);
        }
        myAd.setTitle(draftAd.getTitle());
        myAd.setDescription(draftAd.getDescription());
        AdAddress adAddress = new AdAddress();
        Object zipCode = draftAd.getZipCode();
        if (StringUtils.isNotBlank(zipCode)) {
            adAddress.setZipCode(zipCode);
        }
        DraftCoordinates coordinates = draftAd.getCoordinates();
        if (coordinates != null) {
            adAddress.setLatitude(coordinates.getLatitude());
            adAddress.setLongitude(coordinates.getLongitude());
        }
        myAd.setAdAddress(adAddress);
        myAd.setAdStatus(draftAd.getStatus());
        String contactEmail = draftAd.getContactEmail();
        if (!(draftAd.isContactEmailSelected() && StringUtils.isNotBlank(contactEmail))) {
            contactEmail = null;
        }
        myAd.setPosterContactEmail(contactEmail);
        myAd.setEmail(draftAd.getEmail());
        myAd.setAccountId(draftAd.getAccountId());
        Object phone = draftAd.getPhone();
        if (draftAd.isContactPhoneSelected() && StringUtils.isNotBlank(phone)) {
            myAd.setPhone(phone);
        } else {
            myAd.setPhone(null);
        }
        DraftCategory category = draftAd.getCategory();
        if (category != null) {
            Category category2 = new Category();
            category2.setId(NumberUtils.toLong(StringUtils.trimToNull(category.getId()), 0));
            category2.setLocalizedName(category.getLocalisedName());
            myAd.setCategory(category2);
        }
        DraftLocation location = draftAd.getLocation();
        if (location != null) {
            ArrayList arrayList = new ArrayList();
            Location location2 = new Location();
            location2.setId(Long.valueOf(location.getId()));
            location2.setLocalizedName(location.getLocalisedName());
            location2.setIdName(location.getName());
            location2.setLeaf(location.isLeaf());
            location2.setParentId(location.getParentId());
            location2.setLocationBreadcrumb(location.getBreadCrumb());
            arrayList.add(location2);
            myAd.setLocations(arrayList);
        }
        myAd.setNeighborhood(draftAd.getNeighborhood());
        phone = draftAd.getSelectedPriceFrequency();
        if (StringUtils.isNotEmpty(phone)) {
            PriceFrequency priceFrequency = new PriceFrequency();
            priceFrequency.setValue(phone);
            myAd.setPriceFrequency(priceFrequency);
        }
        List<DraftAdAttribute> attributesValues = draftAd.getAttributesValues();
        if (attributesValues != null) {
            arrayList = new ArrayList();
            for (DraftAdAttribute draftAdAttribute : attributesValues) {
                String value = draftAdAttribute.getValue();
                if (value != null) {
                    AttributeValue attributeValue = new AttributeValue();
                    attributeValue.setValue(value);
                    Attribute attribute = new Attribute();
                    attribute.setName(draftAdAttribute.getName());
                    attribute.setAttributeValue(attributeValue);
                    arrayList.add(attribute);
                }
            }
            myAd.setAttributes(arrayList);
        }
        if (draftAd.getLinks() != null) {
            arrayList = new ArrayList();
            for (DraftLink draftLink : draftAd.getLinks()) {
                Link link = new Link();
                link.setRel(draftLink.getRel());
                link.setHref(draftLink.getHref());
                arrayList.add(link);
            }
            myAd.setLinks(arrayList);
        }
        List<PostAdImage> postAdImages = draftAd.getPostAdImages();
        if (postAdImages != null) {
            arrayList = new ArrayList();
            for (PostAdImage postAdImage : postAdImages) {
                Picture picture = new Picture();
                PictureLink pictureLink = new PictureLink();
                ArrayList arrayList2 = new ArrayList();
                pictureLink.setHref(PictureLinkUtils.getPictureLink80(postAdImage.getUrl()));
                arrayList2.add(pictureLink);
                picture.setLinks(arrayList2);
                arrayList.add(picture);
            }
            myAd.setPictures(arrayList);
        }
        myAd.setVisibleOnMap(draftAd.isVisibleOnMap());
        myAd.setPosterContactName(draftAd.getContactName());
        return myAd;
    }
}

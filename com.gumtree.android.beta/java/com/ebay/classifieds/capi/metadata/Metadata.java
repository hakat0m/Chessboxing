package com.ebay.classifieds.capi.metadata;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "ad", strict = false)
@Namespace(prefix = "ad", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
public class Metadata {
    @Element(name = "attributes", required = false)
    private MetadataAttributes metadataAttributes;
    @Namespace(prefix = "ad", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "price", required = false)
    private PriceMetadataAttribute price;
    @Namespace(prefix = "ad", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "price-frequency", required = false)
    private PriceMetadataAttribute priceFrequency;

    public PriceMetadataAttribute getPrice() {
        return this.price;
    }

    public void setPrice(PriceMetadataAttribute priceMetadataAttribute) {
        this.price = priceMetadataAttribute;
    }

    public PriceMetadataAttribute getPriceFrequency() {
        return this.priceFrequency;
    }

    public void setPriceFrequency(PriceMetadataAttribute priceMetadataAttribute) {
        this.priceFrequency = priceMetadataAttribute;
    }

    public MetadataAttributes getMetadataAttributes() {
        return this.metadataAttributes;
    }

    public void setMetadataAttributes(MetadataAttributes metadataAttributes) {
        this.metadataAttributes = metadataAttributes;
    }
}

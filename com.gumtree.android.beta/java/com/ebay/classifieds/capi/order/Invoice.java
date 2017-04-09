package com.ebay.classifieds.capi.order;

import com.ebay.classifieds.capi.features.Price;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "invoice", strict = false)
public final class Invoice {
    @Attribute(name = "id", required = false)
    @Namespace(prefix = "order", reference = "http://www.ebayclassifiedsgroup.com/schema/order/v1")
    private final String id;
    @Attribute(name = "status", required = false)
    @Namespace(prefix = "order", reference = "http://www.ebayclassifiedsgroup.com/schema/order/v1")
    private final String status;
    @Namespace(prefix = "order", reference = "http://www.ebayclassifiedsgroup.com/schema/order/v1")
    @Element(name = "tax", required = false)
    private final Price tax;
    @Namespace(prefix = "order", reference = "http://www.ebayclassifiedsgroup.com/schema/order/v1")
    @Element(name = "total-price", required = false)
    private final Price totalPrice;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Invoice)) {
            return false;
        }
        Invoice invoice = (Invoice) obj;
        String id = getId();
        String id2 = invoice.getId();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getStatus();
        id2 = invoice.getStatus();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        Price totalPrice = getTotalPrice();
        Price totalPrice2 = invoice.getTotalPrice();
        if (totalPrice != null ? !totalPrice.equals(totalPrice2) : totalPrice2 != null) {
            return false;
        }
        totalPrice = getTax();
        totalPrice2 = invoice.getTax();
        if (totalPrice == null) {
            if (totalPrice2 == null) {
                return true;
            }
        } else if (totalPrice.equals(totalPrice2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String id = getId();
        int hashCode = (id == null ? 43 : id.hashCode()) + 59;
        String status = getStatus();
        hashCode = (status == null ? 43 : status.hashCode()) + (hashCode * 59);
        Price totalPrice = getTotalPrice();
        hashCode = (totalPrice == null ? 43 : totalPrice.hashCode()) + (hashCode * 59);
        totalPrice = getTax();
        hashCode *= 59;
        if (totalPrice != null) {
            i = totalPrice.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "Invoice(id=" + getId() + ", status=" + getStatus() + ", totalPrice=" + getTotalPrice() + ", tax=" + getTax() + ")";
    }

    public static InvoiceBuilder builder() {
        return new InvoiceBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getStatus() {
        return this.status;
    }

    public Price getTotalPrice() {
        return this.totalPrice;
    }

    public Price getTax() {
        return this.tax;
    }

    public Invoice(@Attribute(name = "id") String str, @Attribute(name = "status") String str2, @Element(name = "total-price") Price price, @Element(name = "tax") Price price2) {
        this.id = str;
        this.status = str2;
        this.totalPrice = price;
        this.tax = price2;
    }
}

package com.ebay.classifieds.capi.order;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "order", strict = false)
@Namespace(prefix = "order", reference = "http://www.ebayclassifiedsgroup.com/schema/order/v1")
public final class OrderResponse {
    @Element(name = "invoice", required = false)
    private final Invoice invoice;
    @Attribute(name = "id", required = false)
    private final String orderId;
    @Element(name = "order-items", required = false)
    private final OrderItems orderItems;
    @Element(name = "payment", required = false)
    private final OrderPayment payment;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OrderResponse)) {
            return false;
        }
        OrderResponse orderResponse = (OrderResponse) obj;
        String orderId = getOrderId();
        String orderId2 = orderResponse.getOrderId();
        if (orderId != null ? !orderId.equals(orderId2) : orderId2 != null) {
            return false;
        }
        OrderItems orderItems = getOrderItems();
        OrderItems orderItems2 = orderResponse.getOrderItems();
        if (orderItems != null ? !orderItems.equals(orderItems2) : orderItems2 != null) {
            return false;
        }
        OrderPayment payment = getPayment();
        OrderPayment payment2 = orderResponse.getPayment();
        if (payment != null ? !payment.equals(payment2) : payment2 != null) {
            return false;
        }
        Invoice invoice = getInvoice();
        Invoice invoice2 = orderResponse.getInvoice();
        if (invoice == null) {
            if (invoice2 == null) {
                return true;
            }
        } else if (invoice.equals(invoice2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String orderId = getOrderId();
        int hashCode = (orderId == null ? 43 : orderId.hashCode()) + 59;
        OrderItems orderItems = getOrderItems();
        hashCode = (orderItems == null ? 43 : orderItems.hashCode()) + (hashCode * 59);
        OrderPayment payment = getPayment();
        hashCode = (payment == null ? 43 : payment.hashCode()) + (hashCode * 59);
        Invoice invoice = getInvoice();
        hashCode *= 59;
        if (invoice != null) {
            i = invoice.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "OrderResponse(orderId=" + getOrderId() + ", orderItems=" + getOrderItems() + ", payment=" + getPayment() + ", invoice=" + getInvoice() + ")";
    }

    public static OrderResponseBuilder builder() {
        return new OrderResponseBuilder();
    }

    public String getOrderId() {
        return this.orderId;
    }

    public OrderItems getOrderItems() {
        return this.orderItems;
    }

    public OrderPayment getPayment() {
        return this.payment;
    }

    public Invoice getInvoice() {
        return this.invoice;
    }

    public OrderResponse(@Attribute(name = "id") String str, @Element(name = "order-items") OrderItems orderItems, @Element(name = "payment") OrderPayment orderPayment, @Element(name = "invoice") Invoice invoice) {
        this.orderId = str;
        this.orderItems = orderItems;
        this.payment = orderPayment;
        this.invoice = invoice;
    }
}

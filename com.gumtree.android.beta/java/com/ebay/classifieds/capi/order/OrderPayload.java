package com.ebay.classifieds.capi.order;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "order-request", strict = false)
@Namespace(prefix = "order", reference = "http://www.ebayclassifiedsgroup.com/schema/order/v1")
public final class OrderPayload {
    @Element(name = "order-items", required = false)
    private final OrderItems orderItems;
    @Namespace(prefix = "order", reference = "http://www.ebayclassifiedsgroup.com/schema/order/v1")
    @Element(name = "payment-method", required = false)
    private final PaymentMethod paymentMethod;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OrderPayload)) {
            return false;
        }
        OrderPayload orderPayload = (OrderPayload) obj;
        OrderItems orderItems = getOrderItems();
        OrderItems orderItems2 = orderPayload.getOrderItems();
        if (orderItems != null ? !orderItems.equals(orderItems2) : orderItems2 != null) {
            return false;
        }
        PaymentMethod paymentMethod = getPaymentMethod();
        PaymentMethod paymentMethod2 = orderPayload.getPaymentMethod();
        if (paymentMethod == null) {
            if (paymentMethod2 == null) {
                return true;
            }
        } else if (paymentMethod.equals(paymentMethod2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        OrderItems orderItems = getOrderItems();
        int hashCode = (orderItems == null ? 43 : orderItems.hashCode()) + 59;
        PaymentMethod paymentMethod = getPaymentMethod();
        hashCode *= 59;
        if (paymentMethod != null) {
            i = paymentMethod.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "OrderPayload(orderItems=" + getOrderItems() + ", paymentMethod=" + getPaymentMethod() + ")";
    }

    public static OrderPayloadBuilder builder() {
        return new OrderPayloadBuilder();
    }

    public OrderItems getOrderItems() {
        return this.orderItems;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public OrderPayload(@Element(name = "order-items") OrderItems orderItems, @Element(name = "payment-method") PaymentMethod paymentMethod) {
        this.orderItems = orderItems;
        this.paymentMethod = paymentMethod;
    }
}

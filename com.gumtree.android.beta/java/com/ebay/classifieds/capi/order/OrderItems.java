package com.ebay.classifieds.capi.order;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "order-items", strict = false)
@Namespace(prefix = "order", reference = "http://www.ebayclassifiedsgroup.com/schema/order/v1")
public final class OrderItems {
    @ElementList(inline = true, name = "order-item", required = false)
    private final List<OrderItem> orderItems;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OrderItems)) {
            return false;
        }
        OrderItems orderItems = (OrderItems) obj;
        List orderItems2 = getOrderItems();
        List orderItems3 = orderItems.getOrderItems();
        if (orderItems2 == null) {
            if (orderItems3 == null) {
                return true;
            }
        } else if (orderItems2.equals(orderItems3)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        List orderItems = getOrderItems();
        return (orderItems == null ? 43 : orderItems.hashCode()) + 59;
    }

    public String toString() {
        return "OrderItems(orderItems=" + getOrderItems() + ")";
    }

    public static OrderItemsBuilder builder() {
        return new OrderItemsBuilder();
    }

    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public OrderItems(@ElementList(name = "order-item") List<OrderItem> list) {
        this.orderItems = list;
    }
}

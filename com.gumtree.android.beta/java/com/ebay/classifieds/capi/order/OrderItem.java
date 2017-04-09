package com.ebay.classifieds.capi.order;

import com.ebay.classifieds.capi.features.FeatureBooked;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "order-item", strict = false)
@Namespace(prefix = "order", reference = "http://www.ebayclassifiedsgroup.com/schema/order/v1")
public final class OrderItem {
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/order/v1")
    @Element(name = "feature-booked", required = false)
    private final FeatureBooked featureBooked;
    @Attribute(name = "target", required = false)
    private final String target;
    @Attribute(name = "target-id", required = false)
    private final String targetId;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OrderItem)) {
            return false;
        }
        OrderItem orderItem = (OrderItem) obj;
        String targetId = getTargetId();
        String targetId2 = orderItem.getTargetId();
        if (targetId != null ? !targetId.equals(targetId2) : targetId2 != null) {
            return false;
        }
        targetId = getTarget();
        targetId2 = orderItem.getTarget();
        if (targetId != null ? !targetId.equals(targetId2) : targetId2 != null) {
            return false;
        }
        FeatureBooked featureBooked = getFeatureBooked();
        FeatureBooked featureBooked2 = orderItem.getFeatureBooked();
        if (featureBooked == null) {
            if (featureBooked2 == null) {
                return true;
            }
        } else if (featureBooked.equals(featureBooked2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String targetId = getTargetId();
        int hashCode = (targetId == null ? 43 : targetId.hashCode()) + 59;
        String target = getTarget();
        hashCode = (target == null ? 43 : target.hashCode()) + (hashCode * 59);
        FeatureBooked featureBooked = getFeatureBooked();
        hashCode *= 59;
        if (featureBooked != null) {
            i = featureBooked.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "OrderItem(targetId=" + getTargetId() + ", target=" + getTarget() + ", featureBooked=" + getFeatureBooked() + ")";
    }

    public static OrderItemBuilder builder() {
        return new OrderItemBuilder();
    }

    public String getTargetId() {
        return this.targetId;
    }

    public String getTarget() {
        return this.target;
    }

    public FeatureBooked getFeatureBooked() {
        return this.featureBooked;
    }

    public OrderItem(@Attribute(name = "target-id") String str, @Attribute(name = "target") String str2, @Element(name = "feature-booked") FeatureBooked featureBooked) {
        this.targetId = str;
        this.target = str2;
        this.featureBooked = featureBooked;
    }
}

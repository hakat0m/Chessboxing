package com.facebook.stetho.inspector.elements;

import com.facebook.stetho.common.Accumulator;
import com.facebook.stetho.common.Util;
import javax.annotation.Nullable;

public abstract class AbstractChainedDescriptor<E> extends Descriptor implements ChainedDescriptor {
    private Descriptor mSuper;

    public void setSuper(Descriptor descriptor) {
        Util.throwIfNull(descriptor);
        if (descriptor == this.mSuper) {
            return;
        }
        if (this.mSuper != null) {
            throw new IllegalStateException();
        }
        this.mSuper = descriptor;
    }

    final Descriptor getSuper() {
        return this.mSuper;
    }

    public final void hook(Object obj) {
        verifyThreadAccess();
        this.mSuper.hook(obj);
        onHook(obj);
    }

    protected void onHook(E e) {
    }

    public final void unhook(Object obj) {
        verifyThreadAccess();
        onUnhook(obj);
        this.mSuper.unhook(obj);
    }

    protected void onUnhook(E e) {
    }

    public final NodeType getNodeType(Object obj) {
        return onGetNodeType(obj);
    }

    protected NodeType onGetNodeType(E e) {
        return this.mSuper.getNodeType(e);
    }

    public final String getNodeName(Object obj) {
        return onGetNodeName(obj);
    }

    protected String onGetNodeName(E e) {
        return this.mSuper.getNodeName(e);
    }

    public final String getLocalName(Object obj) {
        return onGetLocalName(obj);
    }

    protected String onGetLocalName(E e) {
        return this.mSuper.getLocalName(e);
    }

    public final String getNodeValue(Object obj) {
        return onGetNodeValue(obj);
    }

    @Nullable
    public String onGetNodeValue(E e) {
        return this.mSuper.getNodeValue(e);
    }

    public final void getChildren(Object obj, Accumulator<Object> accumulator) {
        this.mSuper.getChildren(obj, accumulator);
        onGetChildren(obj, accumulator);
    }

    protected void onGetChildren(E e, Accumulator<Object> accumulator) {
    }

    public final void getAttributes(Object obj, AttributeAccumulator attributeAccumulator) {
        this.mSuper.getAttributes(obj, attributeAccumulator);
        onGetAttributes(obj, attributeAccumulator);
    }

    protected void onGetAttributes(E e, AttributeAccumulator attributeAccumulator) {
    }

    public final void setAttributesAsText(Object obj, String str) {
        onSetAttributesAsText(obj, str);
    }

    protected void onSetAttributesAsText(E e, String str) {
        this.mSuper.setAttributesAsText(e, str);
    }

    public final void getStyles(Object obj, StyleAccumulator styleAccumulator) {
        this.mSuper.getStyles(obj, styleAccumulator);
        onGetStyles(obj, styleAccumulator);
    }

    protected void onGetStyles(E e, StyleAccumulator styleAccumulator) {
    }

    public final void getAccessibilityStyles(Object obj, StyleAccumulator styleAccumulator) {
        this.mSuper.getAccessibilityStyles(obj, styleAccumulator);
        onGetAccessibilityStyles(obj, styleAccumulator);
    }

    protected void onGetAccessibilityStyles(E e, StyleAccumulator styleAccumulator) {
    }
}

package com.gumtree.android.postad.views.attribute;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.gumtree.android.postad.views.attribute.AttributeView.OnFieldValueChangeListener;
import java.util.Map;

public class VRMWrapperView extends LinearLayout implements VRMWrapperAttributeView {
    private String attributeName;
    private String value;
    @Bind({2131624811})
    CustomVRMNumberAttributeView vrmAttributeView;
    private OnFieldValueChangeListener vrmOnFieldValueChangeListener;

    public VRMWrapperView(Context context) {
        super(context);
        init(context);
    }

    public VRMWrapperView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public VRMWrapperView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(getContext()).inflate(2130903328, this);
        ButterKnife.bind(this);
    }

    public void showTitle(String str) {
        this.vrmAttributeView.showTitle(str);
    }

    public void setAttributeName(String str) {
        this.attributeName = str;
        this.vrmAttributeView.setAttributeName(str);
    }

    public void setRequired(boolean z) {
        this.vrmAttributeView.setRequired(z);
    }

    public void showValue(String str, String str2) {
        this.value = str;
        this.vrmAttributeView.showValue(str, str2);
        if (this.vrmOnFieldValueChangeListener != null) {
            this.vrmOnFieldValueChangeListener.onFieldValueChange(this.attributeName, str, str2);
        }
    }

    public void setOnFieldValueChangeListener(OnFieldValueChangeListener onFieldValueChangeListener) {
        if (onFieldValueChangeListener != null) {
            this.vrmAttributeView.setOnFieldValueChangeListener(VRMWrapperView$$Lambda$1.lambdaFactory$(this, onFieldValueChangeListener));
        }
    }

    /* synthetic */ void lambda$setOnFieldValueChangeListener$0(OnFieldValueChangeListener onFieldValueChangeListener, String str, String str2, String str3) {
        onFieldValueChangeListener.onFieldValueChange(str, str2, str3);
        if (this.vrmOnFieldValueChangeListener != null) {
            this.vrmOnFieldValueChangeListener.onFieldValueChange(str, str2, str3);
        }
    }

    public void setAttributes(Map<String, String> map) {
        this.vrmAttributeView.setAttributes(map);
    }

    public void doValidation() {
        this.vrmAttributeView.doValidation();
    }

    public void setVRMOnFieldValueChangeListener(OnFieldValueChangeListener onFieldValueChangeListener) {
        this.vrmOnFieldValueChangeListener = onFieldValueChangeListener;
    }

    public String getVRMValue() {
        return this.value;
    }

    public void registrationNotRecognised() {
        this.vrmAttributeView.registrationNotRecognised();
    }
}

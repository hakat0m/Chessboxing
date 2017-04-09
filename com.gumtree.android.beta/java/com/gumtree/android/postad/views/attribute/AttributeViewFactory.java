package com.gumtree.android.postad.views.attribute;

import android.content.Context;
import com.apptentive.android.sdk.R;
import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;
import com.gumtree.android.logging.Timber;
import com.gumtree.android.postad.customdetails.models.CustomAttributeData;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import java.util.Map;

public class AttributeViewFactory {
    public static AttributeView createAttributeView(Context context, CustomAttributeData customAttributeData) {
        switch (1.$SwitchMap$com$gumtree$android$metadata$model$MetadataType[customAttributeData.getType().ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                return new CustomStringAttributeView(context);
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return new CustomDateAttributeView(context);
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                return new CustomBooleanAttributeView(context);
            case HighlightView.GROW_RIGHT_EDGE /*4*/:
                Map attributeValues = customAttributeData.getAttributeValues();
                if (attributeValues == null || attributeValues.size() != 2) {
                    return new CustomEnumAttributeView(context);
                }
                return new CustomEnumBooleanAttributeView(context);
            case Timber.WARN /*5*/:
            case Timber.ERROR /*6*/:
                return new CustomIntegerAttributeView(context);
            case Timber.ASSERT /*7*/:
            case HighlightView.GROW_TOP_EDGE /*8*/:
                return new CustomDecimalAttributeView(context);
            case R.styleable.TextInputLayout_counterOverflowTextAppearance /*9*/:
                return new FSBODecoratorView(context);
            case R.styleable.TextInputLayout_hintAnimationEnabled /*10*/:
                return new VRMWrapperView(context);
            case R.styleable.Toolbar_popupTheme /*11*/:
                return new VRMMileageWrapperView(context);
            default:
                CrashlyticsHelper.getInstance().catchCustomLogging("Unknown metadata type: " + customAttributeData.getType());
                return new CustomStringAttributeView(context);
        }
    }
}

package com.facebook.widget;

import android.app.Activity;
import com.facebook.model.OpenGraphAction;
import com.facebook.widget.FacebookDialog.DialogFeature;
import com.facebook.widget.FacebookDialog.OpenGraphActionDialogFeature;
import java.util.EnumSet;

public class FacebookDialog$OpenGraphActionDialogBuilder extends FacebookDialog$OpenGraphDialogBuilderBase<FacebookDialog$OpenGraphActionDialogBuilder> {
    @Deprecated
    public FacebookDialog$OpenGraphActionDialogBuilder(Activity activity, OpenGraphAction openGraphAction, String str, String str2) {
        super(activity, openGraphAction, str, str2);
    }

    public FacebookDialog$OpenGraphActionDialogBuilder(Activity activity, OpenGraphAction openGraphAction, String str) {
        super(activity, openGraphAction, str);
    }

    protected EnumSet<? extends DialogFeature> getDialogFeatures() {
        return EnumSet.of(OpenGraphActionDialogFeature.OG_ACTION_DIALOG);
    }
}

package com.gumtree.android.login.registration.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.auth.services.validators.PasswordStrengthService$PasswordStrength;
import com.gumtree.android.logging.Timber;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;

public class PasswordStrengthTextView extends TextView {
    public PasswordStrengthTextView(Context context) {
        super(context);
    }

    public PasswordStrengthTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PasswordStrengthTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @TargetApi(21)
    public PasswordStrengthTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void update(PasswordStrengthService$PasswordStrength passwordStrengthService$PasswordStrength) {
        switch (1.$SwitchMap$com$gumtree$android$auth$services$validators$PasswordStrengthService$PasswordStrength[passwordStrengthService$PasswordStrength.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                setText(getContext().getString(2131165940));
                setTextColor(getResources().getColor(2131493363));
                return;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                setText(getContext().getString(2131165949));
                setTextColor(getResources().getColor(2131493363));
                return;
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                setText(getContext().getString(2131165934));
                setTextColor(getResources().getColor(2131493363));
                return;
            case HighlightView.GROW_RIGHT_EDGE /*4*/:
                setText(getContext().getString(2131165474));
                setTextColor(getResources().getColor(2131493363));
                return;
            case Timber.WARN /*5*/:
                setText(getContext().getString(2131165795));
                setTextColor(getResources().getColor(2131493018));
                return;
            case Timber.ERROR /*6*/:
                setText(getContext().getString(2131165670));
                setTextColor(getResources().getColor(2131493018));
                return;
            default:
                setText(BuildConfig.FLAVOR);
                return;
        }
    }
}

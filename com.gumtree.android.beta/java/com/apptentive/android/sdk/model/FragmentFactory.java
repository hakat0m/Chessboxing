package com.apptentive.android.sdk.model;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.apptentive.android.sdk.module.engagement.interaction.fragment.AboutFragment;
import com.apptentive.android.sdk.module.engagement.interaction.fragment.AppStoreRatingFragment;
import com.apptentive.android.sdk.module.engagement.interaction.fragment.ApptentiveBaseFragment;
import com.apptentive.android.sdk.module.engagement.interaction.fragment.EnjoymentDialogFragment;
import com.apptentive.android.sdk.module.engagement.interaction.fragment.MessageCenterErrorFragment;
import com.apptentive.android.sdk.module.engagement.interaction.fragment.MessageCenterFragment;
import com.apptentive.android.sdk.module.engagement.interaction.fragment.NavigateToLinkFragment;
import com.apptentive.android.sdk.module.engagement.interaction.fragment.NoteFragment;
import com.apptentive.android.sdk.module.engagement.interaction.fragment.RatingDialogFragment;
import com.apptentive.android.sdk.module.engagement.interaction.fragment.SurveyFragment;
import com.apptentive.android.sdk.module.engagement.interaction.fragment.UpgradeMessageFragment;
import com.apptentive.android.sdk.module.engagement.interaction.model.Interaction;
import com.apptentive.android.sdk.module.engagement.interaction.model.Interaction.Factory;
import com.apptentive.android.sdk.module.engagement.interaction.model.Interaction.Type;
import com.gumtree.android.logging.Timber;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;

public class FragmentFactory {

    /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$apptentive$android$sdk$module$engagement$interaction$model$Interaction$Type = new int[Type.values().length];

        static {
            try {
                $SwitchMap$com$apptentive$android$sdk$module$engagement$interaction$model$Interaction$Type[Type.UpgradeMessage.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$module$engagement$interaction$model$Interaction$Type[Type.EnjoymentDialog.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$module$engagement$interaction$model$Interaction$Type[Type.RatingDialog.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$module$engagement$interaction$model$Interaction$Type[Type.AppStoreRating.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$module$engagement$interaction$model$Interaction$Type[Type.Survey.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$module$engagement$interaction$model$Interaction$Type[Type.MessageCenter.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$module$engagement$interaction$model$Interaction$Type[Type.TextModal.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$module$engagement$interaction$model$Interaction$Type[Type.NavigateToLink.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public static ApptentiveBaseFragment createFragmentInstance(@NonNull Bundle bundle) {
        int i = bundle.getInt("fragmentType", 0);
        if (i != 0) {
            if (i == 3) {
                Interaction parseInteraction = Factory.parseInteraction(bundle.getCharSequence("interaction").toString());
                if (parseInteraction != null) {
                    switch (AnonymousClass1.$SwitchMap$com$apptentive$android$sdk$module$engagement$interaction$model$Interaction$Type[parseInteraction.getType().ordinal()]) {
                        case HighlightView.GROW_NONE /*1*/:
                            return UpgradeMessageFragment.newInstance(bundle);
                        case HighlightView.GROW_LEFT_EDGE /*2*/:
                            return EnjoymentDialogFragment.newInstance(bundle);
                        case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                            return RatingDialogFragment.newInstance(bundle);
                        case HighlightView.GROW_RIGHT_EDGE /*4*/:
                            return AppStoreRatingFragment.newInstance(bundle);
                        case Timber.WARN /*5*/:
                            return SurveyFragment.newInstance(bundle);
                        case Timber.ERROR /*6*/:
                            return MessageCenterFragment.newInstance(bundle);
                        case Timber.ASSERT /*7*/:
                            return NoteFragment.newInstance(bundle);
                        case HighlightView.GROW_TOP_EDGE /*8*/:
                            return NavigateToLinkFragment.newInstance(bundle);
                    }
                }
            } else if (i == 2) {
                return MessageCenterErrorFragment.newInstance(bundle);
            } else {
                if (i == 1) {
                    return AboutFragment.newInstance(bundle);
                }
            }
        }
        return null;
    }

    public static Bundle addDisplayModeToFragmentBundle(@NonNull Bundle bundle) {
        boolean z = false;
        if (!bundle.containsKey("showAsModal")) {
            if (bundle.getInt("fragmentType", 0) == 3) {
                Interaction parseInteraction = Factory.parseInteraction(bundle.getCharSequence("interaction").toString());
                if (parseInteraction != null) {
                    switch (AnonymousClass1.$SwitchMap$com$apptentive$android$sdk$module$engagement$interaction$model$Interaction$Type[parseInteraction.getType().ordinal()]) {
                        case Timber.WARN /*5*/:
                        case Timber.ERROR /*6*/:
                            break;
                    }
                }
            }
            z = true;
            bundle.putBoolean("showAsModal", z);
        }
        return bundle;
    }
}

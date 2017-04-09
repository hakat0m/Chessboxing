package com.mikepenz.materialdrawer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountHeader {
    protected static final String BUNDLE_SELECTION_HEADER = "bundle_selection_header";
    protected static final double NAVIGATION_DRAWER_ACCOUNT_ASPECT_RATIO = 0.5625d;
    protected final AccountHeaderBuilder mAccountHeaderBuilder;

    protected AccountHeader(AccountHeaderBuilder accountHeaderBuilder) {
        this.mAccountHeaderBuilder = accountHeaderBuilder;
    }

    protected AccountHeaderBuilder getAccountHeaderBuilder() {
        return this.mAccountHeaderBuilder;
    }

    public View getView() {
        return this.mAccountHeaderBuilder.mAccountHeaderContainer;
    }

    public void setDrawer(Drawer drawer) {
        this.mAccountHeaderBuilder.mDrawer = drawer;
    }

    public ImageView getHeaderBackgroundView() {
        return this.mAccountHeaderBuilder.mAccountHeaderBackground;
    }

    public void setHeaderBackground(ImageHolder imageHolder) {
        ImageHolder.applyTo(imageHolder, this.mAccountHeaderBuilder.mAccountHeaderBackground);
    }

    public void setBackground(Drawable drawable) {
        this.mAccountHeaderBuilder.mAccountHeaderBackground.setImageDrawable(drawable);
    }

    public void setBackgroundRes(@DrawableRes int i) {
        this.mAccountHeaderBuilder.mAccountHeaderBackground.setImageResource(i);
    }

    public void toggleSelectionList(Context context) {
        this.mAccountHeaderBuilder.toggleSelectionList(context);
    }

    public boolean isSelectionListShown() {
        return this.mAccountHeaderBuilder.mSelectionListShown;
    }

    public void setSelectionFirstLineShown(boolean z) {
        this.mAccountHeaderBuilder.mSelectionFirstLineShown = z;
        this.mAccountHeaderBuilder.updateHeaderAndList();
    }

    public void setSelectionSecondLineShown(boolean z) {
        this.mAccountHeaderBuilder.mSelectionSecondLineShown = z;
        this.mAccountHeaderBuilder.updateHeaderAndList();
    }

    public void setSelectionFirstLine(String str) {
        this.mAccountHeaderBuilder.mSelectionFirstLine = str;
        this.mAccountHeaderBuilder.updateHeaderAndList();
    }

    public void setSelectionSecondLine(String str) {
        this.mAccountHeaderBuilder.mSelectionSecondLine = str;
        this.mAccountHeaderBuilder.updateHeaderAndList();
    }

    public List<IProfile> getProfiles() {
        return this.mAccountHeaderBuilder.mProfiles;
    }

    public void setProfiles(List<IProfile> list) {
        this.mAccountHeaderBuilder.mProfiles = list;
        this.mAccountHeaderBuilder.updateHeaderAndList();
    }

    public void setActiveProfile(IProfile iProfile) {
        setActiveProfile(iProfile, false);
    }

    public void setActiveProfile(IProfile iProfile, boolean z) {
        boolean switchProfiles = this.mAccountHeaderBuilder.switchProfiles(iProfile);
        if (this.mAccountHeaderBuilder.mDrawer != null && isSelectionListShown()) {
            this.mAccountHeaderBuilder.mDrawer.setSelection(iProfile.getIdentifier(), false);
        }
        if (z && this.mAccountHeaderBuilder.mOnAccountHeaderListener != null) {
            this.mAccountHeaderBuilder.mOnAccountHeaderListener.onProfileChanged(null, iProfile, switchProfiles);
        }
    }

    public void setActiveProfile(long j) {
        setActiveProfile(j, false);
    }

    public void setActiveProfile(long j, boolean z) {
        if (this.mAccountHeaderBuilder.mProfiles != null) {
            for (IProfile iProfile : this.mAccountHeaderBuilder.mProfiles) {
                if (iProfile != null && iProfile.getIdentifier() == j) {
                    setActiveProfile(iProfile, z);
                    return;
                }
            }
        }
    }

    public IProfile getActiveProfile() {
        return this.mAccountHeaderBuilder.mCurrentProfile;
    }

    public void updateProfile(@NonNull IProfile iProfile) {
        updateProfileByIdentifier(iProfile);
    }

    @Deprecated
    public void updateProfileByIdentifier(@NonNull IProfile iProfile) {
        int positionByIdentifier = getPositionByIdentifier(iProfile.getIdentifier());
        if (positionByIdentifier > -1) {
            this.mAccountHeaderBuilder.mProfiles.set(positionByIdentifier, iProfile);
            this.mAccountHeaderBuilder.updateHeaderAndList();
        }
    }

    public void addProfiles(@NonNull IProfile... iProfileArr) {
        if (this.mAccountHeaderBuilder.mProfiles == null) {
            this.mAccountHeaderBuilder.mProfiles = new ArrayList();
        }
        Collections.addAll(this.mAccountHeaderBuilder.mProfiles, iProfileArr);
        this.mAccountHeaderBuilder.updateHeaderAndList();
    }

    public void addProfile(@NonNull IProfile iProfile, int i) {
        if (this.mAccountHeaderBuilder.mProfiles == null) {
            this.mAccountHeaderBuilder.mProfiles = new ArrayList();
        }
        this.mAccountHeaderBuilder.mProfiles.add(i, iProfile);
        this.mAccountHeaderBuilder.updateHeaderAndList();
    }

    public void removeProfile(int i) {
        if (this.mAccountHeaderBuilder.mProfiles != null && this.mAccountHeaderBuilder.mProfiles.size() > i) {
            this.mAccountHeaderBuilder.mProfiles.remove(i);
        }
        this.mAccountHeaderBuilder.updateHeaderAndList();
    }

    public void removeProfileByIdentifier(long j) {
        int positionByIdentifier = getPositionByIdentifier(j);
        if (positionByIdentifier > -1) {
            this.mAccountHeaderBuilder.mProfiles.remove(positionByIdentifier);
        }
        this.mAccountHeaderBuilder.updateHeaderAndList();
    }

    public void removeProfile(@NonNull IProfile iProfile) {
        removeProfileByIdentifier(iProfile.getIdentifier());
    }

    public void clear() {
        this.mAccountHeaderBuilder.mProfiles = null;
        this.mAccountHeaderBuilder.calculateProfiles();
        this.mAccountHeaderBuilder.buildProfiles();
    }

    private int getPositionByIdentifier(long j) {
        if (this.mAccountHeaderBuilder.mProfiles != null && j >= 0) {
            int i = 0;
            while (i < this.mAccountHeaderBuilder.mProfiles.size()) {
                if (this.mAccountHeaderBuilder.mProfiles.get(i) != null && ((IProfile) this.mAccountHeaderBuilder.mProfiles.get(i)).getIdentifier() == j) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    public Bundle saveInstanceState(Bundle bundle) {
        if (bundle != null) {
            bundle.putInt(BUNDLE_SELECTION_HEADER, this.mAccountHeaderBuilder.getCurrentSelection());
        }
        return bundle;
    }
}

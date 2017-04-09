package com.gumtree.android.location;

import android.support.v4.app.ActivityCompat;
import permissions.dispatcher.PermissionUtils;

final class LocationActivityPermissionsDispatcher {
    private static final String[] PERMISSION_GETCURRENTLOCATION = new String[]{"android.permission.ACCESS_FINE_LOCATION"};
    private static final int REQUEST_GETCURRENTLOCATION = 2;

    private LocationActivityPermissionsDispatcher() {
    }

    static void getCurrentLocationWithCheck(LocationActivity locationActivity) {
        if (PermissionUtils.hasSelfPermissions(locationActivity, PERMISSION_GETCURRENTLOCATION)) {
            locationActivity.getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(locationActivity, PERMISSION_GETCURRENTLOCATION, REQUEST_GETCURRENTLOCATION);
        }
    }

    static void onRequestPermissionsResult(LocationActivity locationActivity, int i, int[] iArr) {
        switch (i) {
            case REQUEST_GETCURRENTLOCATION /*2*/:
                if (PermissionUtils.getTargetSdkVersion(locationActivity) < 23 && !PermissionUtils.hasSelfPermissions(locationActivity, PERMISSION_GETCURRENTLOCATION)) {
                    return;
                }
                if (PermissionUtils.verifyPermissions(iArr)) {
                    locationActivity.getCurrentLocation();
                    return;
                } else if (!PermissionUtils.shouldShowRequestPermissionRationale(locationActivity, PERMISSION_GETCURRENTLOCATION)) {
                    locationActivity.showOnNeverAskLocation();
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }
}

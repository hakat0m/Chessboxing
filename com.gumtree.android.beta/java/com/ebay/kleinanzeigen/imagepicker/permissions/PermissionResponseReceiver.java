package com.ebay.kleinanzeigen.imagepicker.permissions;

public interface PermissionResponseReceiver {
    void onPermissionDenied();

    void onPermissionGranted();
}

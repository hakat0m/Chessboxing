package com.adjust.sdk;

public interface IRequestHandler {
    void init(IPackageHandler iPackageHandler);

    void sendPackage(ActivityPackage activityPackage, int i);
}

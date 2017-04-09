package com.ebay.classifieds.capi;

public interface LocalisedTextProvider {
    String locationServicesErrorMessage();

    String networkErrorMessage();

    String unauthorisedErrorMessage();

    String unknownError();
}

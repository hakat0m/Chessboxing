package com.ebay.classifieds.capi;

public interface ICapiClient {
    public static final String CLIENT_GSON = "gsonClient";
    public static final String CLIENT_PICTURES_UPLOAD = "picturesUploadClient";
    public static final String CLIENT_XML = "xmlClient";
    public static final String ECG_AUTHORIZATION_USER = "X-ECG-Authorization-User";
    public static final String ECG_UID = "X-ECG-UID";
    public static final String ECG_USER_AGENT = "X-ECG-USER-AGENT";
    public static final String ECG_USER_UID = "X-ECG-USER-UID";
    public static final String ECG_VERSION = "X-ECG-VER";
    public static final String THREAT_DEFENDER = "X-THREATMETRIX-SESSION-ID";

    <T> T api(Class<T> cls);
}

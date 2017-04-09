package com.apptentive.android.sdk.model;

import android.webkit.MimeTypeMap;

public class StoredFile {
    private String apptentiveUri;
    private long creationTime;
    private String id;
    private String localFilePath;
    private String mimeType;
    private String sourceUriOrPath;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String str) {
        this.mimeType = str;
    }

    public String getSourceUriOrPath() {
        return this.sourceUriOrPath;
    }

    public void setSourceUriOrPath(String str) {
        this.sourceUriOrPath = str;
    }

    public String getLocalFilePath() {
        return this.localFilePath;
    }

    public void setLocalFilePath(String str) {
        this.localFilePath = str;
    }

    public String getApptentiveUri() {
        return this.apptentiveUri;
    }

    public void setApptentiveUri(String str) {
        this.apptentiveUri = str;
    }

    public String getFileName() {
        return String.format("file.%s", new Object[]{MimeTypeMap.getSingleton().getExtensionFromMimeType(this.mimeType)});
    }

    public long getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(long j) {
        this.creationTime = j;
    }
}

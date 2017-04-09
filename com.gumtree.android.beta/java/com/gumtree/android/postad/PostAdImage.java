package com.gumtree.android.postad;

import com.ebay.kleinanzeigen.imagepicker.image_library.Image;
import java.util.UUID;

public final class PostAdImage {
    private String id;
    private boolean isModifiedByUser;
    private String path;
    private String url;

    public PostAdImage(String str, String str2, boolean z, String str3) {
        this.url = str;
        this.path = str2;
        this.isModifiedByUser = z;
        if (str3 == null) {
            this.id = UUID.randomUUID().toString();
        } else if (Image.NEW_IMAGE_ID.equals(str3)) {
            this.id = UUID.randomUUID().toString();
            this.isModifiedByUser = true;
        } else {
            this.id = str3;
        }
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public boolean isModifiedByUser() {
        return this.isModifiedByUser;
    }

    public void setModifiedByUser(boolean z) {
        this.isModifiedByUser = z;
    }

    public String getId() {
        return this.id;
    }
}

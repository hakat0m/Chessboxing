package com.gumtree.android.ads;

public class PostAdResult {
    private String adId;
    private String adStatus;
    private boolean hasError;
    private boolean isPost;
    private String message;

    public void setError(String str) {
        this.message = str;
        this.hasError = true;
    }

    public boolean hasError() {
        return this.hasError;
    }

    public String getErrorMessage() {
        return this.message;
    }

    public String getAdId() {
        return this.adId;
    }

    public void setAdId(String str) {
        this.adId = str;
    }

    public String getAdStatus() {
        return this.adStatus;
    }

    public void setAdStatus(String str) {
        this.adStatus = str;
    }

    public void setIsPost(boolean z) {
        this.isPost = z;
    }

    public boolean isPost() {
        return this.isPost;
    }

    public void setPost(boolean z) {
        this.isPost = z;
    }
}

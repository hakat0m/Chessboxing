package com.gumtree.android.conversations;

public class ReplyConversationResponse {
    private String errorMessage;
    private boolean success;

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String str) {
        this.errorMessage = str;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean z) {
        this.success = z;
    }
}

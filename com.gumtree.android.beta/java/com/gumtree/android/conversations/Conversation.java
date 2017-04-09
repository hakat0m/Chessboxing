package com.gumtree.android.conversations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Conversation implements Serializable {
    private static final String AD_STATUS_DELETED = "DELETED";
    private static final String AD_STATUS_EXPIRED = "EXPIRED";
    private String adFirstImageUrl;
    private String adId;
    private String adOwnerEmail;
    private String adOwnerId;
    private String adOwnerName;
    private String adReplierEmail;
    private String adReplierId;
    private String adReplierName;
    private String adStatus;
    private String adSubject;
    private boolean deletedBuyer;
    private boolean deletedSeller;
    private boolean flaggedBuyer;
    private boolean flaggedSeller;
    private int numUnreadMsg;
    private String replyTemplate;
    private String uid;
    private List<UserMessage> userMessages;

    public Conversation() {
        this.userMessages = new ArrayList();
    }

    public Conversation(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i, boolean z, boolean z2, boolean z3, boolean z4, List<UserMessage> list, String str11) {
        this.uid = str;
        this.adId = str2;
        this.adOwnerId = str3;
        this.adOwnerEmail = str4;
        this.adOwnerName = str5;
        this.adReplierId = str6;
        this.adReplierEmail = str7;
        this.adReplierName = str8;
        this.adSubject = str9;
        this.adFirstImageUrl = str10;
        this.numUnreadMsg = i;
        this.deletedBuyer = z;
        this.deletedSeller = z2;
        this.flaggedBuyer = z3;
        this.flaggedSeller = z4;
        if (list == null) {
            list = new ArrayList();
        }
        this.userMessages = list;
        this.adStatus = str11;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public String getAdId() {
        return this.adId;
    }

    public void setAdId(String str) {
        this.adId = str;
    }

    public String getAdOwnerId() {
        return this.adOwnerId;
    }

    public void setAdOwnerId(String str) {
        this.adOwnerId = str;
    }

    public String getAdOwnerEmail() {
        return this.adOwnerEmail;
    }

    public void setAdOwnerEmail(String str) {
        this.adOwnerEmail = str;
    }

    public String getAdOwnerName() {
        return this.adOwnerName;
    }

    public void setAdOwnerName(String str) {
        this.adOwnerName = str;
    }

    public String getAdReplierId() {
        return this.adReplierId;
    }

    public void setAdReplierId(String str) {
        this.adReplierId = str;
    }

    public String getAdReplierEmail() {
        return this.adReplierEmail;
    }

    public void setAdReplierEmail(String str) {
        this.adReplierEmail = str;
    }

    public String getAdReplierName() {
        return this.adReplierName;
    }

    public void setAdReplierName(String str) {
        this.adReplierName = str;
    }

    public String getAdSubject() {
        return this.adSubject;
    }

    public void setAdSubject(String str) {
        this.adSubject = str;
    }

    public String getAdFirstImageUrl() {
        return this.adFirstImageUrl;
    }

    public void setAdFirstImageUrl(String str) {
        this.adFirstImageUrl = str;
    }

    public int getNumUnreadMsg() {
        return this.numUnreadMsg;
    }

    public void setNumUnreadMsg(int i) {
        this.numUnreadMsg = i;
    }

    public boolean isDeletedBuyer() {
        return this.deletedBuyer;
    }

    public void setDeletedBuyer(boolean z) {
        this.deletedBuyer = z;
    }

    public boolean isDeletedSeller() {
        return this.deletedSeller;
    }

    public void setDeletedSeller(boolean z) {
        this.deletedSeller = z;
    }

    public boolean isFlaggedBuyer() {
        return this.flaggedBuyer;
    }

    public void setFlaggedBuyer(boolean z) {
        this.flaggedBuyer = z;
    }

    public boolean isFlaggedSeller() {
        return this.flaggedSeller;
    }

    public void setFlaggedSeller(boolean z) {
        this.flaggedSeller = z;
    }

    public List<UserMessage> getUserMessages() {
        return this.userMessages;
    }

    public void setUserMessages(List<UserMessage> list) {
        this.userMessages = list;
    }

    public void addUserMessage(UserMessage userMessage) {
        this.userMessages.add(userMessage);
    }

    public String getReplyTemplate() {
        return this.replyTemplate;
    }

    public void setReplyTemplate(String str) {
        this.replyTemplate = str;
    }

    public String getAdStatus() {
        return this.adStatus;
    }

    public void setAdStatus(String str) {
        this.adStatus = str;
    }

    public boolean isAdDeleted() {
        return AD_STATUS_DELETED.equalsIgnoreCase(this.adStatus);
    }

    public boolean isAdExpired() {
        return AD_STATUS_EXPIRED.equalsIgnoreCase(this.adStatus);
    }
}

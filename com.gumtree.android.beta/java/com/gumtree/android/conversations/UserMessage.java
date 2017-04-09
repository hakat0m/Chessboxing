package com.gumtree.android.conversations;

import com.gumtree.android.common.utils.DateTimeDataProcessor;
import com.gumtree.android.model.Messages;
import java.io.Serializable;

public class UserMessage implements Serializable {
    private boolean answered;
    private String conversationId;
    private String direction;
    private String id;
    private String localId;
    private String msgContent;
    private String postTimeStamp;
    private String senderName;

    public UserMessage(String str, String str2, String str3, String str4, String str5, boolean z, String str6) {
        this.id = str;
        this.conversationId = str2;
        this.senderName = str3;
        this.direction = str4;
        this.msgContent = str5;
        this.answered = z;
        this.postTimeStamp = str6;
    }

    public static UserMessage reply(ReplyConversation replyConversation) {
        UserMessage userMessage = new UserMessage();
        userMessage.conversationId = replyConversation.getConversation().getUid();
        userMessage.msgContent = replyConversation.getReplyMessage();
        userMessage.postTimeStamp = new DateTimeDataProcessor().getNowInISO();
        if (replyConversation.isNewConversation()) {
            userMessage.direction = Messages.DIRECTION_SELLER;
        } else if (replyConversation.isMine()) {
            userMessage.direction = Messages.DIRECTION_BUYER;
        } else {
            userMessage.direction = Messages.DIRECTION_SELLER;
        }
        return userMessage;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public void setSenderName(String str) {
        this.senderName = str;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String str) {
        this.direction = str;
    }

    public String getMsgContent() {
        return this.msgContent;
    }

    public void setMsgContent(String str) {
        this.msgContent = str;
    }

    public boolean isAnswered() {
        return this.answered;
    }

    public void setAnswered(boolean z) {
        this.answered = z;
    }

    public String getPostTimeStamp() {
        return this.postTimeStamp;
    }

    public void setPostTimeStamp(String str) {
        this.postTimeStamp = str;
    }

    public String getConversationId() {
        return this.conversationId;
    }

    public void setConversationId(String str) {
        this.conversationId = str;
    }
}

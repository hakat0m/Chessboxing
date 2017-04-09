package com.gumtree.android.conversations.parser;

import android.util.Xml;
import com.adjust.sdk.Constants;
import com.apptentive.android.sdk.BuildConfig;
import com.apptentive.android.sdk.model.CodePointStore;
import com.ebay.classifieds.capi.categories.CategoriesApi;
import com.ebay.classifieds.capi.locations.LocationsApi;
import com.ebay.classifieds.capi.replies.RepliesApi;
import com.ebay.classifieds.capi.users.ads.UserAdsApi;
import com.ebay.classifieds.capi.users.profile.UserProfileApi;
import com.gumtree.android.conversations.Conversation;
import com.gumtree.android.conversations.ReplyConversation;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import org.xmlpull.v1.XmlSerializer;

public class ReplyConversationXmlBuilder {
    private static final String EN_GB = "en_GB";
    private static final String REPLY = "reply:";
    private static final String REPLY_REPLY_TO_AD_CONVERSATION = "reply:reply-to-ad-conversation";
    private final ReplyConversation replyConversation;
    private final XmlSerializer serializer;

    public ReplyConversationXmlBuilder(ReplyConversation replyConversation) {
        this(replyConversation, Xml.newSerializer());
    }

    public ReplyConversationXmlBuilder(ReplyConversation replyConversation, XmlSerializer xmlSerializer) {
        this.replyConversation = replyConversation;
        this.serializer = xmlSerializer;
    }

    public String createPostReplyConversationRequestXml() throws IOException {
        Writer stringWriter = new StringWriter();
        this.serializer.setOutput(stringWriter);
        this.serializer.startDocument(Constants.ENCODING, Boolean.valueOf(true));
        this.serializer.startTag(BuildConfig.FLAVOR, REPLY_REPLY_TO_AD_CONVERSATION);
        setHeaderAttributes();
        fillInAttributeData();
        this.serializer.endTag(BuildConfig.FLAVOR, REPLY_REPLY_TO_AD_CONVERSATION);
        this.serializer.endDocument();
        return stringWriter.toString();
    }

    private void setHeaderAttributes() throws IOException {
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:types", "http://www.ebayclassifiedsgroup.com/schema/types/v1");
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:cat", CategoriesApi.CATEGORY_NAMESPACE);
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:ad", UserAdsApi.SCHEMA_AD);
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:loc", LocationsApi.LOCATION_NAMESPACE);
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:attr", "http://www.ebayclassifiedsgroup.com/schema/attribute/v1");
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:pic", "http://www.ebayclassifiedsgroup.com/schema/picture/v1");
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:user", UserProfileApi.SCHEMA_USER_PROFILE);
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:rate", "http://www.ebayclassifiedsgroup.com/schema/rate/v1");
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:reply", RepliesApi.NAMESPACE);
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:feed", "http://www.ebayclassifiedsgroup.com/schema/feed/v1");
        this.serializer.attribute(BuildConfig.FLAVOR, "supported-locales", EN_GB);
        this.serializer.attribute(BuildConfig.FLAVOR, "locale", EN_GB);
        this.serializer.attribute(BuildConfig.FLAVOR, CodePointStore.KEY_VERSION, "1.15");
    }

    private void fillInAttributeData() throws IOException {
        addAttribute("reply-message", this.replyConversation.getReplyMessage());
        Conversation conversation = this.replyConversation.getConversation();
        if (conversation != null) {
            addAttribute("ad-id", conversation.getAdId());
            if (!this.replyConversation.isNewConversation()) {
                String uid = conversation.getUid();
                if (uid != null) {
                    addAttribute("conversation-id", uid);
                }
            }
        }
    }

    private void addAttribute(String str, String str2) throws IOException {
        this.serializer.startTag(BuildConfig.FLAVOR, REPLY + str);
        if (str2 != null) {
            this.serializer.text(str2);
        }
        this.serializer.endTag(BuildConfig.FLAVOR, REPLY + str);
    }
}

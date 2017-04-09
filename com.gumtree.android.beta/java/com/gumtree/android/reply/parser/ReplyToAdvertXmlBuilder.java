package com.gumtree.android.reply.parser;

import android.util.Xml;
import com.adjust.sdk.Constants;
import com.apptentive.android.sdk.BuildConfig;
import com.apptentive.android.sdk.model.CodePointStore;
import com.ebay.classifieds.capi.replies.RepliesApi;
import com.gumtree.android.common.serializer.XmlBuilder;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import com.gumtree.android.reply.ReplyMetadataField;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import org.xmlpull.v1.XmlSerializer;

public class ReplyToAdvertXmlBuilder implements XmlBuilder {
    private static final String REPLY = "reply:";
    private static final String REPLY_AD_ID = "reply:ad-id";
    private static final String REPLY_REPLY_TO_AD = "reply:reply-to-ad";
    private String adId;
    private Collection<ReplyMetadataField> replyFields;
    private final XmlSerializer xmlSerializer;

    public ReplyToAdvertXmlBuilder() {
        this(Xml.newSerializer());
    }

    public ReplyToAdvertXmlBuilder(XmlSerializer xmlSerializer) {
        this.xmlSerializer = xmlSerializer;
    }

    public void setAdId(String str) {
        this.adId = str;
    }

    public void setReplyFields(Collection<ReplyMetadataField> collection) {
        this.replyFields = collection;
    }

    public String build() {
        try {
            Writer stringWriter = new StringWriter();
            this.xmlSerializer.setOutput(stringWriter);
            this.xmlSerializer.startDocument(Constants.ENCODING, Boolean.valueOf(true));
            this.xmlSerializer.startTag(BuildConfig.FLAVOR, REPLY_REPLY_TO_AD);
            this.xmlSerializer.attribute(BuildConfig.FLAVOR, "xmlns:reply", RepliesApi.NAMESPACE);
            this.xmlSerializer.attribute(BuildConfig.FLAVOR, "locale", "en_GB");
            this.xmlSerializer.attribute(BuildConfig.FLAVOR, CodePointStore.KEY_VERSION, "1.15");
            this.xmlSerializer.startTag(BuildConfig.FLAVOR, REPLY_AD_ID);
            this.xmlSerializer.text(this.adId);
            this.xmlSerializer.endTag(BuildConfig.FLAVOR, REPLY_AD_ID);
            for (ReplyMetadataField replyMetadataField : this.replyFields) {
                if (replyMetadataField.getId() == "reply-stored-cv-id") {
                    this.xmlSerializer.startTag(BuildConfig.FLAVOR, REPLY + replyMetadataField.getId());
                    this.xmlSerializer.text(Boolean.valueOf(replyMetadataField.getValue()).booleanValue() ? PaymentConverter.DEFAULT_PAYMENT_METHOD_ID : BuildConfig.FLAVOR);
                    this.xmlSerializer.endTag(BuildConfig.FLAVOR, REPLY + replyMetadataField.getId());
                } else {
                    this.xmlSerializer.startTag(BuildConfig.FLAVOR, REPLY + replyMetadataField.getId());
                    this.xmlSerializer.text(replyMetadataField.getValue());
                    this.xmlSerializer.endTag(BuildConfig.FLAVOR, REPLY + replyMetadataField.getId());
                }
            }
            this.xmlSerializer.endTag(BuildConfig.FLAVOR, REPLY_REPLY_TO_AD);
            this.xmlSerializer.endDocument();
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException("Problem building xml " + e.getMessage());
        }
    }
}

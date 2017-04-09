package com.gumtree.android.auth;

import android.util.Xml;
import com.adjust.sdk.Constants;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.users.profile.UserProfileApi;
import com.gumtree.android.common.serializer.XmlBuilder;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import org.xmlpull.v1.XmlSerializer;

public class ActivateUserXmlBuilder implements XmlBuilder {
    private static final String USER_SIGNATURE = "user:signature";
    private static final String USER_TOKEN = "user:token";
    private static final String USER_USER_ACTIVATION = "user:user-activation";
    private final XmlSerializer serializer;
    private final String signature;
    private final String token;

    public ActivateUserXmlBuilder(String str, String str2) {
        this(str, str2, Xml.newSerializer());
    }

    public ActivateUserXmlBuilder(String str, String str2, XmlSerializer xmlSerializer) {
        this.signature = str2;
        this.token = str;
        this.serializer = xmlSerializer;
    }

    public String build() {
        try {
            Writer stringWriter = new StringWriter();
            this.serializer.setOutput(stringWriter);
            initXmlSerializerHeaders(this.serializer);
            addToken(this.serializer);
            addSignature(this.serializer);
            endActivateUserXml(this.serializer);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException("Problem building xml when trying to activate a user" + e.getMessage());
        }
    }

    private void addToken(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startTag(BuildConfig.FLAVOR, USER_TOKEN);
        xmlSerializer.text(this.token);
        xmlSerializer.endTag(BuildConfig.FLAVOR, USER_TOKEN);
    }

    private void endActivateUserXml(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.endTag(BuildConfig.FLAVOR, USER_USER_ACTIVATION);
        xmlSerializer.endDocument();
    }

    private void addSignature(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startTag(BuildConfig.FLAVOR, USER_SIGNATURE);
        xmlSerializer.text(this.signature);
        xmlSerializer.endTag(BuildConfig.FLAVOR, USER_SIGNATURE);
    }

    private void initXmlSerializerHeaders(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startDocument(Constants.ENCODING, Boolean.valueOf(true));
        xmlSerializer.startTag(BuildConfig.FLAVOR, USER_USER_ACTIVATION);
        xmlSerializer.attribute(BuildConfig.FLAVOR, "xmlns:types", "http://www.ebayclassifiedsgroup.com/schema/types/v1");
        xmlSerializer.attribute(BuildConfig.FLAVOR, "xmlns:user", UserProfileApi.SCHEMA_USER_PROFILE);
    }
}

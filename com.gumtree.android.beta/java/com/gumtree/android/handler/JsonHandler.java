package com.gumtree.android.handler;

import com.gumtree.android.ads.Ad;
import com.gumtree.android.ads.ManageAd;
import com.gumtree.android.ads.Picture;
import com.gumtree.android.ads.PostAd;
import com.gumtree.android.category.CategoryItem;
import com.gumtree.android.conversations.Conversation;
import com.gumtree.android.conversations.UserMessage;
import com.gumtree.android.features.Feature;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.metadata.SearchMetadata;
import com.gumtree.android.metadata.SupportedValueOptions;

public interface JsonHandler {
    Batch getBatch();

    void onAdFromList(Ad ad, String str);

    void onCategory(CategoryItem categoryItem, String str);

    void onConversation(Conversation conversation);

    void onFeature(Feature feature);

    void onFinish(String str, long j, String str2);

    void onFinish(String str, long j, String str2, String str3, String str4, String str5, String str6, String str7);

    void onFinish(String str, String str2);

    void onManageAd(PostAd postAd);

    void onManagedAdFromList(ManageAd manageAd, String str);

    void onMetadata(SearchMetadata searchMetadata, String str);

    void onMetadataSupportedValuesOptions(SupportedValueOptions supportedValueOptions, String str);

    void onPicture(String str, Picture picture);

    void onPictureState(String str, String str2);

    void onPictureUploaded(String str, String str2, String str3);

    void onSearchMetadata(SearchMetadata searchMetadata, String str);

    void onUserMessageFromConversation(UserMessage userMessage);

    void onUserMessagesFromList(UserMessage userMessage);
}

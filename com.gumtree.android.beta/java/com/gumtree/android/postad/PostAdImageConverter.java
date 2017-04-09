package com.gumtree.android.postad;

import android.support.annotation.NonNull;
import com.ebay.kleinanzeigen.imagepicker.image_library.Image;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public final class PostAdImageConverter {
    private static final String SEPARATOR = "^";

    public static List<PostAdImage> fromEbayKToPostAd(@NonNull List<Image> list) {
        if (list == null) {
            return null;
        }
        List<PostAdImage> arrayList = new ArrayList();
        for (Image image : list) {
            String[] splitUrlId = splitUrlId(image.getImageId());
            arrayList.add(new PostAdImage(splitUrlId[1], image.getFinalPath(), image.isImageModifiedByUser(), splitUrlId[0]));
        }
        return arrayList;
    }

    public static List<Image> fromPostAdToEbayK(@NonNull List<PostAdImage> list) {
        if (list == null) {
            return null;
        }
        List<Image> arrayList = new ArrayList();
        for (PostAdImage postAdImage : list) {
            arrayList.add(new Image(appendUrlToId(postAdImage.getId(), postAdImage.getUrl()), postAdImage.getPath(), false));
        }
        return arrayList;
    }

    public static String appendUrlToId(String str, String str2) {
        return str2 == null ? str : str + SEPARATOR + str2;
    }

    public static String[] splitUrlId(String str) {
        String[] strArr = new String[2];
        if (str.contains(SEPARATOR)) {
            return StringUtils.split(str, SEPARATOR, 2);
        }
        strArr[0] = str;
        strArr[1] = null;
        return strArr;
    }
}

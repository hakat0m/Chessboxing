package com.facebook.model;

import java.util.Date;
import java.util.List;

public interface OpenGraphObject extends GraphObject {
    GraphObject getApplication();

    GraphObjectList<GraphObject> getAudio();

    @PropertyName("fbsdk:create_object")
    boolean getCreateObject();

    Date getCreatedTime();

    GraphObject getData();

    String getDescription();

    String getDeterminer();

    String getId();

    GraphObjectList<GraphObject> getImage();

    boolean getIsScraped();

    String getPostActionId();

    List<String> getSeeAlso();

    String getSiteName();

    String getTitle();

    String getType();

    Date getUpdatedTime();

    String getUrl();

    GraphObjectList<GraphObject> getVideo();

    void setApplication(GraphObject graphObject);

    void setAudio(GraphObjectList<GraphObject> graphObjectList);

    @PropertyName("fbsdk:create_object")
    void setCreateObject(boolean z);

    void setCreatedTime(Date date);

    void setData(GraphObject graphObject);

    void setDescription(String str);

    void setDeterminer(String str);

    void setId(String str);

    void setImage(GraphObjectList<GraphObject> graphObjectList);

    @CreateGraphObject("url")
    @PropertyName("image")
    void setImageUrls(List<String> list);

    void setIsScraped(boolean z);

    void setPostActionId(String str);

    void setSeeAlso(List<String> list);

    void setSiteName(String str);

    void setTitle(String str);

    void setType(String str);

    void setUpdatedTime(Date date);

    void setUrl(String str);

    void setVideo(GraphObjectList<GraphObject> graphObjectList);
}

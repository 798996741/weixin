package com.fh.pojo.resp;

public class Video {
    //视频文件id，可以调用获取媒体文件接口拉取
    private String MediaId;
    //视频消息的标题
    private String Title;
    //视频消息的描述
    private String Description;
    public String getMediaId() {
        return MediaId;
    }
    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }
}

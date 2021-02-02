package me.wangcai.myblog.model;


public class WordBean {
    private String title;
    private String content;
    private String imageUrl;
    private long time;

    public WordBean(String title, String content,String imageUrl,long time) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public long getTime() {
        return time;
    }
}

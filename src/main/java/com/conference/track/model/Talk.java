package com.conference.track.model;

/**
 * Talk
 *
 * @author zc.ding
 * @since 1.0
 */
public class Talk {
    
    private String title;
    private Integer time;
    private Boolean lightning = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Boolean getLightning() {
        return lightning;
    }

    public void setLightning(Boolean lightning) {
        this.lightning = lightning;
    }

    @Override
    public String toString() {
        return "Talk{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", lightning=" + lightning +
                '}';
    }
}

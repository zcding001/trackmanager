package com.conference.track.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Track desc
 *
 * @author zc.ding
 * @since 1.0
 */
public class Track {

    private Integer id;
    private List<Talk> forenoon = new ArrayList<>();
    private List<Talk> afternoon = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Talk> getForenoon() {
        return forenoon;
    }

    public void setForenoon(List<Talk> forenoon) {
        this.forenoon = forenoon;
    }

    public List<Talk> getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(List<Talk> afternoon) {
        this.afternoon = afternoon;
    }
}

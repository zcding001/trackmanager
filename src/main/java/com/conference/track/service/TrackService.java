package com.conference.track.service;

import com.conference.track.model.Talk;
import com.conference.track.model.Track;

import java.util.List;

/**
*  Track service 
*  @author                  ：zc.ding@foxmail.com
*  @since                   ：1.0
*/
public interface TrackService {

    /**
    *  get all track info
    *  @param filePath  input file path
    *  @return java.util.List<com.conference.track.model.Track>
    *  @author  zc.ding@foxmail.com
    */
    List<Track> getTracks(String filePath) throws Exception;

    /**
     *  get all talk info from input path
     *  @param filePath  input file path
     *  @return java.util.List<com.conference.track.model.Talk>
     *  @since                   ：2019/4/5
     *  @author                  ：zc.ding@foxmail.com
     */
    List<Talk> getTalks(String filePath) throws Exception;

    List<Track> getTracks(String filePath, int trackIndex) throws Exception;

    int getGap();  
    
}

package com.conference.track.service.impl;

import com.conference.track.model.Talk;
import com.conference.track.model.Track;
import com.conference.track.service.TrackService;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * TrackServiceImpl
 * @author zc.ding
 * @since 1.0
 */
public class TrackServiceImpl implements TrackService {

    private final Pattern pattern = Pattern.compile("\\d+min|lightning");
    private final String SUFFIX = "min";
    /**lightning is 5 min**/
    private final Integer LIGHTNING = 5;
    private int max = 210;
    private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    private volatile boolean flag = true;
    
    public TrackServiceImpl(){
        threadLocal.set(0);
    }
    

    @Override
    public List<Talk> getTalks(String filePath) throws Exception{
        return Files.readAllLines(Paths.get(filePath)).stream().map(line -> {
            if (line.length() > 0) {
                Talk talk = new Talk();
                talk.setTime(1);
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    String time = matcher.group().toLowerCase().trim();
                    if (time.endsWith(SUFFIX)) {
                        talk.setTime(Integer.parseInt(time.replace(SUFFIX, "")));
                    }else{
                        talk.setTime(LIGHTNING);
                        talk.setLightning(true);
                    }
                    talk.setTitle(line.replace(time, "").trim());
                }
                return talk;
            }
            return null;
        }).filter(Objects::nonNull).sorted(Comparator.comparing(Talk::getTime).reversed()).collect(Collectors.toList());
    }
    
    @Override
    public List<Track> getTracks(String filePath) throws Exception{
        List<Track> tracks = new ArrayList<>();
        List<Talk> talks = getTalks(filePath);
        while (talks.size() > 0) {
            tracks.add(dealTalk(talks));
        }
        return tracks;
    }

    @Override
    public List<Track> getTracks(String filePath, int extraTime) throws Exception {
        List<Track> tracks = new ArrayList<>();
        List<Talk> talks = getTalks(filePath);
        while (talks.size() > 0) {
            tracks.add(dealTalk(talks));
        }
        return tracks;
    }

    public int getGap() {
        int i = threadLocal.get();
        threadLocal.remove();
        return i;
    }

    /**
     *  处理每天的上午下午
     *  @param talks 所有任务
     *  @return com.conference.track.model.Track
     *  @since                   ：2019/4/5
     *  @author                  ：zc.ding@foxmail.com
     */
    private Track dealTalk(List<Talk> talks) {
        List<List<Talk>> combList = combination(talks, max - threadLocal.get());
        Track track = new Track();
        track.setForenoon(combList.get(0));
        talks.removeAll(track.getForenoon());
        if(talks.size() > 0){
            combList = combination(talks, max + threadLocal.get());
            track.setAfternoon(combList.get(0));
            talks.removeAll(track.getAfternoon());
        }
        return track;
    }
    

    /**
    *  talks中获得时间累加是target的所有组合
    *  @param talks 所有任务
    *  @param target    累计时间
    *  @return java.util.List<java.util.List<com.conference.track.model.Talk>>
    *  @since                   ：2019/4/5
    *  @author                  ：zc.ding@foxmail.com
    */
    private List<List<Talk>> combination(List<Talk> talks, int target) {
        List<List<Talk>> combLIst = new LinkedList<>();
        combination(talks, target, combLIst, new LinkedList<>(), 0);
        if(combLIst.size() == 0){
            return combination(talks, --target);
        }else{
            if (threadLocal.get() == 0 && flag) {
                threadLocal.set(max - target);
                flag = false;
            }
        }
        return combLIst;
    }

    /**
    *  递归从talks中获得时间累加是target的所有组合
    *  @param talks 所有任务
    *  @param target    累计时间
    *  @param combList  结果
    *  @param item  子结果
    *  @param index 索引
    *  @since                   ：2019/4/5
    *  @author                  ：zc.ding@foxmail.com
    */
    private void combination(List<Talk> talks, int target, List<List<Talk>> combList, List<Talk> item, int index) {
        if (target == 0) {
            combList.add(item);
            return;
        }
        if (index >= talks.size() || talks.get(index).getTime() > target) {
            return;
        }
        boolean first = true;
        for (int i = index; i < talks.size() && talks.get(i).getTime() <= target; i++) {
            if (first || Objects.equals(talks.get(i).getTime(), talks.get(i - 1).getTime())) {
                List<Talk> tmp = new LinkedList<>(item);
                tmp.add(talks.get(i));
                combination(talks, target - talks.get(i).getTime(), combList, tmp, i+1);
            }
            first = false;
        }
    }

    /**
     *  优化版: 递归从talks中获得时间累加是target的所有组合
     *  @param talks 所有任务
     *  @param target    累计时间
     *  @param combList  结果
     *  @param item  子结果
     *  @param index 索引
     *  @since                   ：2019/4/5
     *  @author                  ：zc.ding@foxmail.com
     */
    private void combination2(List<Talk> talks, int target, List<List<Talk>> combList, List<Talk> item, int index) {
        if (target == 0) {
            combList.add(item);
        } else {
            for (int i = index; i < talks.size() && talks.get(i).getTime() <= target; i++) {
                if (i == index || Objects.equals(talks.get(i).getTime(), talks.get(i - 1).getTime())) {
                    List<Talk> list = new ArrayList<>(item);
                    final Talk talk = talks.get(i);
                    list.add(talk);
                    combination(talks, target - talk.getTime(), combList, list, ++i);
                }
            }
        }
    }    
}

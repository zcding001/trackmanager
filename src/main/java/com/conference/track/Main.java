package com.conference.track;

import com.conference.track.model.Talk;
import com.conference.track.model.Track;
import com.conference.track.service.TrackService;
import com.conference.track.service.impl.TrackServiceImpl;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * main
 * @author zc.ding
 * @since 1.0
 */
public class Main {

    public static void main(String[] args) throws Exception {
//        if (args.length < 1) {
//            System.out.println("usage java -jar track filePath");
//        }
        String filePath = "";
        if(args != null && args.length > 0) {
            filePath = args[0];
        }else{
            URL url =  Main.class.getClassLoader().getResource("input.txt");
            if (url != null) {
                filePath = url.getPath();
            }
        }
        if (filePath == null || !(new File(filePath).isFile())) {
            throw new RuntimeException("can't find input file.");
        }
//        printTrack(new TrackServiceImpl().getTracks(new File(args[0]).getAbsolutePath()));
//        printTrack(new TrackServiceImpl().getTracks(new File(args[0]).getAbsolutePath()));
        TrackService trackService = new TrackServiceImpl();
        printTrack(trackService.getTracks(new File(filePath).getAbsolutePath()), trackService.getGap());
    }

    /**
    *  print track message
    *  @param tracks    track messages
    *  @author                  ：zc.ding@foxmail.com
    */    
    public static void printTrack(List<Track> tracks, int extraTime) {
        StringBuilder sb = new StringBuilder();
        tracks.forEach(track -> {
            LocalDateTime mon = LocalDateTime.of(2000, Month.JANUARY, 1, 12, 30, 0);
            mon = mon.plusMinutes(-extraTime);
            sb.append("track").append(tracks.indexOf(track) + 1).append("\n");
            LocalDateTime date = LocalDateTime.of(2000, Month.JANUARY, 1, 9, 0, 0);
            for (int i = 0; i < track.getForenoon().size(); i++) {
                date = fitMsg(sb, track.getForenoon().get(i), date, "AM");
            }
//            sb.append("12:00PM Lunch").append("\n");
            sb.append(mon.format(DateTimeFormatter.ofPattern("HH:mm"))).append("PM Lunch").append("\n");
            if (track.getAfternoon().size() > 0) {
                date = LocalDateTime.of(2000, Month.JANUARY, 1, 1, 30, 0);
                date = date.plusMinutes(-extraTime);
                for (int j = 0; j < track.getAfternoon().size(); j++) {
                    date = fitMsg(sb, track.getAfternoon().get(j), date, "PM");
                }
            }
            sb.append("05:00PM Networking Event").append("\n\n");
//            result.add(sb.toString());
        });
        System.out.println(sb.toString());
    }

    /**
    *  组装信息
    *  @param sb 信息
    *  @param talk  任务
    *  @param date  时间
    *  @param apm 单位
    *  @return java.time.LocalDateTime
    *  @since                   ：2019/4/6
    *  @author                  ：zc.ding@foxmail.com
    */
    private static LocalDateTime fitMsg(StringBuilder sb, Talk talk, LocalDateTime date, String apm) {
        String dateTime = date.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + apm;
        sb.append(dateTime).append(" ").append(talk.getTitle()).append(" ");
        if (talk.getLightning()) {
            sb.append("Lightning");
        }else{
            sb.append(talk.getTime()).append("min");
        }
        sb.append("\n");
        return date.plusMinutes(talk.getTime());
    }
}

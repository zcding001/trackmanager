package com.conference.track.test;

import com.conference.track.Main;
import com.conference.track.model.Track;
import com.conference.track.service.impl.TrackServiceImpl;
import org.junit.Assert;
import org.junit.Test;


import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TalkServiceTest
 *
 * @author zc.ding
 * @since 1.0
 */
public class TalkServiceTest {
    
    @Test
    public void testPattern() {
        Pattern pattern = Pattern.compile("\\d+min|lightning");
//        String msg = "Writing Fast Tests Against Enterprise Rails 60min";
        String msg = "Rails for Python Developers  60min lightning";
        Matcher matcher = pattern.matcher(msg);
        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }
    
    @Test
    public void testDate(){
//        Date date = Date.from(LocalDateTime.parse("09:00:00", DateTimeFormatter.ofPattern("HH:mm:ss")).toInstant(ZoneOffset.of("+8")));
//        Date.from(date.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime().plusMinutes(60).toInstant(ZoneOffset.of("+8")));
        LocalDateTime date = LocalDateTime.of(2000, Month.JANUARY, 1, 9, 0, 0);
        System.out.println(date.plusMinutes(60));
    }


//    @Test
//    public void testSingleTalk() throws Exception {
//        String filePath = "C:\\soft\\projects\\trackmanager\\src\\main\\resources\\input.txt";
//        Main main = new Main();
//        List<Track> list = new TrackServiceImpl().getTracks(new File(filePath).getAbsolutePath());
//        List<String> result= main.printTrack(list);
//
//        Assert.assertEquals("09:00:00AM Writing Fast Tests Against Enterprise Rails 60min", result.get(0));
////        Assert.assertThat(list.get(0), "09:00:00AM Writing Fast Tests Against Enterprise Rails 60min");
//
//        Assert.assertTrue(result.get(0).contains("09:00:00AM Writing Fast Tests Against Enterprise Rails 60min"));
//    }
//    
}

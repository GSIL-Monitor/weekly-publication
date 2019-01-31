package com.tgt.weekly.publication;

import com.alibaba.fastjson.JSON;
import com.tgt.weekly.publication.entity.DM;
import com.tgt.weekly.publication.service.CpuDataHandle;
import com.tgt.weekly.publication.service.HeartDataHandle;
import com.tgt.weekly.publication.utils.DateUtils;
import com.tgt.weekly.publication.utils.FreemarkerUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static com.tgt.weekly.publication.constant.CommonConstant.HEART;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/18
 * @Time: 16:00
 * @Description: To change this template use File | Settings | File Templates.
 **/
public class NormalTests {

    @Test
    public void testStringUtils(){
        System.out.println(StringUtils.substringBeforeLast("DM3_api.txt.zip", "."));
    }


    @Test
    public void testDate(){
        System.out.println((new Date().getTime() - 24 * 3600000)/(1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset());
        System.out.println(DateUtils.getLastWeekEnd());
    }

    @Test
    public void testZip() throws Exception{
        File file = new File("F:" + java.io.File.separator + "test-weekly" + java.io.File.separator + "test-weekly.zip");
            try {
                ZipInputStream zin = new ZipInputStream(new FileInputStream(file));
                ZipEntry ze;
                while ((ze = zin.getNextEntry()) != null){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new ZipFile(file).getInputStream(ze)));
                    String line;
                    while ((line = reader.readLine()) != null){
                        System.out.println(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Test
    public void testDm() throws Exception{
        DM dm = new DM();
        dm(dm);
        System.out.println(dm);
    }

    private void dm(DM dm){
        dm.setUser("root1");
    }

    @Test
    public void testTemplate(){
        Map map = new HashMap();
        map.put("statisticalTime", "2018-12-20");
        map.put("activeTerminal", "2018-12-20");
        map.put("activeSim", "2018-12-20");
        map.put("onlinePeak", "2018-12-20");
        map.put("heartTotal", "2018-12-20");
        map.put("deductTotal", "2018-12-20");
        map.put("certifyTotal", "2018-12-20");
        new FreemarkerUtil().createWordTemplate(map, "bss-daily-2003.xml", "F:\\bss-daily","BSS服务器运行状态日报-demo.doc");
    }

    @Test
    public void testDay(){
        System.out.println(DateUtils.getStringDay(1519179221000l));
        System.out.println(DateUtils.getMinuteDay(1544454059000L));
    }

    @Test
    public void testHeart(){
        long start = System.currentTimeMillis();
        new CpuDataHandle().packageData(0);
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testArray(){
        System.out.println(JSON.toJSONString(new HeartDataHandle().convertData(1)));
    }

    @Test
    public void testRun() throws IOException {
        Runtime.getRuntime().exec("cmd.exe /c start F:\\work\\weekly-publication\\src\\main\\resources\\pic.bat");
    }

    @Test
    public void testEndDay() throws IOException {
        System.out.println(DateUtils.getLastWeekEnd());
    }

    @Test
    public void testCompare(){
        System.out.println("20181230.txt".compareTo("20181231.txt"));
    }
}

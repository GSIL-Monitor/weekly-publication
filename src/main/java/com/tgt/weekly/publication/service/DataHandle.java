package com.tgt.weekly.publication.service;

import com.tgt.weekly.publication.model.DataModel;
import com.tgt.weekly.publication.utils.DataHandleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;

import static com.tgt.weekly.publication.constant.CommonConstant.FILE_PATH;
import static com.tgt.weekly.publication.constant.CommonConstant.SEPARATOR;
import static com.tgt.weekly.publication.utils.Base64Utils.getImageBase;
import static com.tgt.weekly.publication.utils.DateUtils.*;
import static com.tgt.weekly.publication.utils.HighChartsUtils.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/19
 * @Time: 13:26
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Slf4j
public abstract class DataHandle<T> {

    protected static final int THIRTY = 30;
    protected static final int TWENTY_FIVE = 25;
    protected static final int TWENTY = 20;
    protected static final int FIFTEEN = 15;
    protected static final int TEN = 10;
    protected static final int FIVE = 5;
    protected static final String TWENTY_FOUR_PIC = "outfile1.png";
    protected static final String SIXTY_PIC = "outfile2.png";
    protected static final String SIX_HUNDRED_PIC = "outfile3.png";
    protected final DataHandleUtils dataHandleUtils = new DataHandleUtils();

    /**
     * 默认处理方式
     *
     * @param line
     * @param map
     */
    protected void defaultHandle(String line, Map<Long,int[]> map){
        String[] strData = line.split(" ");
        if (strData.length != 4){
            return;
        }
        long key = Long.parseLong(strData[0]);
        int [] data = toIntArray(strData);
        if (map.containsKey(key)){
            map.get(key)[0] += data[0];
            map.get(key)[1] += data[1];
            map.get(key)[2] += data[2];
            return;
        }
        map.put(key, new int[]{data[0], data[1],data[2]});
    }

    /**
     * 处理压缩文件中各种数据
     *
     * @param line
     */
    public abstract void handle(String line);

    protected void doWrite(String dirName, Map<Long,int[]> map) throws IOException{
        doWrite(dirName, map ,false);
    }

    protected void doWrite(String dirName, Map<Long,int[]> map, boolean flag) throws IOException {
        File file=new File(String.format("%s%s%s", FILE_PATH, SEPARATOR, dirName));
        if (!file.exists()){
            file.mkdirs();
        }
        map = new TreeMap<>(map);
        Set<String> set = new HashSet<>(7);
        FileWriter fileWriter = null;
        if (flag){
            for (Map.Entry<Long, int[]> entry : map.entrySet()) {
                fileWriter = getFileWriter(dirName, set, fileWriter, entry);
                fileWriter.write(String.format("%d %d\n", entry.getKey(), entry.getValue()) );
            }
        }else {
            for (Map.Entry<Long, int[]> entry : map.entrySet()) {
                fileWriter = getFileWriter(dirName, set, fileWriter, entry);
                if (entry.getValue()[1] == 0){
                    fileWriter.write(String.format("%d %d %d %d %d\n", entry.getKey(), entry.getValue()[0], entry.getValue()[1], entry.getValue()[2] , 0) );
                    continue;
                }
                fileWriter.write(String.format("%d %d %d %d %d\n", entry.getKey(), entry.getValue()[0], entry.getValue()[1], entry.getValue()[2] , entry.getValue()[2] / entry.getValue()[1]) );
            }
        }
        if (fileWriter != null){
            fileWriter.close();
        }
        map.clear();
    }

    /**
     * 处理压缩文件中各种数据
     *
     * @param day
     */
    public List<DataModel> concludeData(int day, String type){
        File[] files = dataHandleUtils.getFiles(type);
        if (files == null){
            return null;
        }
        List<DataModel> dataModels = new ArrayList<>(day);
        for (int i = 0; i < day; i++){
            Map<String, Integer> minuteMap = new LinkedHashMap<>(1440);
            DataModel dataModel = new DataModel().setMinTime(Integer.MAX_VALUE);
            int frequency = 0;
            long spendTime = 0;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(files[i])));
                log.info("文件名为：{}", files[i].getName());
                String line;
                while ((line = reader.readLine()) != null){
                    String[] strData = line.split(" ");
                    long key = Long.parseLong(strData[0]);
                    int [] data = toIntArray(strData);
                    frequency += data[1];
                    spendTime += data[2];
                    /*
                    处理分钟数据：同一分钟内数据存入map
                     */
                    if (!minuteMap.containsKey(getMinuteDay(key))){
                        minuteMap.put(getMinuteDay(key), data[1]);
                    }else {
                        minuteMap.put(getMinuteDay(key), data[1] + minuteMap.get(getMinuteDay(key)));
                    }
                    dataModel.setMaxSecond(dataModel.getMaxSecond() > data[1] ? dataModel.getMaxSecond() : data[1])
                            .setMaxTime(dataModel.getMaxTime() > data[3] ? dataModel.getMaxTime() : data[3])
                            .setMinTime(dataModel.getMinTime() > data[3] && data[3] != 0 ? data[3] : dataModel.getMinTime());
                    secondFrequency(dataModel, data[1]);
                }
                List<Integer> minuteList = new ArrayList<>(minuteMap.values());
                Collections.sort(minuteList, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2 - o1;
                    }
                });
                dataModel.setMaxMinute(minuteList.get(0));
                dataModel.setTotal(frequency);
                if (frequency > 0){
                    dataModel.setAvgTime((int) (spendTime / frequency));
                    dataModel.setThroughput(1000 / dataModel.getAvgTime());
                }
                minuteMap.clear();
                minuteList.clear();
                dataModels.add(dataModel);
            } catch (IOException e) {
                log.error("{}数据归类失败", type);
                e.printStackTrace();
            }
        }
        return dataModels;
    }

    protected Map<String, Integer> convertData(List<DataModel> dataModels, String type){
        Map<String, Integer> map = new HashMap<>(15);
        //todo？ 可使用反射
        for (int i = 0; i < dataModels.size(); i ++){
            map.put(String.format("%s%s%d", type, "Total", i), dataModels.get(i).getTotal());
            map.put(String.format("%s%s%d", type, "MaxMinute", i), dataModels.get(i).getMaxMinute());
            map.put(String.format("%s%s%d", type, "MaxSecond", i), dataModels.get(i).getMaxSecond());
            map.put(String.format("%s%s%d", type, "AvgTime", i), dataModels.get(i).getAvgTime());
            map.put(String.format("%s%s%d", type, "MaxTime", i), dataModels.get(i).getMaxTime());
            map.put(String.format("%s%s%d", type, "MinTime", i), dataModels.get(i).getMinTime());
            map.put(String.format("%s%s%d", type, "Throughput", i), dataModels.get(i).getThroughput());
            map.put(String.format("%s%s%d", type, "FiveNum", i), dataModels.get(i).getFiveNum());
            map.put(String.format("%s%s%d", type, "TenNum", i), dataModels.get(i).getTenNum());
            map.put(String.format("%s%s%d", type, "FifteenNum", i), dataModels.get(i).getFifteenNum());
            map.put(String.format("%s%s%d", type, "TwentyNum", i), dataModels.get(i).getTwentyNum());
            map.put(String.format("%s%s%d", type, "TwentyFiveNum", i), dataModels.get(i).getTwentyFiveNum());
            map.put(String.format("%s%s%d", type, "ThirtyNum", i), dataModels.get(i).getThirtyNum());
            map.put(String.format("%s%s%d", type, "OverNum", i), dataModels.get(i).getOverNum());
        }
        return map;
    }

    public List<Map<String, Integer>> packageHourData(int day, String type){
        File[] files = dataHandleUtils.getFiles(type);
        if (files == null){
            return null;
        }
        List<Map<String, Integer>> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(files[day])));
            Map<String, Integer> hourMap = new LinkedHashMap<>(24);
            Map<String, Integer> secondMap = new HashMap(86400);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strData = line.split(" ");
                long key = Long.parseLong(strData[0]);
                int frequency = Integer.parseInt(strData[2]);
                if (!hourMap.containsKey(getHourDay(key))){
                    hourMap.put(getHourDay(key), frequency);
                }else{
                    hourMap.put(getHourDay(key), frequency + hourMap.get(getHourDay(key)));
                }
                secondMap.put(getSecondDay(key), frequency);
            }
            list.add(hourMap);
            list.add(secondMap);
        } catch(IOException e){
                e.printStackTrace();
        }
        return list;
    }

    public void packageSecondAndMinuteData(List<Map<String, Integer>> data, String type, String name){
        Map<String, Integer> hour = data.get(0);
        List<Map.Entry<String, Integer>> list = new ArrayList<>(hour.entrySet());
        Map<String, Integer> hours = dataHandleUtils.getHourMap();
        Map<String, Integer> minutes = dataHandleUtils.getMinutesMap();
        Map<String, Integer> seconds = dataHandleUtils.getSecondsMap();
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        String maxHour = list.get(0).getKey();
        hours.putAll(hour);
        for (Map.Entry<String, Integer> entry : data.get(1).entrySet()) {
            if (StringUtils.equals(maxHour, entry.getKey().substring(0, 2))){
                String minuteStr = entry.getKey().substring(2, 4);
                if (Integer.parseInt(minuteStr) < 10){
                    seconds.put(String.format("%s:%s", entry.getKey().substring(2, 4), entry.getKey().substring(4, 6)), entry.getValue());
                }
                if (!minutes.containsKey(minuteStr)){
                    minutes.put(minuteStr, entry.getValue());
                }else {
                    minutes.put(minuteStr, minutes.get(minuteStr) + entry.getValue());
                }
            }
        }
        createDayJson(hours, type, name);
        createHourJson(minutes, type, name);
        createMinuteJson(seconds, type, name);
    }

    public Map<String, Object> picToBase64(String type){
        Map<String, Object> picMap = new HashMap<>(5);
        picMap.put(String.format("%s%s", type, "img1"), getImageBase(String.format("%s%s%s%s%s", FILE_PATH, SEPARATOR, type, SEPARATOR, TWENTY_FOUR_PIC)));
        picMap.put(String.format("%s%s", type, "img2"), getImageBase(String.format("%s%s%s%s%s", FILE_PATH, SEPARATOR, type, SEPARATOR, SIXTY_PIC)));
        picMap.put(String.format("%s%s", type, "img3"), getImageBase(String.format("%s%s%s%s%s", FILE_PATH, SEPARATOR, type, SEPARATOR, SIX_HUNDRED_PIC)));
        return picMap;
    }

    private int[] toIntArray(String [] strings){
        int[] ints = new int[strings.length - 1];
        for (int i = 0; i < strings.length - 1; i ++){
            ints[i] = Integer.parseInt(strings[i + 1]);
        }
        return ints;
    }

    private FileWriter getFileWriter(String dirName, Set<String> set, FileWriter fileWriter, Map.Entry<Long, int[]> entry) throws IOException {
        if (!set.contains(getStringDay(entry.getKey()))){
            if (fileWriter != null){
                fileWriter.close();
            }
            set.add(getStringDay(entry.getKey()));
            fileWriter = new FileWriter(new File(String.format("%s%s%s%s%s.txt", FILE_PATH, SEPARATOR, dirName, SEPARATOR, getStringDay(entry.getKey()))));
        }
        return fileWriter;
    }

    private void secondFrequency(DataModel dataModel, int frequency){
        if (frequency < FIVE){
            dataModel.setFiveNum(dataModel.getFiveNum() + 1);
            return;
        }
        if (frequency >= THIRTY){
           dataModel.setOverNum(dataModel.getOverNum() + 1);
           return;
        }
        if (frequency >= TWENTY_FIVE){
            dataModel.setThirtyNum(dataModel.getThirtyNum() + 1);
        }else if (frequency >= TWENTY) {
            dataModel.setTwentyFiveNum(dataModel.getTwentyFiveNum() + 1);
        }else if (frequency >= FIFTEEN){
            dataModel.setTwentyNum(dataModel.getTwentyNum() + 1);
        }else if (frequency >= TEN){
            dataModel.setFifteenNum(dataModel.getFifteenNum() + 1);
        }else if (frequency >= FIVE){
            dataModel.setTenNum(dataModel.getTenNum() + 1);
        }
    }
}

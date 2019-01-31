package com.tgt.weekly.publication;

import com.tgt.weekly.publication.entity.DM;
import com.tgt.weekly.publication.properties.DMProperties;
import com.tgt.weekly.publication.service.ReportServiceImpl;
import com.tgt.weekly.publication.service.SendMailImpl;
import com.tgt.weekly.publication.ssh2.LinuxConnect;
import com.tgt.weekly.publication.utils.DateUtils;
import com.tgt.weekly.publication.utils.FreemarkerUtil;
import com.tgt.weekly.publication.utils.WordToPDF;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static com.tgt.weekly.publication.constant.CommonConstant.*;
import static com.tgt.weekly.publication.utils.DateUtils.getDateToStringStyle;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/18
 * @Time: 16:30
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Component
@Slf4j
public class CustomCommandLine implements CommandLineRunner {

    private static final String ONE_DAY = "1";
    private static final String ONE_WEEK = "7";

    @Autowired
    DMProperties dmProperties;

    @Autowired
    LinuxConnect linuxConnect;

    @Autowired
    SendMailImpl sendMail;

    @Autowired
    ReportServiceImpl reportService;

    @Override
    public void run(String... args) throws Exception {
        long start = System.currentTimeMillis();
        if (args.length != 1 || !StringUtils.equalsAny(args[0], ONE_DAY, ONE_WEEK)){
            log.error("请输入正确的参数");
            return;
        }
        deleteOldFile();
        dmProperties.setStart(StringUtils.equals(args[0], ONE_DAY) ? DateUtils.getYesterday() : DateUtils.getLastWeekBegin());
        dmProperties.setEnd(StringUtils.equals(args[0], ONE_DAY) ? DateUtils.getToday() : DateUtils.getLastWeekEnd());
        dmProperties.setDay(Integer.parseInt(args[0]));
        for(DM dm :dmProperties.getDmList()){
            linuxConnect.execScript(dm);
            linuxConnect.download(dm);
        }
        linuxConnect.generateData();
        Map root = reportService.handleCombineData();
        root.put("statisticalTime", getDateToStringStyle(TIME_FORMAT, new Date()));
        new FreemarkerUtil().createWordTemplate(root,
                TEMPLATE_NAME,
                FILE_PATH,
                String.format("BSS服务器运行状态日报_%s.doc", getDateToStringStyle(TIME_FORMAT, new Date())));
        WordToPDF.doc2pdf(
                String.format("/home/domain/weekly-new/file/BSS服务器运行状态日报_%s.doc", getDateToStringStyle(TIME_FORMAT, new Date())),
                String.format("/home/domain/weekly-new/file/BSS服务器运行状态日报_%s.pdf", getDateToStringStyle(TIME_FORMAT, new Date()))
        );
        sendMail.sendDayPublication();
        log.info("执行总耗时为：{}s", (System.currentTimeMillis() - start) / 1000);
    }

    private void deleteOldFile() throws IOException {
        Runtime run = Runtime.getRuntime();
        run.exec("rm -rf /home/domain/weekly-new/file/*");
        run.exec("rm -rf /home/domain/weekly-new/compress/*");
    }
}

package com.tgt.weekly.publication.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

import static com.tgt.weekly.publication.constant.CommonConstant.TIME_FORMAT;
import static com.tgt.weekly.publication.utils.DateUtils.getDateToStringStyle;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2019/1/28
 * @Time: 9:23
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Service
@Slf4j
public class SendMailImpl {

    @Autowired
    JavaMailSenderImpl javaMailSender;

    public void sendDayPublication(){
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(javaMailSender.getUsername());
            helper.setTo(new String[]{
                    "tiaolun.li@51tgt.com",
                    "yuxiong.su@51tgt.com",
                    "siqi.yu@51tgt.com"
            });
            helper.setText("日报详情见附件");
            helper.setSubject(String.format("BSS服务器运行状态日报_%s", getDateToStringStyle(TIME_FORMAT, new Date())));
            FileSystemResource file = new FileSystemResource(new File(String.format("/home/domain/weekly-new/file/BSS服务器运行状态日报_%s.pdf", getDateToStringStyle(TIME_FORMAT, new Date()))));
//            FileSystemResource file = new FileSystemResource(new File(String.format("F:/test-weekly/BSS服务器运行状态日报_%s.doc", getDateToStringStyle(TIME_FORMAT, new Date()))));
            helper.addAttachment("日报.doc", file);
        } catch (MessagingException e) {
            log.error("邮件发送失败，请检查系统");
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }
}

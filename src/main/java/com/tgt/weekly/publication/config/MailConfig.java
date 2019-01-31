package com.tgt.weekly.publication.config;

import com.tgt.weekly.publication.properties.MailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2019/1/3
 * @Time: 10:22
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Configuration
public class MailConfig {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    @Autowired
    MailProperties mailProperties;

    @Bean
    public SimpleMailMessage templateMessage(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        return simpleMailMessage;
    }

    @Bean
    public JavaMailSenderImpl javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setDefaultEncoding(DEFAULT_CHARSET.name());
        javaMailSender.setHost(mailProperties.getHost());
        javaMailSender.setProtocol(mailProperties.getProtocol());
        javaMailSender.setUsername(mailProperties.getUsename());
        javaMailSender.setPassword(mailProperties.getPassword());
        javaMailSender.setJavaMailProperties(mailProperties());
        //阿里屏蔽端口25
        javaMailSender.setPort(465);
        return javaMailSender;
    }

    private Properties mailProperties(){
        Properties properties = new Properties();
        //阿里云屏蔽了默认25端口
//        properties.put("mail.smtp.auth", true);
//        properties.put("mail.smtp.starttls.enable", true);
//        properties.put("mail.smtp.starttls.required", true);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return properties;
    }
}

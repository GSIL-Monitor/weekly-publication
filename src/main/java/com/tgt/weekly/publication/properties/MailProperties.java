package com.tgt.weekly.publication.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2019/1/3
 * @Time: 10:22
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Component
@ConfigurationProperties(prefix = "dm.mail")
@Data
public class MailProperties {

    private String host;

    private String protocol;

    private String usename;

    private Integer port;

    private String password;
}

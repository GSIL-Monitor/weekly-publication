package com.tgt.weekly.publication.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/24
 * @Time: 16:59
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Slf4j
public class Base64Utils {

    public static String getImageBase(String path) {
        if (StringUtils.isEmpty(path)) {
            return "";
        }
        File file = new File(path);
        if (!file.exists()) {
            return "";
        }
        byte[] data = null;
        try (InputStream in = new FileInputStream(file)) {
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            log.error("文件流读取错误");
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}

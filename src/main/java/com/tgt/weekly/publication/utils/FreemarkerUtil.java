package com.tgt.weekly.publication.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/19
 * @Time: 18:24
 * @Description: To change this template use File | Settings | File Templates.
 **/
public class FreemarkerUtil {

    private Configuration configuration;

    {
        configuration = new Configuration(Configuration.VERSION_2_3_22);
        //设置编码
        configuration.setDefaultEncoding("UTF-8");
//        configuration.setClassicCompatible(true);
        //ftl模板文件统一放至resources包下面
        configuration.setClassForTemplateLoading(FreemarkerUtil.class,"/");
        //获取模板 
    }

    public void createWordTemplate(Map dataMap, String templateName, String filePath, String fileName){
        try {
            Template template = configuration.getTemplate(templateName);
            //输出文件
            File outFile = new File(String.format("%s%s%s", filePath, File.separator, fileName));
            //如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
            }
            //将模板和数据模型合并生成文件 
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
            //生成文件
            template.process(dataMap, out);
            //关闭流
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

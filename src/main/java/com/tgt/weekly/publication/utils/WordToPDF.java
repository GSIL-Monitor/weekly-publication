package com.tgt.weekly.publication.utils;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2019/1/31
 * @Time: 10:11
 * @Description: To change this template use File | Settings | File Templates.
 **/
public class WordToPDF {

    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = WordToPDF.class.getClassLoader().getResourceAsStream("license.xml");
            License lic = new License();
            lic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void doc2pdf(String inPath, String outPath) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
//        if (!getLicense()) {
//            return;
//        }
        try {
            long old = System.currentTimeMillis();
            // 新建一个空白pdf文档
            File file = new File(outPath);
            FileOutputStream os = new FileOutputStream(file);
            // Address是将要被转化的word文档
            Document doc = new Document(inPath);
            // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            doc.save(os, SaveFormat.PDF);
            // EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            // 转化用时
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

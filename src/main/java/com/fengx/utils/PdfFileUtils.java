package com.fengx.utils;

import com.fengx.view.AsianFontProvider;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;
import java.nio.charset.Charset;

/**
 * 生成PDF文件
 */
public class PdfFileUtils {
    public static void savePdf(OutputStream out,String css, String html) {
        Document document = new Document(PageSize.A4, 90, 90, 70, 70);
        //Document document = new Document(PageSize.A4, 110, 110, 120, 140);
        try {
            // pdf 实例化页眉页脚
            PDFPageBuilderUtil pdfPageBuilderUtil = new PDFPageBuilderUtil();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            // 将页眉页脚写入pdf文档
            writer.setPageEvent(pdfPageBuilderUtil);
            document.open();
            // 将html加入css样式转为pdf文档
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(html.getBytes()), new ByteArrayInputStream(css.getBytes()), Charset.forName("UTF-8"), new AsianFontProvider());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public static void saveChinesePdf(OutputStream out, String html) {
        Document document = new Document(PageSize.A4, 50, 50, 60, 60);
        //Document document = new Document(PageSize.A4, 110, 110, 120, 140);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(html.getBytes()), Charset.forName("UTF-8"), new AsianFontProvider() );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public static String getURLSource(File url) throws Exception {
        InputStream inStream = new FileInputStream(url);
        // 通过输入流获取html二进制数据
        // 把二进制数据转化为byte字节数据
        byte[] data = readInputStream(inStream);
        String htmlSource = new String(data);

        inStream.close();
        return htmlSource;
    }

    /**
     * 把二进制数据转化为byte字节数据
     * @param instream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream instream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1204];
        int len = 0;
        while ((len = instream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        instream.close();
        return outStream.toByteArray();
    }
}

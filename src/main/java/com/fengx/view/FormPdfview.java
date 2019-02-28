package com.fengx.view;

import com.fengx.utils.FreemarkerUtils;
import com.fengx.utils.PdfFileUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * pdf文档转换
 */
public class FormPdfview extends AbstractITextPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document, PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        URL fileResource = FormPdfview.class.getResource("/templates");
        // 将ftl模板转成html字符串
        String html = FreemarkerUtils.loadFtlHtml(new File(fileResource.getFile()), "simpleForm.ftl", model);
        // 读取css样式
        String cssPath = FormPdfview.class.getResource("/css/temp.css").getPath();
        String cssFile = PdfFileUtils.getURLSource(new File(cssPath));
        // 将html加入css样式转为pdf文档
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(html.getBytes()), new ByteArrayInputStream(cssFile.getBytes()), Charset.forName("UTF-8"), new AsianFontProvider());
    }


    public static void generate(String htmlStr, OutputStream out)
            throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        org.w3c.dom.Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes()));
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(doc, null);
        renderer.layout();
        renderer.createPDF(out);
        out.close();
    }
}

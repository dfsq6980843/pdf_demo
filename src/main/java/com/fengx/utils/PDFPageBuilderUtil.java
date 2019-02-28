package com.fengx.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * pdf 页眉页脚
 */
public class PDFPageBuilderUtil extends PdfPageEventHelper {

    /**
     * 基础字体
     */
    private static BaseFont baseFont;
    /**
     * 生成下划线空白占位符
     */
    private static String Blank;
    /**
     * 页眉字体
     */
    private static Font font;
    /**
     * 下划线字体
     */
    private static Phrase blankPhrase;

    /**
     * 字体地址
     */
    public static final String FONT_PATH = PDFPageBuilderUtil.class.getResource("/fonts/huawenkaiti.ttf").getPath();

    static {
        try {
            // 中文字体依赖itext得itext-asian包
            // 设置页眉页脚的基础字体 华文楷体
            baseFont = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            // 绘制页眉横线
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 140; i++) {
                sb.append("\u00a0");
            }
            Blank = sb.toString();
            font = new Font(baseFont, 8, Font.UNDEFINED);
            blankPhrase = new Phrase(Blank, new Font(baseFont, Font.DEFAULTSIZE, Font.UNDERLINE));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * logo地址
     */
    private static final String IMG_PATH = PDFPageBuilderUtil.class.getResource("/img/huaxinglogo.jpg").getPath();

    /**
     * pdf单页写入后触发事件
     * @param writer
     * @param document
     */
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        int yMargin = -20;
        float top = document.top(yMargin);
        //生成下划线，使用空格占位
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_LEFT, blankPhrase,
                document.left(-1), top, 0);
        //生成左侧页眉
        //华兴资本logo图片
        Image image;
        try {
            image = Image.getInstance(IMG_PATH);
            PdfContentByte pdfContentByte = writer.getDirectContentUnder();
            pdfContentByte.beginText();
            // 设置页眉图片的x,y坐标
            image.setAbsolutePosition(100, 790);
            // 设置图片缩放比例
            image.scalePercent(35);
            pdfContentByte.addImage(image);
            pdfContentByte.endText();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 生成右侧页眉
        // 当天日期
        String date = "Date: " + new SimpleDateFormat("YYYY-MM-dd").format(new Date());
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_RIGHT, new Phrase(date, font),
                document.right() - 10, top, 0);
        // 生成页脚页数
        String pageStart = "Page " + writer.getPageNumber() + " of ";
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_RIGHT, new Phrase(pageStart, font),
                document.right(), document.bottom(yMargin), 0);
        // 生成页脚总页数
        PdfContentByte pageNum = writer.getDirectContent();
        pageNum.addTemplate(total, document.right(), document.bottom(yMargin));

    }

    /**
     * pdf模板 用来设置总页码
     */
    public PdfTemplate total;

    /**
     * 设置pdf模板初始化数据
     * @param writer
     * @param document
     */
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(50, 50);
    }

    /**
     * 文档关闭前触发事件
     * @param writer
     * @param document
     */
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        int yMargin = -20;
        total.beginText();
        // 生成的模版的字体、颜色
        total.setFontAndSize(baseFont, 8);
        String foot2 = "  " + (writer.getPageNumber());
        // 模版显示的内容
        total.showText(foot2);
        total.endText();
        total.closePath();
    }
}

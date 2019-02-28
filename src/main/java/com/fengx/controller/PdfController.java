package com.fengx.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fengx.utils.FreemarkerUtils;
import com.fengx.utils.JsonFileUtil;
import com.fengx.utils.PdfFileUtils;
import com.fengx.view.FormPdfview;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Resource
    private PdfTemplate pdfTemplate;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public void createPDF(HttpServletResponse httpServletResponse) {
        Map<String, Object> model = new HashMap<>();
        model.put("project", pdfTemplate.getProjectName());
        model.put("time", pdfTemplate.getTime());
        model.put("participantA", pdfTemplate.getParticipantA());
        model.put("participantB", pdfTemplate.getParticipantB());
        model.put("address", pdfTemplate.getAddress());
        model.put("scoreTeam", pdfTemplate.getScoreTeam());
        model.put("scoreMarket", pdfTemplate.getScoreMarket());
        model.put("scoreProduct", pdfTemplate.getScoreProduct());
        model.put("scoreValuation", pdfTemplate.getScoreValuation());
        String json = JsonFileUtil.readJsonFile(this.getClass().getResource("/data/content.json").getPath());
        JSONArray jsonArray = JSONObject.parseArray(json);
        model.put("children", jsonArray);
        URL fileResource = FormPdfview.class.getResource("/templates");
        // 将ftl模板转成html字符串
        String html = FreemarkerUtils.loadFtlHtml(new File(fileResource.getFile()), "simpleForm.ftl", model);
        // 读取css样式
        String cssPath = FormPdfview.class.getResource("/css/template.css").getPath();
        try {
            String cssFile = PdfFileUtils.getURLSource(new File(cssPath));
            PdfFileUtils.savePdf(httpServletResponse.getOutputStream(), cssFile, html);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    @Component
    class PdfTemplate{
        @Value("${huaxing.call-report.project}")
        private String projectName;
        @Value("${huaxing.call-report.time}")
        private String time;
        @Value("${huaxing.call-report.address}")
        private String address;
        @Value("${huaxing.call-report.participant.a}")
        private String participantA;
        @Value("${huaxing.call-report.participant.b}")
        private String participantB;
        @Value("${huaxing.call-report.resource}")
        private String resource;
        @Value("${huaxing.call-report.score.team}")
        private String scoreTeam;
        @Value("${huaxing.call-report.score.market}")
        private String scoreMarket;
        @Value("${huaxing.call-report.score.product}")
        private String scoreProduct;
        @Value("${huaxing.call-report.score.valuation}")
        private String scoreValuation;
    }
}

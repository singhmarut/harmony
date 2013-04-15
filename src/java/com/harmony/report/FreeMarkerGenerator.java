package com.harmony.report;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: marut
 * Date: 13/04/13
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class FreeMarkerGenerator {
    public String generateReport(Map templateValues){
        String finalReport = "";
        try{
            File templateFile=new File("../ftl/candidateReport.ftl");
            Configuration cfg = new Configuration();
            cfg.setDirectoryForTemplateLoading(templateFile.getParentFile());
            Template template = cfg.getTemplate(templateFile.getName());
            StringWriter stringWriter = new StringWriter();
            template.process(templateValues, stringWriter);
            finalReport = stringWriter.toString();

        }catch (Exception ex){
            String s = ex.getMessage();
        }
        return finalReport;
    }
}

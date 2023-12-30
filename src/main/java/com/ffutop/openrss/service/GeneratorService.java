package com.ffutop.openrss.service;

import com.ffutop.openrss.model.Opml;
import com.ffutop.openrss.model.Rss;
import com.ffutop.openrss.util.DateUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ffutop
 * @since 2023-12-08
 */
public class GeneratorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorService.class);

    private static final String htmlFileName = "publish/index.html";

    public void generateHtml(Opml opml, List<Rss.Item> rssItemList) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTimeZone(TimeZone.getDefault());
        configuration.setClassForTemplateLoading(GeneratorService.class, "/templates");
        Template template = null;
        try {
            template = configuration.getTemplate("template.html.ftl");
        } catch (IOException iox) {
            LOGGER.error("getTemplate failed, abort generate html", iox);
            return;
        }

        Map<String, Object> data = prepareData(opml, rssItemList);

        try (FileWriter fw = new FileWriter(htmlFileName)) {
            template.process(data, fw);
            fw.flush();
        } catch (IOException iox) {
            LOGGER.error("create FileWriter({}) failed, abort generate html", htmlFileName, iox);
        } catch (TemplateException tx) {
            LOGGER.error("process template failed, abort generate html", tx);
        }
    }

    private Map<String, Object> prepareData(Opml opml, List<Rss.Item> rssItemList) {
        final String A_WEEK_AGO = DateUtil.A_WEEK_AGO();

        Map<String, Object> data = new HashMap<>();
        data.put("BLOGS_COUNT", opml.getBody().getOutlineList().size());
        rssItemList = rssItemList.stream()
                .sorted((x,y)->-x.getPubDate().compareTo(y.getPubDate()))
                .filter(x->x.getPubDate().compareTo(A_WEEK_AGO) > 0)
                .collect(Collectors.toList());
        data.put("BLOG_ITEMS", rssItemList);
        return data;
    }
}

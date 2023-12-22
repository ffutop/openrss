package com.ffutop.openrss;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ffutop.openrss.model.Opml;
import com.ffutop.openrss.model.Rss;
import com.ffutop.openrss.service.GeneratorService;
import com.ffutop.openrss.service.RssService;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ffutop
 * @since 2023-12-06
 */
public class Main {

    private static RssService rssService = new RssService();
    private static GeneratorService generatorService = new GeneratorService();

    public static void main(String[] args) throws IOException, ParseException {
        XmlMapper xmlMapper = XmlMapper.xmlBuilder().build();
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream("ffutop.opml")) {
            Opml opml = xmlMapper.readValue(is, Opml.class);
            List<Rss> rssList = rssService.crawlOpml(opml);
            List<Rss.Item> rssItemList = rssList.stream().flatMap(rss -> rss.getChannel().getItemList().stream()).collect(Collectors.toList());
            generatorService.generateHtml(opml, rssItemList);
        }

        System.out.println("Hello world!");
    }
}
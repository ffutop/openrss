package com.ffutop.openrss.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ffutop.openrss.model.Opml;
import com.ffutop.openrss.model.Rss;
import com.ffutop.openrss.util.DateUtil;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ffutop
 * @since 2023-12-07
 */
public class RssService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RssService.class);

    private HttpClient client = HttpClients.createDefault();

    private XmlMapper xmlMapper = XmlMapper.xmlBuilder().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).build();

    /**
     * Crawls the specified RSS feed URI and returns the RSS object.
     *
     * @param  uri  The URI of the RSS feed to crawl.
     * @return      The RSS object representing the crawled feed, or null if an error occurs.
     */
    public Rss crawlRss(String uri) {
        ClassicHttpRequest request = ClassicRequestBuilder.get(uri).build();
        try {
            LOGGER.info("crawl rss.xml from {}.", uri);
            return client.execute(request, (response) -> xmlMapper.readValue(response.getEntity().getContent(), Rss.class));
        } catch (IOException iox) {
            LOGGER.error("crawl rss.xml from {} failed.", uri, iox);
            return null;
        } finally {
            LOGGER.info("finish crawl rss.xml from {}.", uri);
        }
    }

    public List<Rss> crawlOpml(Opml opml) {
        return opml.getBody().getOutlineList().stream().map(outline -> {
            Rss rss = crawlRss(outline.getXmlUrl());

            if (rss != null && rss.getChannel() != null && !rss.getChannel().getItemList().isEmpty()) {
                // rewrite author
                rss.getChannel().getItemList().forEach(item -> {
                    if (item.getAuthor() == null) {
                        item.setAuthor(outline.getTitle());
                    } else if (!item.getAuthor().equals(outline.getTitle())) {
                        item.setAuthor(String.format("%s(%s)", outline.getText(), item.getAuthor()));
                    }
                });

                // rewrite pubDate
                rss.getChannel().getItemList().forEach(item -> {
                    Date pubDate = DateUtil.parseFromRFC822Datetime(item.getPubDate());
                    item.setPubDate(DateUtil.formatAsRFC3339Date(pubDate));
                });
                return rss;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

}

package com.ffutop.openrss.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * @author ffutop
 * @since 2023-12-06
 */
@JacksonXmlRootElement()
public class Opml {

    @JacksonXmlProperty(isAttribute = true)
    private String version;

    @JacksonXmlProperty
    private Head head;

    @JacksonXmlProperty
    private Body body;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public static class Head {
        @JacksonXmlProperty
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class Body {
        @JacksonXmlProperty(localName = "outline")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Outline> outlineList;

        public List<Outline> getOutlineList() {
            return outlineList;
        }

        public void setOutlineList(List<Outline> outlineList) {
            this.outlineList = outlineList;
        }
    }

    public static class Outline {

        @JacksonXmlProperty(isAttribute = true)
        private String title;

        @JacksonXmlProperty(isAttribute = true)
        private String text;

        @JacksonXmlProperty(isAttribute = true)
        private String type;

        @JacksonXmlProperty(isAttribute = true)
        private String xmlUrl;

        @JacksonXmlProperty(isAttribute = true)
        private String htmlUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getXmlUrl() {
            return xmlUrl;
        }

        public void setXmlUrl(String xmlUrl) {
            this.xmlUrl = xmlUrl;
        }

        public String getHtmlUrl() {
            return htmlUrl;
        }

        public void setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
        }
    }
}

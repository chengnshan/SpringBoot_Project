package com.cxp.springbootmybatis.principle.utils;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

/**
 * @author 程
 * @date 2019/7/7 下午6:02
 */
public class DOMUtils {

    public static Document parseXMLDocument(String content) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);

        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(new ByteArrayInputStream(content.getBytes()));
            return document;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

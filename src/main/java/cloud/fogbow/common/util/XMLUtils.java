package cloud.fogbow.common.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import cloud.fogbow.common.exceptions.ConfigurationErrorException;

public class XMLUtils {
    
    public static Element getRootNodeFromXMLFile(File xmlFile) throws ConfigurationErrorException {
        SAXBuilder builder = new SAXBuilder();
        Document document = buildDocumentFromFile(xmlFile, builder);
        Element rootNode = document.getRootElement();
        return rootNode;
    }
    
    private static Document buildDocumentFromFile(File xmlFile, SAXBuilder builder) throws ConfigurationErrorException {
        Document document = null;
        try {
            document = builder.build(xmlFile);
        } catch (JDOMException | IOException e) {
            // TODO add message
            throw new ConfigurationErrorException();
        }
        return document;
    }

    public static Element getRootNodeFromXMLString(String xmlString) throws ConfigurationErrorException {
        SAXBuilder builder = new SAXBuilder();
        Document document = buildDocument(xmlString, builder);
        Element rootNode = document.getRootElement();
        return rootNode;
    }
    
    private static Document buildDocument(String xmlString, SAXBuilder builder) throws ConfigurationErrorException {
        Document document = null;
        try {
            InputSource is = new InputSource(new StringReader(xmlString));
            document = builder.build(is);
        } catch (JDOMException | IOException e) {
            // TODO add message
            throw new ConfigurationErrorException();
        }
        return document;
    }
}

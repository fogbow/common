package cloud.fogbow.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.InputSource;

import cloud.fogbow.common.constants.Messages;
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
            throw new ConfigurationErrorException(String.format(Messages.Exception.ERROR_WHILE_READING_XML_FILE, 
                    xmlFile.getAbsolutePath(), e.getMessage()));
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
            throw new ConfigurationErrorException(String.format(Messages.Exception.ERROR_WHILE_PARSING_XML_STRING, 
                    e.getMessage()));
        }
        return document;
    }
    
    public static void writeXMLToFile(Element root, String fileName) throws ConfigurationErrorException {
        try {
            Document doc = new Document(root);
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(fileName));
        } catch (IOException e) {
            throw new ConfigurationErrorException(String.format(Messages.Exception.ERROR_WHILE_WRITING_XML_TO_FILE, 
                    fileName, e.getMessage()));
        }
    }
}

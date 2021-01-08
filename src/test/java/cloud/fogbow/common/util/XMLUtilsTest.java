package cloud.fogbow.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.jdom2.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cloud.fogbow.common.exceptions.ConfigurationErrorException;

public class XMLUtilsTest {

    private String rootName = "root";
    private String element1Name = "element1";
    private String element2Name = "element2";
    private String attribute1Text = "attribute1";
    private String attribute2Text = "attribute2";
    private String xmlFile = "src/test/resource/test.xml";
    
    @Before
    public void setUp() throws IOException {
        deleteTestFiles();
    }
    
    @After
    public void tearDown() throws IOException {
        deleteTestFiles();
    }
    
    @Test
    public void testReadAndWriteXML() throws ConfigurationErrorException {
        assertFalse(fileExists(xmlFile));
        
        Element root = new Element(rootName);
        
        Element attribute1 = new Element(element1Name);
        attribute1.setText(attribute1Text);
        Element attribute2 = new Element(element2Name);
        attribute2.setText(attribute2Text);
        
        root.addContent(attribute1);
        root.addContent(attribute2);
        
        
        XMLUtils.writeXMLToFile(root, xmlFile);
        
        assertTrue(fileExists(xmlFile));
        
        
        File fileToRead = new File(xmlFile);
        
        Element newRoot = XMLUtils.getRootNodeFromXMLFile(fileToRead);
        assertEquals(rootName, newRoot.getName());
        
        List<Element> newChildren = newRoot.getChildren();
        assertEquals(2, newChildren.size());
        assertEquals(attribute1Text, newChildren.get(0).getText());
        assertEquals(element1Name, newChildren.get(0).getName());
        assertEquals(attribute2Text, newChildren.get(1).getText());
        assertEquals(element2Name, newChildren.get(1).getName());
    }
    
    private void deleteTestFiles() throws IOException {
        File file = new File(xmlFile);
        Files.deleteIfExists(file.toPath());
    }
    
    private boolean fileExists(String fileName) {
        File file = new File(fileName);
        return Files.exists(file.toPath());
    }
}

package com.opengisviewer.opengisviewer.utils;

import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;

@Slf4j
public class XMLUtils {

    /**
     *  validates xml content from an input stream against a xsd found in the resources directory
     * @param xsdPath xsd path in the resources directory to be validated against.
     * @param xmlStream XML content to validate.
     * @return validate status of provided xmlStream against the xsd
     */
    public static boolean validateXMLSchema(String xsdPath, InputStream xmlStream){
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlStream));
        } catch (IOException | SAXException e) {
            log.debug("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }
}

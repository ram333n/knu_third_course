package org.example.xml;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public final class XsdValidator {
    private XsdValidator() {}

    public static boolean validate(String pathToXml, String pathToXsd) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(pathToXsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(pathToXml));
            return true;
        } catch (SAXException | IOException e) {
            return false;
        }
    }
}

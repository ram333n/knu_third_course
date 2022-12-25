package org.example.validators;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XSDValidator {
    public boolean validate(String pathToXML, String pathToXSD) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(pathToXSD));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(pathToXML));
        } catch (SAXException | IOException e) {
            return false;
        }

        return true;
    }
}

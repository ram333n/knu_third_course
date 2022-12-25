package org.example.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ParsingErrorHandler implements ErrorHandler {

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        System.out.println(generateErrorMessage(exception));
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        System.out.println(generateErrorMessage(exception));
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        System.out.println(generateErrorMessage(exception));
    }

    private String generateErrorMessage(SAXParseException exception) {
        int line = exception.getLineNumber();
        String exceptionMessage = exception.getMessage();
        return String.format("Line %d : %n%s%n", line, exceptionMessage);
    }
}

package org.example.parsers;

import org.example.classes.Flower;
import org.example.handlers.OrangeryHandler;
import org.example.util.PathBuilder;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class CustomSAXParser implements Parser {
    @Override
    public List<Flower> parse(String pathToXML) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            OrangeryHandler handler = new OrangeryHandler();
            saxParser.parse(pathToXML, handler);

            return handler.getOrangery();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Parser parser = new CustomSAXParser();
        System.out.println(parser.parse(PathBuilder.getPath(".", "src", "main", "resources", "input.xml")));
    }
}

package org.example.parsers;

import org.example.classes.Flower;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class CustomStAXParser implements Parser {
    @Override
    public List<Flower> parse(String pathToXML) {
//        try {
//            XMLInputFactory factory = XMLInputFactory.newInstance();
//            XMLEventReader reader = factory.createXMLEventReader(new FileInputStream(pathToXML));
//
//            while(reader.hasNext()) {
//                XMLEvent event = reader.nextEvent();
//                if(event.isStartElement()) {
//                    StartElement startElement = event.asStartElement();
//                    String qName = startElement.getName().getLocalPart();
//                }
//            }
//        } catch (XMLStreamException | FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
    }
}

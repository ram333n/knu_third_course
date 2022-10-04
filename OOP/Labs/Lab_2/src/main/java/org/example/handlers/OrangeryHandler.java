package org.example.handlers;

import org.example.classes.*;
import org.example.util.XMLTags;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class OrangeryHandler extends DefaultHandler {
    private List<Flower> orangery;
    private String elementValue;

    public OrangeryHandler() {
        this.orangery = new ArrayList<>();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }

    @Override
    public void startDocument() throws SAXException {
        this.orangery = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        instantiateSubclass(qName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        setField(qName, elementValue);
    }

    public void addDataByDOMElement(Element element) {
        if(orangery == null) {
            orangery = new ArrayList<>();
        }

        orangery.add(new Flower());

        setField("id", element.getElementsByTagName("id").item(0).getTextContent());
        setField("name", element.getElementsByTagName("name").item(0).getTextContent());
        setField("soil", element.getElementsByTagName("soil").item(0).getTextContent());
        setField("origin", element.getElementsByTagName("origin").item(0).getTextContent());

        instantiateSubclass("visualParameters");

        setField("stemColor", element.getElementsByTagName("stemColor").item(0).getTextContent());
        setField("leavesColor", element.getElementsByTagName("leavesColor").item(0).getTextContent());
        setField("averageSize", element.getElementsByTagName("averageSize").item(0).getTextContent());

        instantiateSubclass("growingTips");

        setField("temperature", element.getElementsByTagName("temperature").item(0).getTextContent());
        setField("isLightLoving", element.getElementsByTagName("isLightLoving").item(0).getTextContent());
        setField("waterAmount", element.getElementsByTagName("waterAmount").item(0).getTextContent());

        setField("multiplying", element.getElementsByTagName("multiplying").item(0).getTextContent());
    }

    private void instantiateSubclass(String qName) {
        switch (qName) {
            case XMLTags.FLOWER ->
                    orangery.add(new Flower());

            case XMLTags.VISUAL_PARAMETERS ->
                    getLast().setVisualParameters(new VisualParameters());

            case XMLTags.GROWING_TIPS ->
                    getLast().setGrowingTips(new GrowingTips());
        }
    }

    public void setField(String qName, String value) {
        switch (qName) {
            case XMLTags.ID ->
                    getLast().setId(Integer.parseInt(value));

            case XMLTags.NAME ->
                    getLast().setName(value);

            case XMLTags.SOIL ->
                    getLast().setSoil(Soil.valueOf(value));

            case XMLTags.ORIGIN ->
                    getLast().setOrigin(value);

            case XMLTags.STEM_COLOR ->
                    getLast().getVisualParameters().setStemColor(value);

            case XMLTags.LEAVES_COLOR ->
                    getLast().getVisualParameters().setLeavesColor(value);

            case XMLTags.AVERAGE_SIZE ->
                    getLast().getVisualParameters().setAverageSize(Double.parseDouble(value));

            case XMLTags.TEMPERATURE ->
                    getLast().getGrowingTips().setTemperature(Integer.parseInt(value));

            case XMLTags.IS_LIGHT_LOVING ->
                    getLast().getGrowingTips().setLightLoving(Boolean.parseBoolean(value));

            case XMLTags.WATER_AMOUNT ->
                    getLast().getGrowingTips().setWaterAmount(Integer.parseInt(value));

            case XMLTags.MULTIPLYING ->
                    getLast().setMultiplying(Multiplying.valueOf(value));
        }
    }
    
    public List<Flower> getOrangery() {
        return new ArrayList<>(orangery);
    }

    private Flower getLast() {
        return orangery.get(orangery.size() - 1);
    }
}

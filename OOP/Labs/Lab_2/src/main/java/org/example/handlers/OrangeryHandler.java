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
        switch (qName) {
            case XMLTags.FLOWER ->
                orangery.add(new Flower());

            case XMLTags.VISUAL_PARAMETERS ->
                getLast().setVisualParameters(new VisualParameters());

            case XMLTags.GROWING_TIPS ->
                getLast().setGrowingTips(new GrowingTips());
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case XMLTags.ID ->
                getLast().setId(Integer.parseInt(elementValue));

            case XMLTags.NAME ->
                getLast().setName(elementValue);

            case XMLTags.SOIL ->
                getLast().setSoil(Soil.valueOf(elementValue));

            case XMLTags.ORIGIN ->
                getLast().setOrigin(elementValue);

            case XMLTags.STEM_COLOR ->
                getLast().getVisualParameters().setStemColor(elementValue);

            case XMLTags.LEAVES_COLOR ->
                getLast().getVisualParameters().setLeavesColor(elementValue);

            case XMLTags.AVERAGE_SIZE ->
                getLast().getVisualParameters().setAverageSize(Double.parseDouble(elementValue));

            case XMLTags.TEMPERATURE ->
                getLast().getGrowingTips().setTemperature(Integer.parseInt(elementValue));

            case XMLTags.IS_LIGHT_LOVING ->
                getLast().getGrowingTips().setLightLoving(Boolean.parseBoolean(elementValue));

            case XMLTags.WATER_AMOUNT ->
                getLast().getGrowingTips().setWaterAmount(Integer.parseInt(elementValue));

            case XMLTags.MULTIPLYING ->
                getLast().setMultiplying(Multiplying.valueOf(elementValue));
        }
    }

    public void addDataByDOMElement(Element element) {
        if(orangery == null) {
            orangery = new ArrayList<>();
        }

        Flower flower = new Flower();

        flower.setId(Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()));
        flower.setName(element.getElementsByTagName("name").item(0).getTextContent());
        flower.setSoil(Soil.valueOf(element.getElementsByTagName("soil").item(0).getTextContent()));
        flower.setOrigin(element.getElementsByTagName("origin").item(0).getTextContent());

        flower.setVisualParameters(new VisualParameters());

        flower.getVisualParameters().setStemColor(
                element.getElementsByTagName("stemColor").item(0).getTextContent());
        flower.getVisualParameters().setLeavesColor(
                element.getElementsByTagName("leavesColor").item(0).getTextContent());
        flower.getVisualParameters().setAverageSize(
                Double.parseDouble(element.getElementsByTagName("averageSize").item(0).getTextContent()));

        flower.setGrowingTips(new GrowingTips());

        flower.getGrowingTips().setTemperature(
                Integer.parseInt(element.getElementsByTagName("temperature").item(0).getTextContent()));
        flower.getGrowingTips().setLightLoving(
                Boolean.parseBoolean(element.getElementsByTagName("isLightLoving").item(0).getTextContent()));
        flower.getGrowingTips().setTemperature(
                Integer.parseInt(element.getElementsByTagName("waterAmount").item(0).getTextContent()));

        flower.setMultiplying(Multiplying.valueOf(
                element.getElementsByTagName("multiplying").item(0).getTextContent()));

        orangery.add(flower);
    }
    
    public List<Flower> getOrangery() {
        return new ArrayList<>(orangery);
    }

    private Flower getLast() {
        return orangery.get(orangery.size() - 1);
    }
}

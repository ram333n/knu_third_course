package org.example.parsers;

import org.example.classes.*;
import org.example.util.XMLTags;
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
                getLast().setId(Integer.parseInt(elementValue.toString()));

            case XMLTags.NAME ->
                getLast().setName(elementValue.toString());

            case XMLTags.SOIL ->
                getLast().setSoil(Soil.valueOf(elementValue.toString()));

            case XMLTags.ORIGIN ->
                getLast().setOrigin(elementValue.toString());

            case XMLTags.STEM_COLOR ->
                getLast().getVisualParameters().setStemColor(elementValue.toString());

            case XMLTags.LEAVES_COLOR ->
                getLast().getVisualParameters().setLeavesColor(elementValue.toString());

            case XMLTags.AVERAGE_SIZE ->
                getLast().getVisualParameters().setAverageSize(Double.parseDouble(elementValue.toString()));

            case XMLTags.TEMPERATURE ->
                getLast().getGrowingTips().setTemperature(Integer.parseInt(elementValue.toString()));

            case XMLTags.IS_LIGHT_LOVING ->
                getLast().getGrowingTips().setLightLoving(Boolean.parseBoolean(elementValue.toString()));

            case XMLTags.WATER_AMOUNT ->
                getLast().getGrowingTips().setWaterAmount(Integer.parseInt(elementValue.toString()));

            case XMLTags.MULTIPLYING ->
                getLast().setMultiplying(Multiplying.valueOf(elementValue.toString()));
        }
    }

    public List<Flower> getOrangery() {
        return new ArrayList<>(orangery);
    }

    private Flower getLast() {
        return orangery.get(orangery.size() - 1);
    }
}

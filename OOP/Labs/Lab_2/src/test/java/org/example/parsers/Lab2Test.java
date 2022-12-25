package org.example.parsers;

import org.example.classes.*;
import org.example.util.PathBuilder;
import org.example.validators.XSDValidator;
import org.junit.jupiter.api.Test;

import javax.xml.parsers.SAXParser;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Lab2Test {
    private static final String PATH_TO_XML = PathBuilder.getPath(".", "src", "main", "resources", "input.xml");
    private static final String PATH_TO_XSD = PathBuilder.getPath(".", "src", "main", "resources", "schema.xsd");
    private static final String PATH_TO_INVALID_XSD = PathBuilder.getPath(".", "src", "main", "resources", "invalid_schema.xsd");
    private static final List<Flower> EXPECTED_SORTED_ORANGERY; //sorted by averageSize
    private static final Comparator<Flower> COMPARATOR = Comparator.comparingDouble(flower -> flower.getVisualParameters().getAverageSize());

    static {
        EXPECTED_SORTED_ORANGERY = new ArrayList<>();

        EXPECTED_SORTED_ORANGERY.add(new Flower(
                2,
                "Viola",
                Soil.SOD_PODZOLIC,
                "Italy",
                new VisualParameters("Green", "Violet", 20),
                new GrowingTips(20, true, 4000),
                Multiplying.LEAVES
        ));

        EXPECTED_SORTED_ORANGERY.add(new Flower(
                1,
                "Tulip",
                Soil.PODZOLIC,
                "Netherlands",
                new VisualParameters("Green", "Yellow", 39.75),
                new GrowingTips(20, true, 5250),
                Multiplying.SEEDS
        ));

        EXPECTED_SORTED_ORANGERY.add(new Flower(
                3,
                "Hydrangea",
                Soil.GROUND,
                "Asia",
                new VisualParameters("Green", "Blue", 45),
                new GrowingTips(50, false, 3000),
                Multiplying.CUTTING
        ));

        EXPECTED_SORTED_ORANGERY.add(new Flower(
                0,
                "Rose",
                Soil.GROUND,
                "Europe",
                new VisualParameters("Green", "Red", 45.5),
                new GrowingTips(15, true, 5000),
                Multiplying.SEEDS
        ));
    }

    private static void testParsing(Parser parser) {
        List<Flower> orangery = parser.parse(PATH_TO_XML);
        orangery.sort(COMPARATOR);
        assertEquals(EXPECTED_SORTED_ORANGERY, orangery);
    }

    @Test
    void testSAXParser() {
        testParsing(new CustomSAXParser());
    }

    @Test
    void testDOMParser() {
        testParsing(new CustomDOMParser());
    }

    @Test
    void testStAXParser() {
        testParsing(new CustomStAXParser());
    }

    @Test
    void testValidation() {
        XSDValidator validator = new XSDValidator();
        assertTrue(validator.validate(PATH_TO_XML, PATH_TO_XSD));
        assertFalse(validator.validate(PATH_TO_XML, PATH_TO_INVALID_XSD));
    }
}
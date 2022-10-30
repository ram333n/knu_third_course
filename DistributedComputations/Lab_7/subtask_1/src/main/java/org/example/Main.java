package org.example;

import org.example.manager.Manager;
import org.example.xml.DomParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Manager manager = DomParser.parse(Constants.PATH_TO_XML);
        //delete PSG players
        manager.deletePlayer(UUID.fromString("fb9fbe92-32d0-47cb-8a0f-35d28ab1f247"));
        manager.deletePlayer(UUID.fromString("d2264520-8f70-4527-9c3e-6db4b382287b"));

        //delete Shakhtar
        manager.deleteTeam(UUID.fromString("98abbc6a-3a6c-49b5-b65d-703e4ae212be"));

        //add to PSG
        manager.addPlayerToTeam(UUID.fromString("8cae924b-e7c4-4c0a-9772-f506ae999bb8"), "Neymar", BigDecimal.valueOf(20320));

        //Update Bushan
        manager.updatePlayer(UUID.fromString("6779598e-a429-494d-9518-457fb8194009"),
                UUID.fromString("b720affc-3030-4974-a13c-11887683aca8"),
                "Buschan Georgy",
                BigDecimal.ONE);

        //Add Milano team
        manager.addTeam("Milano", "Italy");

        //Update Dynamo
        manager.updateTeam(UUID.fromString("b720affc-3030-4974-a13c-11887683aca8"),
                "Dynamo Kyiv",
                "Ukraine");

        //Delete Besedin
        manager.deletePlayer(UUID.fromString("41ac24c0-e33f-4be5-a69d-763ee7978943"));

        //Save
        DomParser.serialize(manager, Constants.PATH_TO_XML);
    }
}
package org.example.xml;

import org.example.Constants;
import org.example.manager.Manager;
import org.example.model.Player;
import org.example.model.Team;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DomParser {
    public static Manager parse(String pathToXml)
            throws ParserConfigurationException, IOException, SAXException {
        SchemaFactory schemaFactory =
                SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        Schema schema = schemaFactory.newSchema(new File(Constants.PATH_TO_XSD));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setSchema(schema);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new ParsingErrorHandler());
        Document document = builder.parse(new File(pathToXml));
        document.getDocumentElement().normalize();

        List<Team> teams = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        NodeList teamNodes = document.getElementsByTagName(Constants.TEAM);
        NodeList playerNodes = document.getElementsByTagName(Constants.PLAYER);

        for (int i = 0; i < teamNodes.getLength(); i++) {
            Element node = (Element) teamNodes.item(i);

            Team team = new Team();
            team.setId(UUID.fromString(node.getAttribute(Constants.ID)));
            team.setName(node.getAttribute(Constants.NAME));
            team.setCountry(node.getAttribute(Constants.COUNTRY));

            teams.add(team);
        }

        for (int i = 0; i < playerNodes.getLength(); i++) {
            Element node = (Element) playerNodes.item(i);

            Player player = new Player();
            player.setId(UUID.fromString(node.getAttribute(Constants.ID)));
            player.setTeamId(UUID.fromString(node.getAttribute(Constants.TEAM_ID)));
            player.setName(node.getAttribute(Constants.NAME));
            player.setPrice(new BigDecimal(node.getAttribute(Constants.PRICE)));

            players.add(player);
        }

        return new Manager(teams, players);
    }

    public static void serialize(Manager manager, String pathToXml)
            throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element root = document.createElement(Constants.MANAGER);
        document.appendChild(root);

        List<Team> teams = manager.getListOfTeams();
        for (Team team : teams) {
            Element teamNode = document.createElement(Constants.TEAM);
            teamNode.setAttribute(Constants.ID, team.getId().toString());
            teamNode.setAttribute(Constants.NAME, team.getName());
            teamNode.setAttribute(Constants.COUNTRY, team.getCountry());
            root.appendChild(teamNode);

            UUID teamId = team.getId();
            List<Player> playersOfTeam = manager.getListOfPlayersOfTeam(teamId);
            for (Player player : playersOfTeam) {
                Element playerNode = document.createElement(Constants.PLAYER);
                playerNode.setAttribute(Constants.ID, player.getId().toString());
                playerNode.setAttribute(Constants.TEAM_ID, player.getTeamId().toString());
                playerNode.setAttribute(Constants.NAME, player.getName());
                playerNode.setAttribute(Constants.PRICE, player.getPrice().toString());

                teamNode.appendChild(playerNode);
            }
        }

        Source domSource = new DOMSource(document);
        Result result = new StreamResult(new File(pathToXml));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(domSource, result);
    }
}

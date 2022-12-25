package org.example;

import org.example.client.Client;
import org.example.model.Player;
import org.example.model.Team;

import java.io.IOException;
import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) throws IOException {
        Client client = new Client(Constants.HOST, Constants.PORT);
        try {
            client.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Connected");

        client.deletePlayer(9L);
        client.deletePlayer(10L);

        //Delete Shakhtar
        client.deleteTeam(1L);

        //Add to PSG
        client.insertPlayer(new Player(null, 4L, "Neymar", BigDecimal.valueOf(20320)));

        //Update Bushan
        client.updatePlayer(new Player(1L, 2L, "Georgy", BigDecimal.ONE));

        //Add Milano
        client.insertTeam(new Team(null, "Milano", "Italy"));

        //Delete Besedin
        client.deletePlayer(3L);

        //Move Cortois to Dynamo
        client.moveToAnotherTeam(8L, 2L);

        //Real players
        System.out.println(client.findPlayersByTeamName("Real Madrid"));

        //All teams
        System.out.println(client.findAllTeams());

        client.disconnect();
    }
}
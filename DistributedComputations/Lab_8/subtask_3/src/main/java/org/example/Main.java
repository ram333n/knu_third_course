package org.example;

import org.example.client.Client;
import org.example.model.Player;
import org.example.model.Team;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        try (Client client = new Client(Constants.HOST, Constants.PORT)) {
            client.connect();
            System.out.println(client.insertTeam(new Team(null, "Juventus", "Italy")));
//            System.out.println(client.insertPlayer(new Player(null, 1L, "Tsygankov", BigDecimal.ZERO)));
//            System.out.println("aboba");
//            System.out.println(client.deletePlayer(1L));
//            System.out.println(client.findPlayersByTeamName("Real Madrid"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
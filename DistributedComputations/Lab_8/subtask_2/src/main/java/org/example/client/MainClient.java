package org.example.client;

import org.example.Constants;
import org.example.model.Player;
import org.example.model.Team;
import org.example.rmi.RmiServer;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MainClient {
    public static void main(String[] args)
            throws MalformedURLException, NotBoundException, RemoteException, InterruptedException {
        RmiServer server = (RmiServer) Naming.lookup(Constants.URL);
        System.out.println("Connected");

        Thread.sleep(4000L);

        server.deletePlayer(9L);
        server.deletePlayer(10L);

        //Delete Shakhtar
        server.deleteTeam(1L);

        //Add to PSG
        server.insertPlayer(new Player(null, 4L, "Neymar", BigDecimal.valueOf(20320)));

        //Update Bushan
        server.updatePlayer(new Player(1L, 2L, "Georgy", BigDecimal.ONE));

        //Add Milano
        server.insertTeam(new Team(null, "Milano", "Italy"));

        //Delete Besedin
        server.deletePlayer(3L);

        //Move Cortois to Dynamo
        server.moveToAnotherTeam(8L, 2L);

        //Real players
        System.out.println(server.findPlayersByTeamName("Real Madrid"));

        //All teams
        System.out.println(server.findAllTeams());
    }
}

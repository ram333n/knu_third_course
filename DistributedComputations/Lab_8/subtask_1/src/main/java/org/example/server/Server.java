package org.example.server;

import org.example.dao.PlayerDao;
import org.example.dao.TeamDao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private final int port;
    private DataInputStream in;
    private DataOutputStream out;
    private final PlayerDao playerDao;
    private final TeamDao teamDao;

    public Server(int port) {
        this.port = port;
        this.playerDao = new PlayerDao();
        this.teamDao = new TeamDao();
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            
            while (processQuery());
        }
    }

    private boolean processQuery() throws IOException {
        out.writeBytes("aboba");
        return true;
    }

    public static void main(String[] args) {
//        Server server = new Server(Constants.PORT);
//        try {
//            server.start();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        PlayerDao p = new PlayerDao();
        p.moveToAnotherTeam(6L, 3L);
        System.out.println(p.findByTeamName("Dynamo Kyiv"));
    }
}

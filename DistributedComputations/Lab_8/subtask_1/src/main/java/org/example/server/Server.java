package org.example.server;

import org.example.Constants;
import org.example.dao.PlayerDao;
import org.example.dao.TeamDao;
import org.example.model.Player;
import org.example.model.Team;
import org.example.util.IoUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

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
            System.out.println("Client accepted");

            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            
            while (processQuery());
        }
    }

    private boolean processQuery() {
        try {
            String query = IoUtils.readString(in);
            System.out.println(query);

            switch (query) {
                case "insertTeam" -> {
                    Team team = IoUtils.readTeam(in, false);
                    boolean result = teamDao.insert(team);
                    out.writeBoolean(result);
                }

                case "deleteTeam" -> {
                    Long id = in.readLong();
                    boolean result = teamDao.deleteById(id);
                    out.writeBoolean(result);
                }

                case "insertPlayer" -> {
                    Player player = IoUtils.readPlayer(in, false);
                    boolean result = playerDao.insert(player);
                    out.writeBoolean(result);
                }

                case "deletePlayer" -> {
                    Long id = in.readLong();
                    boolean result = playerDao.deleteById(id);
                    out.writeBoolean(result);
                }

                case "updatePlayer" -> {
                    Player player = IoUtils.readPlayer(in, true);
                    boolean result = playerDao.update(player);
                    out.writeBoolean(result);
                }

                case "moveToAnotherTeam" -> {
                    Long playerId = in.readLong();
                    Long newTeamId = in.readLong();
                    boolean result = playerDao.moveToAnotherTeam(playerId, newTeamId);
                    out.writeBoolean(result);
                }

                case "findPlayersByTeamName" -> {
                    String teamName = IoUtils.readString(in);
                    List<Player> result = playerDao.findByTeamName(teamName);
                    writeListOfPlayers(result);
                }

                case "findAllTeams" -> {
                    List<Team> teams = teamDao.findAll();
                    writeListOfTeams(teams);
                }

                default -> {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void writeListOfPlayers(List<Player> players) throws IOException {
        out.writeInt(players.size());

        for (Player player : players) {
            IoUtils.writePlayer(out, player, true);
        }
    }

    private void writeListOfTeams(List<Team> teams) throws IOException {
        out.writeInt(teams.size());

        for (Team team : teams) {
            IoUtils.writeTeam(out, team, true);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(Constants.PORT);
        try {
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

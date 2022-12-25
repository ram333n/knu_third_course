package org.example.client;

import org.example.model.Player;
import org.example.model.Team;
import org.example.util.IoUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Socket socket;
    private final String host;
    private final int port;
    private DataInputStream in;
    private DataOutputStream out;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(host, port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public boolean insertTeam(Team team) throws IOException {
        IoUtils.writeString(out, "insertTeam");
        IoUtils.writeTeam(out, team, false);

        return in.readBoolean();
    }

    public boolean deleteTeam(Long id) throws IOException {
        IoUtils.writeString(out, "deleteTeam");
        out.writeLong(id);

        return in.readBoolean();
    }

    public boolean insertPlayer(Player player) throws IOException {
        IoUtils.writeString(out, "insertPlayer");
        IoUtils.writePlayer(out, player, false);

        return in.readBoolean();
    }

    public boolean deletePlayer(Long id) throws IOException {
        IoUtils.writeString(out, "deletePlayer");
        out.writeLong(id);

        return in.readBoolean();
    }

    public boolean updatePlayer(Player player) throws IOException {
        IoUtils.writeString(out, "updatePlayer");
        IoUtils.writePlayer(out, player, true);

        return in.readBoolean();
    }

    public boolean moveToAnotherTeam(Long playerId, Long newTeamId) throws IOException {
        IoUtils.writeString(out, "moveToAnotherTeam");
        out.writeLong(playerId);
        out.writeLong(newTeamId);

        return in.readBoolean();
    }

    public List<Player> findPlayersByTeamName(String teamName) throws IOException {
        IoUtils.writeString(out, "findPlayersByTeamName");
        IoUtils.writeString(out, teamName);

        return readPlayers();
    }

    public List<Team> findAllTeams() throws IOException {
        IoUtils.writeString(out, "findAllTeams");

        return readTeams();
    }

    private List<Player> readPlayers() throws IOException {
        List<Player> result = new ArrayList<>();
        int listSize = in.readInt();

        for (int i = 0; i < listSize; i++) {
            result.add(IoUtils.readPlayer(in, true));
        }

        return result;
    }

    private List<Team> readTeams() throws IOException {
        List<Team> result = new ArrayList<>();
        int listSize = in.readInt();

        for (int i = 0; i < listSize; i++) {
            result.add(IoUtils.readTeam(in, true));
        }

        return result;
    }
}

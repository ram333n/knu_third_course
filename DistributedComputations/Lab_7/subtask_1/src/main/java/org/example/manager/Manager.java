package org.example.manager;

import org.example.model.Player;
import org.example.model.Team;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Manager {
    private List<Team> teams;
    private List<Player> players;

    public void loadFromFile(String pathToXml) {}

    public void saveToFile(String pathToXml) {}

    public void addTeam(String name, String country) {}

    public void addPlayerToTeam(UUID teamId, String name, BigDecimal price) {}

    public void deleteTeam(UUID teamId) {}

    public void deletePlayer(UUID playerId) {}

    public void updateTeam(UUID teamId, String name, String country) {}

    public void updatePlayer(UUID playerId, UUID teamId, String name, BigDecimal price) {}

    public void getTeamById(UUID teamId) {}

    public void getPlayerById(UUID playerId) {}

    public void getAllTeams() {}

    public void getAllPlayersOfTeam(UUID teamId) {}
}

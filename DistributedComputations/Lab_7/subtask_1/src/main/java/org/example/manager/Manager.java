package org.example.manager;

import org.example.model.Player;
import org.example.model.Team;

import java.math.BigDecimal;
import java.util.*;

public class Manager {
    private final List<Team> teams;
    private final List<Player> players;

    public Manager() {
        this.teams = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    public Manager(List<Team> teams, List<Player> players) {
        this.teams = teams;
        this.players = players;
    }

    public void loadFromFile(String pathToXml) {}

    public void saveToFile(String pathToXml) {}

    public void addTeam(String name, String country) {
        UUID id = UUID.randomUUID();
        Team toAdd = new Team(id, name, country);
        teams.add(toAdd);
        System.out.printf("Added team: %n%s%n%n", toAdd);
    }

    public void addPlayerToTeam(UUID teamId, String name, BigDecimal price) {
        Optional<Team> team = findTeamById(teamId);

        if (team.isEmpty()) {
            System.out.printf("Unable to add player to team with id: %s(no occurrence found)%n", teamId);
            return;
        }

        UUID id = UUID.randomUUID();
        Player toAdd = new Player(id, teamId, name, price);
        players.add(toAdd);
        System.out.printf("Added player: %n%s%n%n", toAdd);
    }

    public void deleteTeam(UUID teamId) {
        Optional<Team> team = findTeamById(teamId);

        if (team.isEmpty()) {
            System.out.printf("Unable to delete team: %s(no occurrence found)", teamId);
            return;
        }

        long playersToDelete = getPlayerCountInTeam(teamId);
        deletePlayersOfTeam(teamId);
        System.out.printf("Deleted %d players of team: %s%n", playersToDelete, teamId);
    }

    public void deletePlayer(UUID playerId) {
        boolean isDeleted = players.removeIf(player -> Objects.equals(player.getId(), playerId));

        if (isDeleted) {
            System.out.printf("Deleted player: %s%n", playerId);
        } else {
            System.out.printf("Unable to delete player: %s%n(no occurrence found%n", playerId);
        }
    }

    public void updateTeam(UUID teamId, String name, String country) {
        Optional<Team> team = findTeamById(teamId);

        if (team.isEmpty()) {
            System.out.printf("Unable to update team: %s(no occurrence found)%n", teamId);
            return;
        }

        Team toUpdate = team.get();
        toUpdate.setName(name);
        toUpdate.setCountry(country);
        System.out.printf("Successfully updated team: %s%n", teamId);
    }

    public void updatePlayer(UUID playerId, UUID teamId, String name, BigDecimal price) {
        Optional<Player> player = findPlayerById(playerId);

        if (player.isEmpty()) {
            System.out.printf("Unable to update player: %s(no occurrence found)%n", playerId);
            return;
        }

        Player toUpdate = player.get();
        toUpdate.setTeamId(teamId);
        toUpdate.setName(name);
        toUpdate.setPrice(price);
        System.out.printf("Successfully updated player: %s%n", playerId);
    }

    public void getTeamById(UUID teamId) {}

    public void getPlayerById(UUID playerId) {}

    public void getAllTeams() {
        System.out.println("Teams:");

        for (Team team : teams) {
            System.out.println(team);
            System.out.println("--------------------------");
        }
    }

    public void getAllPlayersOfTeam(UUID teamId) {
        Optional<Team> team = findTeamById(teamId);

        if (team.isEmpty()) {
            System.out.printf("Unable to get all players of team: %s(no occurrence of team found)%n", teamId);
        }

        System.out.printf("Players of team %s:%n", teamId);
        players.stream()
                .filter(player -> Objects.equals(player.getTeamId(), teamId))
                .forEach((player -> {
                    System.out.println(player);
                    System.out.println("--------------------------");
                }));
    }

    public long getPlayerCountInTeam(UUID teamId) {
        return players.stream()
                .filter(team -> Objects.equals(team.getTeamId(), teamId))
                .count();
    }

    private Optional<Team> findTeamById(UUID id) {
        return teams.stream()
                .filter(team -> Objects.equals(team.getId(), id))
                .findFirst();
    }

    private Optional<Player> findPlayerById(UUID id) {
        return players.stream()
                .filter(player -> Objects.equals(player.getId(), id))
                .findFirst();
    }

    private void deletePlayersOfTeam(UUID teamId) {
        players.removeIf(player -> Objects.equals(player.getTeamId(), teamId));
    }
}

package org.example.rmi;

import org.example.model.Player;
import org.example.model.Team;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RmiServer extends Remote {

    boolean insertTeam(Team team) throws RemoteException;

    boolean deleteTeam(Long id) throws RemoteException;

    boolean insertPlayer(Player player) throws RemoteException;

    boolean deletePlayer(Long id) throws RemoteException;

    boolean updatePlayer(Player player) throws RemoteException;

    boolean moveToAnotherTeam(Long playerId, Long newTeamId) throws RemoteException;

    List<Player> findPlayersByTeamName(String teamName) throws RemoteException;

    List<Team> findAllTeams() throws RemoteException;
}

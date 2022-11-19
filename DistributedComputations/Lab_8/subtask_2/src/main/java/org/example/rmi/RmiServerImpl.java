package org.example.rmi;

import org.example.dao.PlayerDao;
import org.example.dao.TeamDao;
import org.example.model.Player;
import org.example.model.Team;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RmiServerImpl extends UnicastRemoteObject implements RmiServer {
    private final PlayerDao playerDao;
    private final TeamDao teamDao;
    private final transient ReadWriteLock lock;

    public RmiServerImpl() throws RemoteException {
        this.playerDao = new PlayerDao();
        this.teamDao = new TeamDao();
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public boolean insertTeam(Team team) throws RemoteException {
        try {
            lock.writeLock().lock();
            return teamDao.insert(team);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean deleteTeam(Long id) throws RemoteException {
        try {
            lock.writeLock().lock();
            return teamDao.deleteById(id);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean insertPlayer(Player player) throws RemoteException {
        try {
            lock.writeLock().lock();
            return playerDao.insert(player);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean deletePlayer(Long id) throws RemoteException {
        try {
            lock.writeLock().lock();
            return playerDao.deleteById(id);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean updatePlayer(Player player) throws RemoteException {
        try {
            lock.writeLock().lock();
            return playerDao.update(player);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean moveToAnotherTeam(Long playerId, Long newTeamId) throws RemoteException {
        try {
            lock.writeLock().lock();
            return playerDao.moveToAnotherTeam(playerId, newTeamId);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Player> findPlayersByTeamName(String teamName) throws RemoteException {
        try {
            lock.readLock().lock();
            return playerDao.findByTeamName(teamName);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Team> findAllTeams() throws RemoteException {
        try {
            lock.readLock().lock();
            return teamDao.findAll();
        } finally {
            lock.readLock().unlock();
        }
    }
}

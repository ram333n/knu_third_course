package org.example;

import org.example.dao.PlayerDao;
import org.example.dao.TeamDao;
import org.example.model.Player;
import org.example.model.Team;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        TeamDao teamDao = new TeamDao();
        PlayerDao playerDao = new PlayerDao();

        //Delete PSG players
        playerDao.deleteById(9L);
        playerDao.deleteById(10L);

        //Delete Shakhtar
        teamDao.deleteById(1L);

        //Add to PSG
        playerDao.insert(new Player(null, 4L, "Neymar", BigDecimal.valueOf(20320)));

        //Update Bushan
        playerDao.update(new Player(1L, 2L, "Georgy", BigDecimal.ONE));

        //Add Milano
        teamDao.insert(new Team(null, "Milano", "Italy"));

        //Update Dynamo
        teamDao.update(new Team(2L, "Dynamo Kyiv", "Ukraine"));

        //Delete Besedin
        playerDao.deleteById(3L);
    }
}
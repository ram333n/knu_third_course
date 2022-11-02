package org.example;

import org.example.dao.TeamDao;
import org.example.model.Team;

public class Main {
    public static void main(String[] args) {
        TeamDao teamDao = new TeamDao();
        teamDao.update(new Team(1L, "Shakhtar", "Ukraine"));
        teamDao.deleteById(1L);
        System.out.println(teamDao.findAll());
    }
}
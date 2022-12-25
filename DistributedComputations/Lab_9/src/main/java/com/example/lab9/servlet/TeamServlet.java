package com.example.lab9.servlet;

import com.example.lab9.dao.TeamDao;
import com.example.lab9.model.Team;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TeamServlet extends HttpServlet {
    private transient TeamDao teamDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.teamDao = new TeamDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processQuery(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processQuery(request, response);
    }

    private void processQuery(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getServletPath();
        String query = url.substring(5); // "team".length + 1

        switch (query) {
            case "New" -> showNewForm(request, response);

            case "Insert" -> insertTeam(request, response);

            case "Delete" -> deleteTeam(request, response);

            case "Edit" -> showEditForm(request, response);

            case "Update" ->  updateTeam(request, response);

            case "List" -> listTeams(request, response);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("TeamForm.jsp");
        dispatcher.forward(request, response);
    }

    private void insertTeam(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("name");
        String country = request.getParameter("country");

        Team team = new Team();
        team.setName(name);
        team.setCountry(country);
        teamDao.insert(team);
        response.sendRedirect("TeamList");
    }

    private void deleteTeam(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        teamDao.deleteById(id);
        response.sendRedirect("TeamList");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Optional<Team> teamOptional = teamDao.findById(id);
        Team team = teamOptional.orElse(null);
        RequestDispatcher dispatcher = request.getRequestDispatcher("TeamForm.jsp");
        request.setAttribute("team", team);
        dispatcher.forward(request, response);
    }

    private void updateTeam(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String country = request.getParameter("country");

        Team team = new Team(id, name, country);
        teamDao.update(team);
        response.sendRedirect("TeamList");
    }

    private void listTeams(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Team> teams = teamDao.findAll();
        request.setAttribute("listTeam", teams);
        RequestDispatcher dispatcher = request.getRequestDispatcher("TeamList.jsp");
        dispatcher.forward(request, response);
    }
}

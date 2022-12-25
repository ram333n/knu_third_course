package com.example.lab9.servlet;

import com.example.lab9.dao.PlayerDao;
import com.example.lab9.model.Player;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PlayerServlet extends HttpServlet {
    private transient PlayerDao playerDao;

    @Override
    public void init() throws ServletException {
        this.playerDao = new PlayerDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processQuery(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processQuery(request, response);
    }

    private void processQuery(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getServletPath();
        String query = url.substring(7); // "player".length + 1

        switch (query) {
            case "New" -> showNewForm(request, response);

            case "Insert" -> insertPlayer(request, response);

            case "Delete" -> deletePlayer(request, response);

            case "Edit" -> showEditForm(request, response);

            case "Update" -> updatePlayer(request, response);

            case "List" -> listPlayers(request, response);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("PlayerForm.jsp");
        dispatcher.forward(request, response);
    }

    private void insertPlayer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long teamId = Long.parseLong(request.getParameter("teamId"));
        String name = request.getParameter("name");
        BigDecimal price = new BigDecimal(request.getParameter("price"));

        Player player = new Player();
        player.setTeamId(teamId);
        player.setName(name);
        player.setPrice(price);
        playerDao.insert(player);
        response.sendRedirect("PlayerList");
    }

    private void deletePlayer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        playerDao.deleteById(id);
        response.sendRedirect("PlayerList");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Optional<Player> playerOptional = playerDao.findById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("PlayerForm.jsp");
        request.setAttribute("player", playerOptional.orElse(null));
        dispatcher.forward(request, response);
    }

    private void updatePlayer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Long teamId = Long.parseLong(request.getParameter("teamId"));
        String name = request.getParameter("name");
        BigDecimal price = new BigDecimal(request.getParameter("price"));

        Player player = new Player(id, teamId, name, price);
        playerDao.update(player);
        response.sendRedirect("PlayerList");
    }

    private void listPlayers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Player> players = playerDao.findAll();
        request.setAttribute("listPlayer", players);
        RequestDispatcher dispatcher = request.getRequestDispatcher("PlayerList.jsp");
        dispatcher.forward(request, response);
    }
}

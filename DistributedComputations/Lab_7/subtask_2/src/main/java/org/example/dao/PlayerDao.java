package org.example.dao;

import org.example.connection.SingletonConnector;
import org.example.model.Player;
import org.example.model.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerDao {
    public Optional<Player> findById(Long id) {
        final String sql = "SELECT * FROM players WHERE id = ?";

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            Optional<Player> result = Optional.empty();

            try (ResultSet rs = statement.executeQuery();) {
                if (rs.next()) {
                    Player player = new Player();
                    player.setId(rs.getLong("id"));
                    player.setTeamId(rs.getLong("team_id"));
                    player.setName(rs.getString("name"));
                    player.setPrice(rs.getBigDecimal("price"));
                    result = Optional.of(player);
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Player> findAll() {
        final String sql = "SELECT * FROM players";

        try (Connection connection = SingletonConnector.getConnection();
             Statement statement = connection.createStatement()) {

            List<Player> result = new ArrayList<>();

            try (ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    Player player = new Player();
                    player.setId(rs.getLong("id"));
                    player.setTeamId(rs.getLong("team_id"));
                    player.setName(rs.getString("name"));
                    player.setPrice(rs.getBigDecimal("price"));
                    result.add(player);
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Player updated) {
        final String sql = "UPDATE players SET team_id = ?, name = ?, price = ? WHERE id = ?";

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, updated.getTeamId());
            statement.setString(2, updated.getName());
            statement.setBigDecimal(3, updated.getPrice());
            statement.setLong(4, updated.getId());
            int updatedRecords = statement.executeUpdate();

            return updatedRecords > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById(Long id) {
        final String sql = "DELETE FROM players WHERE id = ?";

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int deletedRecords = statement.executeUpdate();

            return deletedRecords > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(Player toInsert) {
        final String sql = "INSERT INTO football.players(team_id, name, price) VALUES(?, ?, ?)";

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, toInsert.getTeamId());
            statement.setString(2, toInsert.getName());
            statement.setBigDecimal(3, toInsert.getPrice());
            int insertedCount = statement.executeUpdate();

            return insertedCount > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.connection.SingletonConnector;
import org.example.model.Team;

public class TeamDao {
    public Optional<Team> findById(Long id) {
        final String sql = "SELECT * FROM teams WHERE id = ?";

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            Optional<Team> result = Optional.empty();

            try (ResultSet rs = statement.executeQuery();) {
                if (rs.next()) {
                    Team team = new Team();
                    team.setId(rs.getLong("id"));
                    team.setName(rs.getString("name"));
                    team.setCountry(rs.getString("country"));
                    result = Optional.of(team);
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Team> findAll() {
        final String sql = "SELECT * FROM teams";

        try (Connection connection = SingletonConnector.getConnection();
             Statement statement = connection.createStatement()) {

            List<Team> result = new ArrayList<>();

            try (ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    Team team = new Team();
                    team.setId(rs.getLong("id"));
                    team.setName(rs.getString("name"));
                    team.setCountry(rs.getString("country"));
                    result.add(team);
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Team updated) {
        final String sql = "UPDATE teams SET name = ?, country = ? WHERE id = ?";

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, updated.getName());
            statement.setString(2, updated.getCountry());
            statement.setLong(3, updated.getId());
            int updatedRecords = statement.executeUpdate();

            return updatedRecords > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById(Long id) {
        final String sql = "DELETE FROM teams WHERE id = ?";

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int deletedRecords = statement.executeUpdate();

            return deletedRecords > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(Team toInsert) {
        final String sql = "INSERT INTO teams(name, country) VALUES(?, ?)";

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, toInsert.getName());
            statement.setString(2, toInsert.getCountry());
            int insertedCount = statement.executeUpdate();
            System.out.println("Team inserted");
            return insertedCount > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

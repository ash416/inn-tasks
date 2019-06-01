package part1.lesson15;

import part1.lesson15.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class UserRepository {

    private Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public void insertIntoTheTable(List<User> users) throws SQLException {
        String sqlInsert = "INSERT INTO users (id, name, birthday, login_ID, city, email, description)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        users.forEach(user -> {
            try {
                statement.setInt(1, user.getId());
                statement.setString(2, user.getName());
                statement.setDate(3, Date.valueOf(user.getBirthdate()));
                statement.setString(4, user.getLoginId());
                statement.setString(5, user.getCity());
                statement.setString(6, user.getEmail());
                statement.setString(7, user.getDescription());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void insertBatchIntoTheTable(List<User> users) throws SQLException {
        String sqlInsert = "INSERT INTO users (id, name, birthday, login_ID, city, email, description)" +
                "VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s')";
        Statement statement = connection.createStatement();
        users.forEach(user -> {
            try {
                statement.addBatch(format(sqlInsert, user.getId(), user.getName(), Date.valueOf(user.getBirthdate()),
                        user.getLoginId(), user.getCity(), user.getEmail(), user.getDescription()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        statement.executeBatch();
    }

    public List<User> selectByLoginIdAndName(String loginId, String name) {
        String sqlSelect = "SELECT * FROM users WHERE login_ID = ? AND name = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlSelect);
            statement.setString(1, loginId);
            statement.setString(2, name);
            ResultSet resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getDate(3).toLocalDate(),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7));
    }
}
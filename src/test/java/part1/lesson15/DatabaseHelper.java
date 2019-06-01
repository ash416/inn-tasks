package part1.lesson15;

import part1.lesson15.model.Role;
import part1.lesson15.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static part1.lesson15.UserRepository.getUserFromResultSet;

public class DatabaseHelper {

    public static Connection initDatabaseConnection() {
        UserDbDesigner designer = new UserDbDesigner();
        designer.design();
        Connection connection = null;
        try (InputStream inputStream = UserRepository.class.getClassLoader()
                .getResourceAsStream("config/application.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            Class.forName(properties.getProperty("database.driver"));
            connection = DriverManager.getConnection(
                    properties.getProperty("database.url"),
                    properties.getProperty("database.username"),
                    properties.getProperty("database.password"));
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    static void cleanTables(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE users");
            statement.executeUpdate("TRUNCATE TABLE role");
            statement.executeUpdate("TRUNCATE TABLE user_role");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static List<User> getAllUsers(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(getUserFromResultSet(resultSet));
        }
        return users;
    }

    static List<Role> getAllRoles(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM role");
        List<Role> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(new Role(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
        }
        return users;
    }
    static List<Integer> getAllUserRoleIds(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id FROM user_role");
        List<Integer> ids = new ArrayList<>();
        while (resultSet.next()) {
            ids.add(resultSet.getInt(1));
        }
        return ids;
    }
}

package part1.lesson15;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * The class creates three table: users, role, user_role
 */

public class UserDbDesigner {
    private static final Logger LOGGER = LogManager.getLogger(UserDbDesigner.class);


    private static String url;
    private static String username;
    private static String password;

    private Connection connection;
    private Statement statement;

    public UserDbDesigner() {
        try (InputStream inputStream = UserDbDesigner.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            Class.forName(properties.getProperty("database.driver"));
            url = properties.getProperty("database.url");
            username = properties.getProperty("database.username");
            password = properties.getProperty("database.password");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void design() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            createUsersTable();
            createRoleTable();
            createUserRoleTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void createUsersTable() throws SQLException {
        LOGGER.info("Создание таблицы users");
        final String sqlQuery =
                "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER NOT NULL," +
                "name VARCHAR(50)," +
                "birthday DATE," +
                "login_ID VARCHAR(50)," +
                "city VARCHAR(50)," +
                "email VARCHAR(50)," +
                "description VARCHAR(200)," +
                "PRIMARY KEY (id) );";
        statement = connection.createStatement();
        statement.executeUpdate(sqlQuery);
        LOGGER.info("Таблица успешно создана");
    }

    private void createRoleTable() throws SQLException {
        LOGGER.info("Создание таблицы role");
        String sqlQuery = "SELECT COUNT(*) AS total FROM pg_type WHERE pg_type.typname = 'role_type'";
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        resultSet.next();
        if (resultSet.getInt("total") == 0) {
            LOGGER.info("Создание типа role_type");
            sqlQuery = "CREATE TYPE IF NOT EXISTS ROLE_TYPE AS ENUM ('Administration', 'Clients', 'Billing')";
            statement.executeQuery(sqlQuery);
        }
        sqlQuery ="CREATE TABLE IF NOT EXISTS role (" +
                        "id INTEGER NOT NULL," +
                        "name ROLE_TYPE," +
                        "description VARCHAR(200)," +
                        "PRIMARY KEY (id) );";

        statement.executeUpdate(sqlQuery);
        LOGGER.info("Таблица успешно создана");
    }

    private void createUserRoleTable() throws SQLException {
        LOGGER.info("Создание таблицы user_role");
        String sqlQuery ="CREATE TABLE IF NOT EXISTS user_role (" +
                "id INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "role_id INTEGER NOT NULL," +
                "PRIMARY KEY (id) );";
        statement.executeUpdate(sqlQuery);
        LOGGER.info("Таблица успешно создана");
    }

    public static void main(String[] args) {
        UserDbDesigner designer = new UserDbDesigner();
        designer.design();
    }
}

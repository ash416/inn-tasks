package part1.lesson15;

import part1.lesson15.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class LoggerDemonstrator {

    private Connection connection;

    LoggerDemonstrator() {
        try (InputStream inputStream = LoggerDemonstrator.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            Class.forName(properties.getProperty("database.driver"));
            connection = DriverManager.getConnection(properties.getProperty("database.url"),
                    properties.getProperty("database.username"),
                    properties.getProperty("database.password"));
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    void demonstrate() throws SQLException {
        UserRepository repository = new UserRepository(connection);
        List<User> user = Arrays.asList(new User(1, "name1", LocalDate.now(),
                "login_id1", "email1", "city1", "description1"));
        repository.insertIntoTheTable(user);
        repository.insertIntoTheTable(user);
    }

    public static void main(String[] args) {
        LoggerDemonstrator demonstrator = new LoggerDemonstrator();
        try {
            demonstrator.demonstrate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

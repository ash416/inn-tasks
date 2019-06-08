package part1.lesson15;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    public static Connection getDatabaseConnection() throws SQLException, ClassNotFoundException {
        try (InputStream inputStream = LoggerDemonstrator.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            Class.forName(properties.getProperty("database.driver"));
            return DriverManager.getConnection(properties.getProperty("database.url"),
                    properties.getProperty("database.username"),
                    properties.getProperty("database.password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package part1.lesson15.mockito;

import part1.lesson15.UserRepository;
import part1.lesson15.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static part1.lesson15.DatabaseHelper.initDatabaseConnection;

/**
 * The class uses UserRepository. It is for only mockito demonstration
 */

public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> doSomethingWithUserRepository() throws SQLException {
        Connection connection = initDatabaseConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO users (id, login_ID, name, birthday) VALUES (1, 'login', 'name', '12.03.1997')");
        List<User> users = repository.selectByLoginIdAndName("login", "name");
        statement.executeUpdate("TRUNCATE TABLE users");
        return users;
    }

}

package part1.lesson15;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import part1.lesson15.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static part1.lesson15.DatabaseHelper.*;


public class UserRepositoryTest {

    private static UserRepository repository;
    private static Connection connection;

    @BeforeAll
    static void init() {
        connection = initDatabaseConnection();
        repository = new UserRepository(connection);
    }


    private List<User> prepareUsersData() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User(i, "user" + i, LocalDate.now(), "login_id" + i,
                    "city" + i, "email" + i, "description" + i);
            users.add(user);
        }
        return users;
    }

    @Test
    public void insertIntoTheTableTest() throws SQLException {
        List<User> expected = prepareUsersData();
        repository.insertIntoTheTable(expected);
        List<User> result = getAllUsers(connection);
        assertIterableEquals(expected, result);
    }

    @Test
    public void insertBatchIntoTheTableTest() throws SQLException {
        List<User> expected = prepareUsersData();
        repository.insertBatchIntoTheTable(expected);
        List<User> result = getAllUsers(connection);
        assertIterableEquals(expected, result);
    }

    @Test
    public void findUsersByLoginIdAndNameTest() throws SQLException {
        repository.insertBatchIntoTheTable(prepareUsersData());
        List<User> users = repository.selectByLoginIdAndName("login_id5", "user5");
        assertEquals(users.get(0), new User(5, "user5", LocalDate.now(),
                "login_id5", "city5", "email5", "description5"));
        users = repository.selectByLoginIdAndName("login_id6", "user5");
        assertEquals(0, users.size());
        users = repository.selectByLoginIdAndName("incorrect_login", "incorrect_name");
        assertEquals(0, users.size());

    }

    @AfterEach
    void clean() {
        cleanTables(connection);
    }


    @AfterAll
    static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

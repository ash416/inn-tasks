package part1.lesson15;

import org.junit.jupiter.api.*;
import part1.lesson15.model.Role;
import part1.lesson15.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static part1.lesson15.DatabaseHelper.*;

public class TransactionManagerTest {

    private static Connection connection;

    @BeforeAll
    public static void init() {
        connection = initDatabaseConnection();
    }

    @Test
    public void successInsertingCorrectData() throws SQLException {
        User user = new User(1, "name1", LocalDate.now(), "login_id1",
                "city1", "email1", "description1");
        Role role = new Role(1, "Administration", "description1");
        TransactionManager.insertToTables(user, role, 1, connection);
        Assertions.assertIterableEquals(getAllUsers(connection), Arrays.asList(user));
        Assertions.assertIterableEquals(getAllRoles(connection), Arrays.asList(role));
        Assertions.assertIterableEquals(getAllUserRoleIds(connection), Arrays.asList(1));
    }

    @Test
    public void successInsertingUserAndUserRoleWhileRoleInsertingFailed() throws SQLException {
        User user = new User(1, "name1", LocalDate.now(), "login_id1",
                "city1", "email1", "description1");
        Role role = new Role(1, "Something_wrong", "description1");
        TransactionManager.insertToTables(user, role, 1, connection);
        Assertions.assertIterableEquals(getAllUsers(connection), Arrays.asList(user));
        Assertions.assertIterableEquals(getAllRoles(connection), new ArrayList<>());
        Assertions.assertIterableEquals(getAllUserRoleIds(connection), Arrays.asList(1));
    }

    @Test
    public void successInsertingUserAndRoleWhileUserRoleInsertingFailed() throws SQLException {
        User user = new User(1, "name1", LocalDate.now(), "login_id1",
                "city1", "email1", "description1");
        Role role = new Role(1, "Administration", "description1");
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO user_role VALUES (1, 1, 1)");
        TransactionManager.insertToTables(user, role, 1, connection);
        Assertions.assertIterableEquals(getAllUsers(connection), Arrays.asList(user));
        Assertions.assertIterableEquals(getAllRoles(connection),  Arrays.asList(role));
        Assertions.assertIterableEquals(getAllUserRoleIds(connection), Arrays.asList(1));
    }

    @AfterEach
    void clean() {
        cleanTables(connection);
    }

    @AfterAll
    static void close() throws SQLException {
        connection.close();
    }
}

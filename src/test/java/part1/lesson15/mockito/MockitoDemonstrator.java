package part1.lesson15.mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import part1.lesson15.DatabaseHelper;
import part1.lesson15.UserRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockitoDemonstrator {

    private static UserService userService;
    private static UserService userServiceWithSpy;
    private static UserRepository repositorySpy;

    @BeforeAll
    static void setUp() {
        UserRepository repository = mock(UserRepository.class);
        when(repository.selectByLoginIdAndName(anyString(), anyString())).thenReturn(new ArrayList<>());
        userService = new UserService(repository);

        Connection connection = DatabaseHelper.initDatabaseConnection();
        repositorySpy = Mockito.spy(new UserRepository(connection));
        userServiceWithSpy = new UserService(repositorySpy);
    }


    @Test
    void userServiceWithMockTest() throws SQLException {
        Assertions.assertIterableEquals(new ArrayList<>(), userService.doSomethingWithUserRepository());
    }

    @Test
    void userServiceWithSpy() throws SQLException{
        Assertions.assertEquals(1, userServiceWithSpy.doSomethingWithUserRepository().size());
        when(repositorySpy.selectByLoginIdAndName(anyString(), anyString())).thenReturn(new ArrayList<>());
        Assertions.assertEquals(0, userServiceWithSpy.doSomethingWithUserRepository().size());
    }

}

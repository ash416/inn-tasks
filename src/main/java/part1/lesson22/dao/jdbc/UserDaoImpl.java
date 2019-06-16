package part1.lesson22.dao.jdbc;

import part1.lesson22.dao.UserDao;
import part1.lesson22.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoImpl implements UserDao {
    private static Logger logger = Logger.getLogger(UserDaoImpl.class.getName());
    private final Connection connection;

    public UserDaoImpl(Connection con) {
        this.connection = con;
        addUser(new User("admin", "password"));
    }

    private static final String INSERT_USER_SQL_TEMPLATE =
            "insert into user (login, password) values (?, ?)";
    private static final String SELECT_USER_BY_LOGIN_AND_PASSWORD_SQL_TEMPLATE =
            "select * from user where login = ? and password = ?";

    @Override
    public void addUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER_SQL_TEMPLATE)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred on the DAO layer.", e);
        }
    }

    @Override
    public User findUser(String login, String password) {
        try (PreparedStatement statement = connection
                .prepareStatement(SELECT_USER_BY_LOGIN_AND_PASSWORD_SQL_TEMPLATE)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setLogin(resultSet.getString(1));
                    user.setPassword(resultSet.getString(2));
                    return user;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred on the DAO layer.", e);
        }
        return null;
    }
}

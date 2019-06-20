package part1.lesson15;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import part1.lesson15.model.Role;
import part1.lesson15.model.User;

import java.sql.*;

import static java.lang.String.format;

/**
 * The class for inserting elements into three table: user, role and user_role without auto commit
 */

public class TransactionManager {
    private static final Logger LOGGER = LogManager.getLogger(TransactionManager.class);

    /**
     * The method inserts data in tables. Firstly it inserts user, than role, tham user_role.
     * If some execution throws an exception, the transaction refuses to the previously savepoint
     * @param user
     * @param role
     * @param userRoleId
     * @param connection
     * @throws SQLException
     */

    public static void insertToTables(User user, Role role, Integer userRoleId, Connection connection) throws SQLException {
        LOGGER.info("Вставка в таблицу данных в ручной транзакции");
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        LOGGER.info("Вставка данных в таблицу user");
        statement.executeUpdate(getInsertUserQuery(user));
        Savepoint userSavePoint = connection.setSavepoint();
        Savepoint roleSavePoint = null;
        try {
            LOGGER.info("Вставка данных в таблицу role");
            statement.executeUpdate(getInsertRoleQuery(role));
            roleSavePoint = connection.setSavepoint();
        } catch (SQLException e) {
            LOGGER.error("Не удалось вставить данные в таблицу role", e);
            if (userSavePoint != null) {
                connection.rollback(userSavePoint);
            }
        }
        try {
            LOGGER.info("Вставка в таблицу user_role");
            statement.executeUpdate(getInsertUserRoleQuery(userRoleId, user.getId(), role.getId()));
        } catch (SQLException e) {
            LOGGER.error("Не удалось вставить данные в таблицу user_role", e);
            if (roleSavePoint != null) {
                connection.rollback(roleSavePoint);
            }
        }
        connection.commit();
        LOGGER.info("Вставка в таблицы завершена");
        connection.setAutoCommit(true);
    }

    private static String getInsertUserQuery(User user) {
        return format("INSERT INTO users (id, name, birthday, login_ID, city, email, description)" +
                        "VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s')", user.getId(), user.getName(),
                Date.valueOf(user.getBirthdate()), user.getLoginId(), user.getCity(), user.getEmail(), user.getDescription());
    }

    private static String getInsertRoleQuery(Role role) {
        return format("INSERT INTO ROLE (id, name, description) VALUES (%s, '%s', '%s')",
                role.getId(), role.getName(), role.getDescription());
    }

    private static String getInsertUserRoleQuery(Integer userRoleId, Integer userId, Integer roleId) {
        return format("INSERT INTO user_role (id, user_id, role_id) VALUES (%s, %s, %s)",
                userRoleId, userId, roleId);
    }


}


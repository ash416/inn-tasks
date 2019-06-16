package part1.lesson22.dao;

import part1.lesson22.entity.User;

public interface UserDao {

    void addUser(User user);
    User findUser(String login, String password);
}

package dao;

import entity.User;

public interface UserDao  {

    User getUserByUsername(String username);
    void addUser(User user);
    void deleteUser(String username);
    void updateUser(User user);
}

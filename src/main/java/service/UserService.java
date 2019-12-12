package service;

import entity.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    User getUserByUsername(String username);
    void addUser(User user);
    void deleteUser(String username);
    void updateUser(User user);
    boolean validate(User user, String password);
}

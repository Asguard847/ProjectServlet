package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.UserService;

import javax.servlet.http.HttpServletRequest;

public class UserServiceImpl implements UserService {

    private static UserDao userDao;

    static{
        userDao = new UserDaoImpl();
    }

    @Override
    public User getUserByUsername(HttpServletRequest request) {
        String username = request.getParameter("username");
        return userDao.getUserByUsername(username);
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void deleteUser(String username) {
        userDao.deleteUser(username);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public boolean validate(User user, String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, user.getPassword());
    }
}

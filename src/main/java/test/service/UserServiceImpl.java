package test.service;

import test.dao.UserDAO;
import test.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    @Transactional
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    @Transactional
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Transactional
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Transactional
    public List<User> listUsers(String searchString) {
        return userDAO.listUsers(searchString);
    }
}

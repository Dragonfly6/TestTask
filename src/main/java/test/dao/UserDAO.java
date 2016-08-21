package test.dao;

import test.model.User;

import java.util.List;

public interface UserDAO {

    void addUser(User user);

    void deleteUser(int id);

    void updateUser(User user);

    User getUserById(int id);

    List<User> listUsers(String searchString);
}

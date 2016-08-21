package test.dao;

import org.hibernate.Query;
import test.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    public void deleteUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, id);
        if (user != null) session.delete(user);
    }

    public void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    public User getUserById(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, id);
        return user;
    }

    public List<User> listUsers(String searchString) {
        List<User> userList;
        if (searchString == null || searchString.length() == 0) {
            Session session = sessionFactory.getCurrentSession();
            userList = session.createQuery("from User").list();
            return userList;
        }else {
            Session session = sessionFactory.getCurrentSession();
            Query q = session.createQuery("from User user where user.name like :searchString");
            q.setString("searchString", "%" + searchString + "%");
            userList = q.list();
            return userList;
        }
    }


}

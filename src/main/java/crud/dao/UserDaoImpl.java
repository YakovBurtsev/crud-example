package crud.dao;

import crud.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Burtsev on 07.08.2016.
 */

@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    public static final int ROWS_PER_PAGE = 5;

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(user);
        logger.info("User successfully added. User details: " + user);
    }

    @Override
    public void updateUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(user);
        logger.info("User successfully updated. User details: " + user);
    }

    @Override
    public void removeUser(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = (User)session.load(User.class, new Integer(id));
        if (user != null) {
            session.delete(user);
        }
        logger.info("User successfully removed. User details: " + user);
    }

    @Override
    public User getUserById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, id);
        logger.info("User successfully loaded. User details: " + user);
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers(int page) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User");
        query.setFirstResult((page-1)* ROWS_PER_PAGE);
        query.setMaxResults(ROWS_PER_PAGE);
        List<User> users = query.list();
        for(User user: users){
            logger.info("User list: " + user);
        }
        return users;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsersByName(String name, int page) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where name = '" + name + "'");
        query.setFirstResult((page-1)* ROWS_PER_PAGE);
        query.setMaxResults(ROWS_PER_PAGE);
        List<User> users = query.list();
        for(User user: users){
            logger.info("Users by name list: " + user);
        }
        return users;
    }

    @Override
    public int count() {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(*) from User");
        Long count = (Long)query.uniqueResult();
        logger.info("Users count: " + count);
        return count.intValue();
    }

    @Override
    public int searchByNameResultCount(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(*) from User where name = '" + name + "'");
        Long count = (Long)query.uniqueResult();
        logger.info("Users by name count: " + count);
        return count.intValue();
    }
}

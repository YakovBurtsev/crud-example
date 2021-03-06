package crud.service;

import crud.dao.UserDao;
import crud.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Burtsev on 07.08.2016.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        this.userDao.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        this.userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void removeUser(int id) {
        this.userDao.removeUser(id);
    }

    @Override
    @Transactional
    public User getUserById(int id) {
        return this.userDao.getUserById(id);
    }

    @Override
    @Transactional
    public List<User> listUsers(int page) {
        return this.userDao.listUsers(page);
    }

    @Override
    @Transactional
    public List<User> listUsersByName(String name, int page) { return this.userDao.listUsersByName(name, page); }

    @Override
    @Transactional
    public int count() {
        return this.userDao.count();
    }

    @Override
    @Transactional
    public int searchByNameResultCount(String name) {
        return this.userDao.searchByNameResultCount(name);
    }
}

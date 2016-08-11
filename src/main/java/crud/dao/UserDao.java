package crud.dao;

import crud.model.User;

import java.util.List;

/**
 * Created by Burtsev on 07.08.2016.
 */
public interface UserDao {
    void addUser(User user);

    void updateUser(User user);

    void removeUser(int id);

    User getUserById(int id);

    List<User> listUsers();

    List<User> listUsersByName(String name);
}

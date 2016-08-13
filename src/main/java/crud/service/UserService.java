package crud.service;

import crud.model.User;

import java.util.List;

/**
 * Created by Burtsev on 07.08.2016.
 */
public interface UserService {
    void addUser(User user);

    void updateUser(User user);

    void removeUser(int id);

    User getUserById(int id);

    List<User> listUsers(int page);

    List<User> listUsersByName(String name);

    int count();
}

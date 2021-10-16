package dao;

import models.User;

import java.util.List;

public interface UsersDao {
    //create
    void add(User user);

    //read
    List<User> getAll();

    User findById(int id);
    User findByEmail(String email);
    User getUserByCategory(String email, String password, String categories);
    boolean authenticate(String email, String password);

    //delete
    void deleteById(int id);
    void clearAll();
}

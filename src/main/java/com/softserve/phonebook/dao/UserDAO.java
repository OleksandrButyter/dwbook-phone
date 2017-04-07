package com.softserve.phonebook.dao;

import java.util.List;

import com.softserve.phonebook.api.User;

public interface UserDAO {

    User getUserByName(String username);

    User getUserById(int id);

    int createUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

    List<User> getAllUsers();

    boolean hasNoDuplicates(User user);
}

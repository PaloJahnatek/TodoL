package com.palo.todolist.services.api;

import com.palo.todolist.domain.User;

import java.util.List;

public interface UserService {
    List<User> getUsers(); // vráti všetkých userov
    User get(Integer id); // vráti len jedného usera
    Integer add(User user); // pridáme jedného usera a vráti mi jeho id
    void delete(Integer id);
    void update(Integer id, User user);


}

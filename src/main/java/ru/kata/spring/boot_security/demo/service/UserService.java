package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getUsers();
    void addUser(User user);
    void deleteUser(Long id);
    void editUser(User user);
    User findById(Long id);
    User findByUsername(String username);
    Set<Role> getRolesByIds(List<Long> roleIds);
}
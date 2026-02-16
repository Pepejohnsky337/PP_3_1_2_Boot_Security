package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserDao userDao,
                           PasswordEncoder passwordEncoder,
                           RoleService roleService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public void editUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            User existing = findById(user.getId());
            user.setPassword(existing.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.editUser(user);
    }

    @Override
    public Set<Role> getRolesByIds(List<Long> roleIds) {
        if (roleIds == null) {
            return new HashSet<>();
        }
        return roleIds.stream()
                .map(roleService::findById)
                .collect(Collectors.toSet());
    }
}

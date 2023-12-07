package com.beans;

import com.entities.User;
import com.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import utilities.PasswordEncrypter;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UserBean implements Serializable {
    @Inject
    private UserRepository userRepository;

    private PasswordEncrypter encrypter;
    private List users;
    private User currentUser;
    private String username;
    private String password;

    @PostConstruct
    public void init() {
        loadUsers();
    }

    public void loadUsers(){ users = userRepository.getAllUsers();}

    public String login() {
        currentUser = (User)userRepository.authenticate(username, password);
        if (currentUser != null) {
            return "home"; // Navigate to the Preferences Page
        }
        return "login"; // Authentication failed, show error message
    }

    public void logout() {
        currentUser = null;
    }

    public void saveUser() {
        if (currentUser != null) {
            String hashedPassword = encrypter.hashPassword(currentUser.getPasswordHash());
            currentUser.setPasswordHash(hashedPassword);
            currentUser.setRole("teacher");
            userRepository.saveUser(currentUser);
        }
    }

    public List getUsers() {
        return users;
    }


    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

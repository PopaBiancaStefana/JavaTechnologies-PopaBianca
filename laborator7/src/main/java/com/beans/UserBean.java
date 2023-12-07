package com.beans;

import com.entities.User;
import com.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import utilities.PasswordEncrypter;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.List;

@Named
@SessionScoped
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
        currentUser = new User();
        encrypter = new PasswordEncrypter();
    }

    public void loadUsers(){ users = userRepository.getAllUsers();}

    public String login() {
        currentUser = (User)userRepository.authenticate(username, password);
        if (currentUser != null) {
            System.out.println("Logged in.");
            return "index?faces-redirect=true"; // Redirect to homepage
        } else {
            System.out.println("Couldn't log in.");
        }
        return "login"; // Authentication failed, show error message
    }

    public String logout() {
        currentUser = null;
        return "login?faces-redirect=true";
    }

    public String register() {
        if (currentUser != null) {
            currentUser.setUsername(username);
            String hashedPassword = encrypter.hashPassword(password);
            currentUser.setPasswordHash(hashedPassword);
            currentUser.setRole("teacher");
            userRepository.saveUser(currentUser);
        }
        return "login?faces-redirect=true";
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

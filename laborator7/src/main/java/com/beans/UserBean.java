package com.beans;

import com.cdi.events.UserLoggedInEvent;
import com.cdi.qualifiers.Loggable;
import com.entities.User;
import com.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import com.utilities.PasswordEncrypter;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class UserBean implements Serializable {
    @Inject
    private UserRepository userRepository;
    @Inject
    Event<UserLoggedInEvent> userLoggedInEvent;
    @Inject
    private PasswordEncrypter encrypter;
    @NotNull(message = "Username cannot be null")
    @Size(min = 1, max = 50, message = "Username must be between 1 and 100 characters")
    private String username;
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    private List users;
    private User currentUser;

    @PostConstruct
    public void init() {
        loadUsers();
        currentUser = new User();
    }

    public void loadUsers(){ users = userRepository.getAllUsers();}

    @Loggable
    public String login() {
        currentUser = (User)userRepository.authenticate(username, password);
        if (currentUser != null) {
            userLoggedInEvent.fire(new UserLoggedInEvent(currentUser));
            return "index?faces-redirect=true"; // Redirect to homepage
        }
        else {
            System.out.println("Couldn't log in.");
        }
        return "login"; // Authentication failed, show error message
    }

    @Loggable
    public String logout() {
        currentUser = null;
        return "login?faces-redirect=true";
    }

    @Loggable
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

    public boolean isAdmin() {
        return currentUser != null && "admin".equals(currentUser.getRole());
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

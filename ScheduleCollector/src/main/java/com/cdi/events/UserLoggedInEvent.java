package com.cdi.events;

import com.entities.User;

public class UserLoggedInEvent {
    private final User user;

    public UserLoggedInEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}

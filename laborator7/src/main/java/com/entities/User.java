package com.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "username")
    private Object username;
    @Basic
    @Column(name = "role")
    private Object role;
    @Basic
    @Column(name = "password_hash")
    private Object passwordHash;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Object getRole() {
        return role;
    }

    public void setRole(Object role) {
        this.role = role;
    }

    public Object getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(Object passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User users = (User) o;

        if (userId != users.userId) return false;
        if (!Objects.equals(username, users.username)) return false;
        if (!Objects.equals(role, users.role)) return false;
        if (!Objects.equals(passwordHash, users.passwordHash)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
        return result;
    }
}

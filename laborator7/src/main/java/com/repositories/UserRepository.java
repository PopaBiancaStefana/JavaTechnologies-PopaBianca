package com.repositories;

import com.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import utilities.PasswordEncrypter;

import java.util.List;

@Stateless
public class UserRepository
{
    @PersistenceContext(unitName = "Persistence")
    private EntityManager em;

    private PasswordEncrypter passwordEncrypter;

    public Object authenticate(String username, String password) {
        User user = (User)em.createNamedQuery("getUserByNameAndPassword")
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList().toArray()[0];
        if (passwordEncrypter.checkPassword(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    public List getAllUsers() {
        return em.createNamedQuery("getUsers").getResultList();
    }

    public void saveUser(User user) {
        if ( user.getUserId() == null|| em.find(User.class, user.getUserId()) == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    public void deleteUser(int userId) {
        User user = em.find(User.class, userId);
        if (user != null) {
            em.remove(user);
        }
    }

}

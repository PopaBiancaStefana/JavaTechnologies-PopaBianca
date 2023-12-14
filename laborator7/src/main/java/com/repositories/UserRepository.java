package com.repositories;

import com.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.utilities.PasswordEncrypter;
import java.util.List;

@Stateless
public class UserRepository
{
    @PersistenceContext(unitName = "Persistence")
    private EntityManager em;

    private final PasswordEncrypter passwordEncrypter = new PasswordEncrypter();

    public Object authenticate(String username, String password) {
        List users = em.createNamedQuery("getUserByName")
                .setParameter("username", username)
                .getResultList();
        if (!users.isEmpty()){
            User user = (User)users.get(0);
            if (passwordEncrypter.checkPassword(password, user.getPasswordHash())){
                System.out.println("Password correct");
                return user;
            }
        }
        return null;
    }

    public List getAllUsers() {
        return em.createNamedQuery("getUsers").getResultList();
    }

    public void saveUser(User user) {
        if ( user.getUserId() == null|| em.find(User.class, user.getUserId()) == null) {
            em.persist(user);
            System.out.println("User " + user.getUsername() + " saved");
        } else {
            em.merge(user);
            System.out.println("User " + user.getUsername() + " created");
        }

    }

    public void deleteUser(int userId) {
        User user = em.find(User.class, userId);
        if (user != null) {
            em.remove(user);
        }
    }

}

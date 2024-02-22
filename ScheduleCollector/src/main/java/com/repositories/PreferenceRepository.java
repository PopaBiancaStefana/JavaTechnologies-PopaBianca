package com.repositories;

import com.entities.Preference;
import com.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.UUID;

@Stateless
public class PreferenceRepository {
    @PersistenceContext(unitName = "Persistence")
    private EntityManager em;

    public void savePreference(Preference preference){
        if(em.find(Preference.class, preference.getRegistrationNumber()) == null){
            em.persist(preference);
        } else {
            em.merge(preference);
        }
    }

    public void deletePreference(UUID uuid){
        Preference preference = em.find(Preference.class, uuid);
        if (preference != null){
            em.remove(preference);
        }
    }

    public List getTeacherPreferences(User user){
        return em.createNamedQuery("getPreferencesByTeacher")
                .setParameter("teacher", user)
                .getResultList();
    }

    public List getAllPreferences(){
        return em.createNamedQuery("getPreferences").getResultList();
    }

    public Preference getPreferenceById(UUID uuid){
        return em.find(Preference.class, uuid);
    }
}

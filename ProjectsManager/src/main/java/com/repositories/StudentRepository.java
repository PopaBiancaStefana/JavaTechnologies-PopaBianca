package com.repositories;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class StudentRepository {
    @PersistenceContext(unitName = "Persistence")
    private EntityManager em;

    public List getStudents(){
        return em.createNamedQuery("getStudents").getResultList();
    }
}

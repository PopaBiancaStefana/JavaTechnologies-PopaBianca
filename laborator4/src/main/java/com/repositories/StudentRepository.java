package com.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class StudentRepository {
    @PersistenceContext(unitName = "Persistence")
    private EntityManager em;

    public List getStudents(){
        return em.createNamedQuery("getStudents").getResultList();
    }
}

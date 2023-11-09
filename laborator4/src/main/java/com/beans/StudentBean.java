package com.beans;


import com.entities.Student;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class StudentBean implements Serializable {
    @PersistenceContext(unitName = "Persistence")
    private EntityManager em;
    private List<Student> students;

   @PostConstruct
    public void init() {
       students = em.createQuery("select s from Student s", Student.class).getResultList();
   }

    public Object getStudents() {
        return students;
    }

}
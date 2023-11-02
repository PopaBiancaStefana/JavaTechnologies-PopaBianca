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
    private EntityManager em;
    private static EntityManagerFactory emf;

    private List<Student> students;

   @PostConstruct
    public void init() {
       if(emf == null) {
           emf = Persistence.createEntityManagerFactory("Persistence");
       }
       em = emf.createEntityManager();

       students = em.createQuery("select s from Student s", Student.class).getResultList();
//       em.close();
   }

    public Object getStudents() {
        return students;
    }

    @PreDestroy
    public void cleanup() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
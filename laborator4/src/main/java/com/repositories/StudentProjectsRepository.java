package com.repositories;


import com.entities.StudentProjects;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class StudentProjectsRepository {

    @PersistenceContext(unitName = "Persistence")
    private EntityManager em;

    public Object getStudentNameById(int id) {
        return em.createNamedQuery("getStudentNameById")
                .setParameter("student", id).getResultList().toArray()[0];
    }

    public Object getProjectName(int id) {
        return em.createNamedQuery("getProjectNameById")
                .setParameter("project", id).getResultList().toArray()[0];
    }

    public List getStudentsProjects(){
        return  em.createNamedQuery("getStudentsProjects").getResultList();
    }

    @Transactional
    public void savePreference(StudentProjects student) {
        try {
            List selected = em.createNamedQuery("getRowByStudentIdProjectId")
                    .setParameter("sid", student.getStudentId()).setParameter("pid", student.getProjectId()).getResultList();
            if (selected.isEmpty()) {
                em.persist(student);
            } else {
                em.merge(student);
            }
            System.out.println("Save operation success.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Save operation failure: " + e.getMessage());
        }
    }
}

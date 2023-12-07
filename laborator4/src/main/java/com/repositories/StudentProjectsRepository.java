package com.repositories;

import com.entities.StudentProjects;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
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

    public void savePreference(StudentProjects student) {
        try {
            List selected = em.createNamedQuery("getRowByStudentIdProjectId")
                    .setParameter("sid", student.getStudentId()).setParameter("pid", student.getProjectId()).getResultList();
            if (selected.isEmpty()) {
                em.persist(student);
            } else {
                em.merge(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Save operation failure: " + e.getMessage());
        }
    }
}

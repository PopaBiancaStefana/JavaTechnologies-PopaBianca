package com.repositories;

import com.entities.Project;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProjectRepository {
    @PersistenceContext(unitName = "Persistence")
    private EntityManager em;

    public List getProjects(){
        return em.createNamedQuery("getProjects").getResultList();
    }

    public void saveProject(Project project) {
        try {
            List selected = em.createNamedQuery("getProjectById")
                    .setParameter("id", project.getProjectId()).getResultList();
            if (selected.isEmpty()) {
                em.persist(project);
            } else {
                em.merge(project);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Save operation failure: " + e.getMessage());
        }
    }

    public void deleteProject(Project project) {
        try {
            Project toDelete = em.contains(project) ? project : em.merge(project);
            em.remove(toDelete);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Delete operation failure: " + e.getMessage());
        }
    }
}

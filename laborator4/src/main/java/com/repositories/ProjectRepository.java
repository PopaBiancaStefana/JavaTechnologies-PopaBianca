package com.repositories;

import com.entities.Project;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProjectRepository {
    @PersistenceContext(unitName = "Persistence")
    private EntityManager em;

    public List getProjects(){
        return em.createNamedQuery("getProjects").getResultList();
    }

    @Transactional
    public void saveProject(Project project) {
        try {
            List selected = em.createNamedQuery("saveProjects")
                    .setParameter("id", project.getProjectId()).getResultList();
            if (selected.isEmpty()) {
                em.persist(project);
            } else {
                em.merge(project);
            }
            System.out.println("Save operation success.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Save operation failure: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteProject(Project project) {
        try {
            Project toDelete = em.contains(project) ? project : em.merge(project);
            em.remove(toDelete);
            System.out.println("Delete operation success.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Delete operation failure: " + e.getMessage());
        }
    }
}

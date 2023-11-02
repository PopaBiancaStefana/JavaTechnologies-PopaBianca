package com.beans;

import com.entities.Project;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ProjectBean implements Serializable {
    private EntityManager em;
    private static EntityManagerFactory emf;
    private List<Project> projects;
    private Project selectedProject;

    @PostConstruct
    public void init() {
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory("Persistence");
        }
        em = emf.createEntityManager();

        projects = em.createQuery("select p from Project p", Project.class).getResultList();
//        em.close();
    }

    public Object getProjects() {
        return projects;
    }
    public void loadProjects() {
        projects = em.createQuery("SELECT p FROM Project p", Project.class).getResultList();
    }

    public void loadProject(Project project) {
        selectedProject = project;
    }

    public void createNewProject() {
        selectedProject = new Project();
    }

    public String saveProject() {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Project selected = em.createQuery("SELECT p from Project p WHERE p.project_id = :id", Project.class)
                    .setParameter("id", selectedProject.getProjectId()).getSingleResult();
            if (selected == null) {
                em.persist(selectedProject);
            } else {
                em.merge(selectedProject);
            }
            transaction.commit();
            loadProjects();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting project: " + e.getMessage();
        }
    }
    public String deleteProject(Project project) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Project toDelete = em.contains(project) ? project : em.merge(project);
            em.remove(toDelete);
            transaction.commit();
            loadProjects();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting project: " + e.getMessage();
        }
    }

    @PreDestroy
    public void cleanup() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }


    public Object getSelectedProject() {
        return selectedProject;
    }
}

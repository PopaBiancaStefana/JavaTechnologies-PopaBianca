package com.beans;

import com.entities.Project;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Named
@ViewScoped
public class ProjectBean implements Serializable {

    @PersistenceContext(unitName = "Persistence")
    private EntityManager em;

    private List<Project> projects;
    private Project selectedProject;

    @PostConstruct
    public void init() {
        loadProjects();
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
        selectedProject.setDeadline(new Date(2024,1,1));
        System.out.println("New Project created" + selectedProject.toString());
    }

    @Transactional
    public void saveProject() {
        try {
            List<Project> selected = em.createQuery("SELECT p from Project p WHERE p.project_id = :id", Project.class)
                    .setParameter("id", selectedProject.getProjectId()).getResultList();
            if (selected.isEmpty()) {
                em.persist(selectedProject);
            } else {
                em.merge(selectedProject);
            }
            System.out.println("Save operation success.");
            loadProjects();
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
            loadProjects();
            System.out.println("Delete operation success.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Delete operation failure: " + e.getMessage());
        }
    }

    public Object getSelectedProject() {
        if(selectedProject == null)
            selectedProject = new Project();
        System.out.println("Get selected project" + selectedProject.toString());
        return selectedProject;
    }
}

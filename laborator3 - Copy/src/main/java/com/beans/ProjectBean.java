package com.beans;

import com.entities.Project;
import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ProjectBean implements Serializable {
    @PersistenceContext
    private EntityManager em;

    private List<Project> projects;

    @PostConstruct
    public void init() {
        projects = em.createQuery("select p from Project p", Project.class).getResultList();
    }

    public List<Project> getAllProjects() {
        return projects;
    }
}

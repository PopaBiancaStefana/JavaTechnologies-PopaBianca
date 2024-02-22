package com.beans;

import com.entities.Project;
import com.repositories.ProjectRepository;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Named
@ViewScoped
public class ProjectBean implements Serializable {
    @Inject
    private ProjectRepository projectRepository;
    private List projects;
    private Project selectedProject;

    @PostConstruct
    public void init() {
        loadProjects();
    }

    public Object getProjects() {
        return projects;
    }

    public Object getSelectedProject() {
        if(selectedProject == null)
            selectedProject = new Project();
        return selectedProject;
    }
    public void loadProjects() {
        projects = projectRepository.getProjects();
    }

    public void loadProject(Project project) {
        selectedProject = project;
    }

    public void createNewProject() {
        selectedProject = new Project();
        selectedProject.setDeadline(new Date(2024,1,1));
    }

    public void saveProject(){
        projectRepository.saveProject(selectedProject);
        loadProjects();
    }

    public void deleteProject(Project project) {
        projectRepository.deleteProject(project);
        loadProjects();
    }

}

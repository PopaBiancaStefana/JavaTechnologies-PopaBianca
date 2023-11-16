package com.beans;


import com.entities.Project;
import com.entities.Student;
import com.entities.StudentProjects;
import com.repositories.StudentProjectsRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class StudentProjectsBean implements Serializable {

    @Inject
    private StudentProjectsRepository repository;
    private List studentProjects;

    private StudentProjects selectedStudent;

    @PostConstruct
    public void init() {
        loadStudentsProjects();
    }

    public Object getStudentProjects(){
        return studentProjects;
    }


    public Object getSelectedStudent() {
        if(selectedStudent == null)
            selectedStudent = new StudentProjects();
        return selectedStudent;
    }

    public Object getStudentName(StudentProjects studentProject) {
        return repository.getStudentNameById(studentProject.getStudentId());
    }

    public Object getProjectName(StudentProjects studentProject) {
        return repository.getProjectName(studentProject.getProjectId());
    }

    public void loadStudentsProjects(){
        studentProjects = repository.getStudentsProjects();
    }

    public void loadStudentProject(StudentProjects studentProject){
        selectedStudent = studentProject;
    }

    public void createNewPreference() {
        selectedStudent = new StudentProjects();
    }


    public void savePreference() {
        repository.savePreference(selectedStudent);
        loadStudentsProjects();
    }

}

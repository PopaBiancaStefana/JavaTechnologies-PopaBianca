package com.beans;


import com.entities.Project;
import com.entities.Student;
import com.entities.StudentProjects;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class StudentProjectsBean implements Serializable {
    @PersistenceContext
    private EntityManager em;

    private List<StudentProjects> studentProjects;

    @PostConstruct
    public void init() {
        studentProjects = em.createQuery("select sp from StudentProjects sp", StudentProjects.class).getResultList();
    }

    public List<Project> getstudentProjects(Student student) {

        //the list of projects IDs for student
        List<Integer> studentProjects = em.createQuery("select sp from StudentProjects sp where sp.studentId = :student", StudentProjects.class)
                .setParameter("student", student.getStudentId()).getResultList()
                .stream().map(StudentProjects::getProjectId).collect(Collectors.toList());

        return em.createQuery("select p from Project p where p.projectId in :projects", Project.class)
                .setParameter("projects", studentProjects).getResultList();
    }
}

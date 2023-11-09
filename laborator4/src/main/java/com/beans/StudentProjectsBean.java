package com.beans;


import com.entities.Project;
import com.entities.Student;
import com.entities.StudentProjects;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.faces.view.ViewScoped;
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
    @PersistenceContext(unitName = "Persistence")
    private EntityManager em;
    private List<StudentProjects> studentProjects;

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
    public void loadStudentsProjects(){
        studentProjects = em.createQuery("select sp from StudentProjects sp", StudentProjects.class).getResultList();
    }

    public void loadStudentProject(StudentProjects studentProject){
        selectedStudent = studentProject;
    }

    public void createNewPreference() {
        selectedStudent = new StudentProjects();
    }


    public Object getStudentName(StudentProjects studentProject) {
        return em.createQuery("select s.name from Student s where s.studentId = :student", Student.class)
                .setParameter("student", studentProject.getStudentId()).getResultList().toArray()[0];
    }

    public Object getProjectName(StudentProjects studentProject) {
        return em.createQuery("select p.name from Project p where p.project_id = :project", Project.class)
                .setParameter("project", studentProject.getProjectId()).getResultList().toArray()[0];
    }

    public List<Project> getstudentProjects(Student student) {

        //the list of projects IDs for student
        List<Integer> studentProjects = em.createQuery("select sp from StudentProjects sp where sp.studentId = :student", StudentProjects.class)
                .setParameter("student", student.getStudentId()).getResultList()
                .stream().map(StudentProjects::getProjectId).collect(Collectors.toList());

        return em.createQuery("select p from Project p where p.project_id in :projects", Project.class)
                .setParameter("projects", studentProjects).getResultList();
    }

    @Transactional
    public void savePreference() {
        try {
            List<StudentProjects> selected = em.createQuery("SELECT sp from StudentProjects sp WHERE sp.studentId = :sid AND sp.projectId = :pid", StudentProjects.class)
                    .setParameter("sid", selectedStudent.getStudentId()).setParameter("pid", selectedStudent.getProjectId()).getResultList();
            if (selected.isEmpty()) {
                em.persist(selectedStudent);
            } else {
                em.merge(selectedStudent);
            }
            System.out.println("Save operation success.");
            loadStudentsProjects();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Save operation failure: " + e.getMessage());
        }
    }

}

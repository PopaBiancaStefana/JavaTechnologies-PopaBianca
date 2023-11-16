package com.beans;

import com.repositories.StudentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class StudentBean implements Serializable {
    private List students;
    @Inject
    private StudentRepository studentRepository;

   @PostConstruct
    public void init() {
       students = studentRepository.getStudents();
   }

    public Object getStudents() {
        return students;
    }

}
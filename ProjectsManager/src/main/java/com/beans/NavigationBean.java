package com.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("navigationBean")
@ViewScoped
public class NavigationBean implements Serializable {

    public String goToProjects() {
        System.out.println("in Projects nav");
        return "goToProjects";
    }

    public String goToStudents() {
        return "goToStudents";
    }

    public String goHome(){
        return "goHome";
    }

    public String goStudentsProjects(){
        return "goStudentsProjects";
    }
}

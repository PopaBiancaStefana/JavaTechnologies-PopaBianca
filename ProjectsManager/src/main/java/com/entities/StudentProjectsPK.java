package com.entities;

import jakarta.persistence.*;
import java.io.Serializable;

public class StudentProjectsPK implements Serializable {
    @Column(name = "student_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;
    @Column(name = "project_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentProjectsPK that = (StudentProjectsPK) o;

        if (studentId != that.studentId) return false;
        if (projectId != that.projectId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + projectId;
        return result;
    }
}

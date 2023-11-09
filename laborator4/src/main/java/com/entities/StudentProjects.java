package com.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "student_projects", schema = "public", catalog = "postgres")
@IdClass(StudentProjectsPK.class)
public class StudentProjects {
    @Id
    @Column(name = "student_id")
    private int studentId;

    @Id
    @Column(name = "project_id")
    private int projectId;
    @Basic
    @Column(name = "project_order")
    private Integer projectOrder;

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

    public Integer getProjectOrder() {
        return projectOrder;
    }

    public void setProjectOrder(Integer projectOrder) {
        this.projectOrder = projectOrder;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentProjects that = (StudentProjects) o;

        if (studentId != that.studentId) return false;
        if (projectId != that.projectId) return false;
        if (projectOrder != null ? !projectOrder.equals(that.projectOrder) : that.projectOrder != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + projectId;
        result = 31 * result + (projectOrder != null ? projectOrder.hashCode() : 0);
        return result;
    }
}

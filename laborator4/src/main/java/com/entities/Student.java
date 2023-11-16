package com.entities;

import jakarta.persistence.*;


@NamedQuery(name="getStudents", query = "SELECT s FROM Student s")
@NamedQuery(name="getStudentNameById", query = "SELECT s.name FROM Student s WHERE s.studentId = :student")
@Entity
@Table(name = "students")
public class Student {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "student_id")
    private int studentId;
    @Basic
    @Column(name = "name")
    private String name;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (studentId != student.studentId) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}

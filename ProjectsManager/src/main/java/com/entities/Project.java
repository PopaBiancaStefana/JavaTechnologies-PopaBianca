package com.entities;

import jakarta.persistence.*;
import java.util.Date;

@NamedQuery(name="getProjects", query = "SELECT p FROM Project p")
@NamedQuery(name="getProjectById", query = "SELECT p FROM Project p WHERE p.project_id = :id")
@NamedQuery(name="getProjectNameById", query = "SELECT p.name FROM Project p WHERE p.project_id = :project")
@Entity
@Table(name = "projects")
public class Project {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "project_id")
    private int project_id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "category")
    private String category;
    @Basic
    @Column(name = "long_description")
    private String long_description;
    @Basic
    @Column(name = "deadline")
    private Date deadline;

    public Project() {

    }
    public Project(String name, String category, String long_description, Date deadline) {
        this.name = name;
        this.category = category;
        this.long_description = long_description;
        this.deadline = deadline;
    }

    public int getProjectId() {
        return project_id;
    }

    public void setProjectId(int projectId) {
        this.project_id = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLongDescription() {
        return long_description;
    }

    public void setLongDescription(String long_description) {
        this.long_description = long_description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (project_id != project.project_id) return false;
        if (name != null ? !name.equals(project.name) : project.name != null) return false;
        if (category != null ? !category.equals(project.category) : project.category != null) return false;
        if (long_description != null ? !long_description.equals(project.long_description) : project.long_description != null)
            return false;
        if (deadline != null ? !deadline.equals(project.deadline) : project.deadline != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = project_id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (long_description != null ? long_description.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        return result;
    }
}

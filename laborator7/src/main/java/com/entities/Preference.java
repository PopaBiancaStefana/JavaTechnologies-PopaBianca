package com.entities;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@NamedQuery(name="getPreferences", query = "SELECT p FROM Preference p")
@NamedQuery(name="getPreferencesByTeacher", query = "SELECT p FROM Preference p WHERE p.teacher = : teacher")
@Entity
@Table(name = "Preferences")
public class Preference {
    @Id
    @Column(name = "registration_number")
    private UUID registrationNumber;
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "user_id")
    private User teacher;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "timestamp")
    private Timestamp timestamp;

    public UUID getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(UUID registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public User getTeacherId() {
        return teacher;
    }

    public void setTeacherId(User teacherId) {
        this.teacher = teacherId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Preference that = (Preference) o;

        if (teacher.getUserId() != that.teacher.getUserId()) return false;
        if (!Objects.equals(registrationNumber, that.registrationNumber))
            return false;
        if (!Objects.equals(content, that.content)) return false;
        if (!Objects.equals(timestamp, that.timestamp)) return false;

        return true;
    }
}

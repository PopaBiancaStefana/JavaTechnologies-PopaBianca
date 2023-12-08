package com.beans;

import com.entities.Preference;
import com.entities.User;
import com.repositories.PreferenceRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Named
@SessionScoped
public class PreferenceBean implements Serializable {
    @Inject
    private PreferenceRepository preferenceRepository;
    @Inject
    private UserBean userBean;

    private List preferences;
    private Preference currentPreference;
    private LocalDateTime submissionStart;
    private LocalDateTime submissionEnd;

    @PostConstruct
    public void init() {
        loadPreferencesBasedOnRole();
        currentPreference = new Preference();
        loadTimeFrame();
    }

    public void loadPreferencesBasedOnRole() {
        User currentUser = userBean.getCurrentUser();
        if (currentUser != null) {
            if ("admin".equals(currentUser.getRole())) {
                preferences = preferenceRepository.getAllPreferences();
            } else if ("teacher".equals(currentUser.getRole())) {
                preferences = preferenceRepository.getTeacherPreferences(currentUser);
            }
        }
    }

    public void savePreference() {
        if (isWithinSubmissionTimeFrame()) {
            System.out.println("Saving");
            currentPreference.setRegistrationNumber(UUID.randomUUID());
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            currentPreference.setTimestamp(currentTimestamp);
            currentPreference.setTeacher(userBean.getCurrentUser());
            preferenceRepository.savePreference(currentPreference);
            currentPreference = new Preference();
            loadPreferencesBasedOnRole();
            System.out.println("Saved");
        } else {
            System.out.println("Not within the time frame");
        }
    }

    private void loadTimeFrame() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(input);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            submissionStart = LocalDateTime.parse(prop.getProperty("preference.submission.start"), formatter);
            submissionEnd = LocalDateTime.parse(prop.getProperty("preference.submission.end"), formatter);
        } catch (IOException e){
            System.out.println("Error loading time frame");
        }
    }

    public boolean isWithinSubmissionTimeFrame() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(submissionStart) && now.isBefore(submissionEnd);
    }

    public void deletePreference(UUID registrationNumber) {
        preferenceRepository.deletePreference(registrationNumber);
        loadPreferencesBasedOnRole();
    }

    public void selectPreferenceForEdit(UUID registrationNumber) {
        this.currentPreference = preferenceRepository.getPreferenceById(registrationNumber);
    }

    public void setPreferences(List preferences) {
        this.preferences = preferences;
    }

    public void setCurrentPreference(Preference currentPreference) {
        this.currentPreference = currentPreference;
    }

    public List getPreferences() {
        return preferences;
    }

    public Preference getCurrentPreference() {
        return currentPreference;
    }

    public LocalDateTime getSubmissionStart() {
        return submissionStart;
    }

    public LocalDateTime getSubmissionEnd() {
        return submissionEnd;
    }

//    public void createNewPreference(){
//        currentPreference = new Preference();
//    }
}

package com.beans;

import com.cdi.events.UserLoggedInEvent;
import com.cdi.qualifiers.Loggable;
import com.entities.Preference;
import com.entities.User;
import com.repositories.PreferenceRepository;
import com.utilities.TimeFrameLoader;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Named
@SessionScoped
public class PreferenceBean implements Serializable, SubmissionHandler {
    @Inject
    private PreferenceRepository preferenceRepository;
    @Inject
    private UUID registrationNumber;
    @Inject
    private TimeFrameLoader timeFrameLoader;
    private List preferences;
    @NotNull(message = "Preference must not be null")
    @Size(min = 1, max = 100, message = "Preference must be between 1 and 100 characters")
    private Preference currentPreference;

    @PostConstruct
    public void init() {
        currentPreference = new Preference();
    }

    public void onUserLogin(@Observes UserLoggedInEvent event) {
        loadPreferencesBasedOnRole(event.getUser());
    }

    public void loadPreferencesBasedOnRole(User currentUser) {
        if (currentUser != null) {
            if ("admin".equals(currentUser.getRole())) {
                preferences = preferenceRepository.getAllPreferences();
            } else if ("teacher".equals(currentUser.getRole())) {
                preferences = preferenceRepository.getTeacherPreferences(currentUser);
            }
        }
    }
    @Override
    @Loggable
    public void savePreference(User currentUser) {
        currentPreference.setRegistrationNumber(registrationNumber);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        currentPreference.setTimestamp(currentTimestamp);
        currentPreference.setTeacher(currentUser);
        preferenceRepository.savePreference(currentPreference);
        currentPreference = new Preference();
        loadPreferencesBasedOnRole(currentUser);
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
        return timeFrameLoader.getSubmissionStart();
    }

    public LocalDateTime getSubmissionEnd() {
        return timeFrameLoader.getSubmissionEnd();
    }

    public boolean isWithinSubmissionTimeFrame(){
        return timeFrameLoader.isWithinSubmissionTimeFrame();
    }
}

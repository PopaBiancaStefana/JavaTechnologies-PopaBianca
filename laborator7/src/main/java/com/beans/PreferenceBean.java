package com.beans;

import com.entities.Preference;
import com.entities.User;
import com.repositories.PreferenceRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
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

    @PostConstruct
    public void init() {
        loadPreferencesBasedOnRole();
        currentPreference = new Preference();
    }

    private void loadPreferencesBasedOnRole() {
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
        currentPreference.setRegistrationNumber(UUID.randomUUID());
        preferenceRepository.savePreference(currentPreference);
        currentPreference = new Preference();
        loadPreferencesBasedOnRole();
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
}

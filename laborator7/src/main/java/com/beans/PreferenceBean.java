package com.beans;

import com.entities.Preference;
import com.entities.User;
import com.repositories.PreferenceRepository;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class PreferenceBean {
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

}

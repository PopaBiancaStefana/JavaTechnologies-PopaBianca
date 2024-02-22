package com.beans;

import com.entities.User;

import java.time.LocalDateTime;

public interface SubmissionHandler {
    void savePreference(User currentUser);
    boolean isWithinSubmissionTimeFrame();
}

package com.cdi.decorators;

import com.beans.SubmissionHandler;
import com.entities.User;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.inject.Inject;

import java.io.Serializable;

@Decorator
public abstract class SubmissionHandlerDecorator implements Serializable, SubmissionHandler {
    @Inject
    @Delegate
    private SubmissionHandler submissionHandler;

    @Override
    public void savePreference(User currentUser) {
        if (submissionHandler.isWithinSubmissionTimeFrame()) {
            submissionHandler.savePreference(currentUser);
        } else {
            System.out.println("Not within the time frame for submitting preferences");
        }
    }
}

package com.utilities;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.logging.Logger;

@ApplicationScoped
public class TimeFrameLoader {
    private LocalDateTime submissionStart;
    private LocalDateTime submissionEnd;
    private static final Logger LOGGER = Logger.getLogger(TimeFrameLoader.class.getName());

    @PostConstruct
    public void init() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(input);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            submissionStart = LocalDateTime.parse(prop.getProperty("preference.submission.start"), formatter);
            submissionEnd = LocalDateTime.parse(prop.getProperty("preference.submission.end"), formatter);
        } catch (IOException e){
            LOGGER.severe("Error loading time frame: " + e.getMessage());
        }
    }
    public LocalDateTime getSubmissionStart() {
        return submissionStart;
    }

    public LocalDateTime getSubmissionEnd() {
        return submissionEnd;
    }
    public boolean isWithinSubmissionTimeFrame() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(submissionStart) && now.isBefore(submissionEnd);
    }
}

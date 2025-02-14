package org.neuefische.applicationmangementapp.model;

public record JobApplicationTracker(JobOffer jobOffer, Application application) {
    @Override
    public String toString() {
        return "jobApplicationTracker" + (application.reminderTime() != null ? application.reminderTime() : null);
    }
}
